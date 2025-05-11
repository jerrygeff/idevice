package info.zhihui.idevice.core.sdk.dahua.icc;

import com.dahuatech.hutool.json.JSONUtil;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.exception.ClientException;
import com.dahuatech.icc.oauth.http.*;
import com.dahuatech.icc.oauth.model.v202010.OauthConfigBaseInfo;
import com.dahuatech.icc.oauth.model.v202010.OauthConfigClientInfo;
import com.dahuatech.icc.oauth.model.v202010.OauthConfigUserPwdInfo;
import com.dahuatech.icc.oauth.profile.GrantType;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.expection.IccRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * @author jerryge
 */

@Slf4j
public abstract class BaseIcc {
    private final ConcurrentHashMap<String, IClient> clientMap = new ConcurrentHashMap<>();

    private static final int FIND_PAGE_SIZE = 500;

    /**
     * 2025.03.04
     * 已与客服确认，认证信息是不排他的
     * <br/>
     * 即在不同节点上用同样的信息去做认证，不会使得前一次的认证信息失效
     */
    protected synchronized IClient getIClient(OauthConfigBaseInfo configBaseInfo) {
        String key = getKey(configBaseInfo);

        IClient iClient = clientMap.get(key);
        if (iClient == null) {
            try {
                iClient = new IccClient(configBaseInfo);
                clientMap.put(key, iClient);
            } catch (ClientException e) {
                log.error("初始化大华连接错误，请检查参数配置: {}", configBaseInfo, e);
                throw new RuntimeException("初始化大华连接错误，请检查参数配置");
            }
        }

        return iClient;
    }

    /**
     * 通过认证信息，计算出特定的Key
     *
     * @param oauthConfigBaseInfo 认证信息
     * @return String key
     */
    private String getKey(OauthConfigBaseInfo oauthConfigBaseInfo) {
        String host = oauthConfigBaseInfo.getHttpConfigInfo().getHost();
        String port = oauthConfigBaseInfo.getHttpConfigInfo().isEnableHttpTest() ? oauthConfigBaseInfo.getHttpConfigInfo().getHttpPort() : oauthConfigBaseInfo.getHttpConfigInfo().getHttpsPort();
        String grantType = oauthConfigBaseInfo.getGrantType().name();
        String userName = null;
        if (oauthConfigBaseInfo.getGrantType() == GrantType.password) {
            OauthConfigUserPwdInfo oauthConfigUserPwdInfo = (OauthConfigUserPwdInfo) oauthConfigBaseInfo;
            userName = oauthConfigUserPwdInfo.getUsername();
        } else {
            OauthConfigClientInfo oauthConfigClientInfo = (OauthConfigClientInfo) oauthConfigBaseInfo;
            userName = oauthConfigClientInfo.getClientOauthuserId();
        }

        return host + "_" + port + "_" + grantType + "_" + userName + "_" + oauthConfigBaseInfo.getClientId();
    }

    protected void checkResult(IccResponse response) {
        if (!Boolean.TRUE.equals(response.isSuccess()) || StringUtils.isNotBlank(response.getErrMsg())) {
            log.error("大华平台返回结果异常，请求id：{}，错误码：{}，错误信息：{}", response.getRequestId(), response.getCode(), response.getErrMsg());
            String errMsg = response.getErrMsg();
            throw new IccRuntimeException(response.getCode(), "请求大华远程接口结果异常：" + errMsg);
        }
    }

    /**
     * 根据配置信息获取OauthConfigBaseInfo
     *
     * @param config 配置信息
     * @return OauthConfigBaseInfo
     * @NOTICE 默认转换成 OauthConfigUserPwdInfo
     * 后续根据需要，通过iccSdkGrantType设置成不同的OauthConfigBaseInfo
     */
    private OauthConfigBaseInfo getOauthConfigBaseInfoByConfig(IccSdkConfig config) {
        if (config.getIccSdkGrantType() == null) {
            return new OauthConfigUserPwdInfo(
                    config.getIccSdkHost(),
                    config.getIccSdkClientId(),
                    config.getIccSdkClientSecret(),
                    config.getIccSdkUserName(),
                    config.getIccSdkPassword(),
                    config.getIsEnableHttpTest(),
                    config.getPort()
            );
        }

        throw new IccRuntimeException("未支持的授权类型");
    }

    /**
     * 包装的请求大华服务的方法，
     * 确保配置信息被加入请求对象
     *
     * @param config        配置信息
     * @param request       请求对象
     * @param responseClass 响应对象的class
     * @return T 响应对象
     */
    protected <T extends IccResponse, E extends AbstractIccRequest<T>> T doActionWithConfig(IccSdkConfig config, E request, Class<T> responseClass) {
        OauthConfigBaseInfo oauthConfigBaseInfo = getOauthConfigBaseInfoByConfig(config);
        request.setOauthConfigBaseInfo(oauthConfigBaseInfo);
        return doAction(request, responseClass);
    }

    /**
     * 请求大华接口
     *
     * @param request       请求对象
     * @param responseClass 响应对象的class
     * @return T 响应对象
     */
    protected <T extends IccResponse, E extends AbstractIccRequest<T>> T doAction(E request, Class<T> responseClass) {
        IClient iClient = getIClient(request.getOauthConfigBaseInfo());

        T response = null;
        try {
            // 请求参数校验
            request.valid();

            // 设置URL
            request.setUrl(request.getOauthConfigBaseInfo().getHttpConfigInfo().getPrefixUrl() + request.getUrl().substring(8));

            // 业务逻辑处理
            response = iClient.doAction(request, responseClass);
            // 大华包内的jsonUtil
            log.debug("请求参数：{}", JSONUtil.toJsonPrettyStr(request));
            log.debug("返回结果：{}", response);

            if (response == null) {
                throw new BusinessRuntimeException("请求远程接口结果异常：返回结果没有正确解析");
            }

            if (response instanceof IccSelfCheckResponse) {
                ((IccSelfCheckResponse) response).checkResult();
            } else {
                checkResult(response);
            }

            return response;
        } catch (ClientException e) {
            log.error("大华开放平台客户端异常：", e);
            throw new BusinessRuntimeException("大华开放平台调用错误：" + e.getErrMsg());
        } catch (BusinessException e) {
            log.error("大华开放平台业务异常", e);
            String message = e.getErrorMsg() == null ? "未知错误" : e.getErrorMsg() + " - " + e.getCode();
            throw new BusinessRuntimeException("大华开放平台调用错误：" + message);
        } catch (Exception e) {
            log.error("大华开放平台其他异常", e);
            String message = e.getMessage() == null ? "未知错误" : e.getMessage();
            throw new BusinessRuntimeException("大华开放平台调用错误：" + message);
        }
    }

    /**
     * 通过配置信息，获取token
     *
     * @param config 配置信息
     * @return IccToken 认证信息
     */
    public IccTokenResponse.IccToken getAccessToken(IccSdkConfig config) {
        OauthConfigBaseInfo oauthConfigBaseInfo = getOauthConfigBaseInfoByConfig(config);

        IClient iClient = getIClient(oauthConfigBaseInfo);
        return iClient.getAccessToken();
    }

    /**
     * 分页查询所有数据并合并结果
     * 该方法会自动处理分页逻辑，循环获取所有页的数据并合并返回
     *
     * @param config  大华平台配置信息
     * @param request 分页查询请求对象，需继承自IccPageRequest
     * @param finder  执行实际查询的函数，接收配置和请求参数，返回分页响应
     * @param <T>     分页数据项的类型
     * @param <R>     分页响应类型，必须继承自IccPageResponse
     * @param <E>     分页请求类型，必须继承自IccPageRequest
     * @return 所有页数据的合并列表
     * @throws BusinessRuntimeException 当查询过程中发生异常时抛出
     */
    public <T, R extends IccPageResponse<T>, E extends IccPageRequest<R>> List<T> findAllByPage(
            IccSdkConfig config,
            E request,
            BiFunction<IccSdkConfig, E, R> finder
    ) {
        // 指定从第一页开始和默认每页数量
        request.setPageNum(1);
        request.setPageSize(FIND_PAGE_SIZE);

        R response = finder.apply(config, request);
        IccPageVo<T> pageData = response.getData();
        List<T> result = new ArrayList<>(pageData.getPageData());

        int currentPage = pageData.getCurrentPage();
        int totalPage = pageData.getTotalPage();

        while (currentPage < totalPage) {
            try {
                request.setPageNum(currentPage + 1);

                response = finder.apply(config, request);
                pageData = response.getData();
                result.addAll(pageData.getPageData());

                currentPage = pageData.getCurrentPage();
            } catch (Exception e) {
                log.error("获取全部数据异常：", e);
                throw new BusinessRuntimeException("获取全部数据异常");
            }
        }

        return result;
    }

}
