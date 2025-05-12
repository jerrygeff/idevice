package info.zhihui.idevice.core.sdk.hikvision.isecure;

import com.hikvision.artemis.sdk.Client;
import com.hikvision.artemis.sdk.Response;
import com.hikvision.artemis.sdk.constant.Constants;
import com.hikvision.artemis.sdk.constant.ContentType;
import com.hikvision.artemis.sdk.constant.HttpSchema;
import com.hikvision.artemis.sdk.enums.Method;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.expection.ISecureRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author jerryge
 */
@Slf4j
public class BaseISecure {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_DEFAULT_VALUE = "*/*";
    private static final String PROXY_ARTEMIS_PATH = "/artemis";
    private static final String HEADER_TAG_ID = "tagId";
    private static final int HTTP_SUCCESS_MIN = 200;
    private static final int HTTP_SUCCESS_MAX = 399;
    private static final int FIND_PAGE_SIZE = 500;

    private static final String ERROR_PREFIX = "海康接口异常: ";
    private static final String MSG_SDK_CONFIG_NULL = "SDK配置信息不能为空";
    private static final String MSG_HOST_BLANK = "SDK host配置不能为空";
    private static final String MSG_APP_KEY_BLANK = "SDK app key配置不能为空";
    private static final String MSG_APP_SECRET_BLANK = "SDK app secret配置不能为空";

    protected <T extends ISecureResponse, E extends ISecureJsonRequest<T>> T doActionWithConfig(ISecureSDKConfig config, E request, Class<T> responseClass) {
        request.validate();
        initJsonRequest(request, config);

        Response response;
        try {
            log.debug("请求参数：{}", JacksonUtil.toJson(request));
            response = Client.execute(request);
            log.debug("返回结果：{}", JacksonUtil.toJson(response));

            validateHttpStatus(response);
        } catch (Exception e) {
            log.error("海康远程接口请求异常：", e);
            throw new BusinessRuntimeException("请求海康远程接口异常:" + e.getMessage());
        }

        T res = JacksonUtil.fromJson(response.getBody(), responseClass);
        checkResult(res);

        return res;
    }

    protected void checkResult(ISecureResponse response) {
        if (!Boolean.TRUE.equals(response.isSuccess())) {
            log.error("请求海康远程接口错误，错误码：{}，错误信息：{}", response.getCode(), response.getMsg());
            String errMsg = response.getMsg();
            throw new ISecureRuntimeException(response.getCode(), "请求海康远程接口结果异常：" + errMsg);
        }
    }

    private void validateHttpStatus(Response response) {
        int statusCode = response.getStatusCode();
        if (statusCode < HTTP_SUCCESS_MIN || statusCode > HTTP_SUCCESS_MAX) {
            String errorMsg = String.format("HTTP状态码异常: %d - %s",
                    statusCode, response.getErrorMessage());
            log.error(errorMsg);
            throw new ISecureRuntimeException(ERROR_PREFIX + errorMsg);
        }
    }

    private void initJsonRequest(ISecureJsonRequest<?> request, ISecureSDKConfig config) {
        validateConfig(config);

        String httpSchema = HttpSchema.HTTPS;
        if (!StringUtils.isEmpty(config.getHttpScheme())) {
            httpSchema = config.getHttpScheme();
        }
        request.setHost(httpSchema + config.getHost());

        String proxy = PROXY_ARTEMIS_PATH;
        if (StringUtils.isNotBlank(config.getProxy())) {
            proxy = "/" + StringUtils.strip(config.getProxy(), "/");
        }

        // fix 分页时多次设置path
        if (!request.getPath().startsWith(proxy)) {
            request.setPath(proxy + request.getPath());
        }

        request.setHeaders(buildJSONHeaders(config));
        request.setMethod(Method.POST_STRING);
        request.setAppKey(config.getAppKey());
        request.setAppSecret(config.getAppSecret());
        request.setTimeout(Constants.DEFAULT_TIMEOUT);
        request.setStringBody(request.buildStringBody());
    }

    private void validateConfig(ISecureSDKConfig config) {
        if (config == null) {
            throw new ParamException(MSG_SDK_CONFIG_NULL);
        }

        if (StringUtils.isBlank(config.getHost())) {
            throw new ParamException(MSG_HOST_BLANK);
        }

        if (StringUtils.isBlank(config.getAppKey())) {
            throw new ParamException(MSG_APP_KEY_BLANK);
        }

        if (StringUtils.isBlank(config.getAppSecret())) {
            throw new ParamException(MSG_APP_SECRET_BLANK);
        }
    }

    private Map<String, String> buildJSONHeaders(ISecureSDKConfig config) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(ACCEPT_HEADER, ACCEPT_DEFAULT_VALUE);
        headerMap.put(CONTENT_TYPE_HEADER, ContentType.CONTENT_TYPE_JSON);

        if (config != null && config.getExtendConfig() != null && StringUtils.isNotBlank(config.getExtendConfig().getTagId())) {
            headerMap.put(HEADER_TAG_ID, config.getExtendConfig().getTagId());
        }

        return headerMap;
    }

    /**
     * 分页查询所有数据并合并结果
     *
     * @param config  海康平台配置信息
     * @param request 分页查询请求对象
     * @param finder  执行实际查询的函数，接收配置和请求参数，返回分页响应
     * @param <T>     返回列表中的数据类型
     * @param <R>     分页响应类型，必须实现ISecurePageResponse接口
     * @param <E>     分页请求类型，必须实现ISecureJsonPageRequest接口
     * @return 所有查询结果的合并列表
     */
    public <T, R extends ISecurePageResponse<T>, E extends ISecureJsonPageRequest<R>> List<T> findAllByPage(ISecureSDKConfig config, E request, BiFunction<ISecureSDKConfig, E, R> finder) {
        // 指定从第一页开始和默认每页数量
        request.setPageNo(1);
        request.setPageSize(FIND_PAGE_SIZE);

        R page = finder.apply(config, request);

        List<T> all = new ArrayList<>(page.getData().getList());
        Integer total = page.getData().getTotal();

        if (total > FIND_PAGE_SIZE) {
            int count = (int) Math.ceil((double) total / FIND_PAGE_SIZE);
            for (int i = 1; i < count; i++) {
                try {
                    request.setPageNo(i + 1);
                    R pageInfo = finder.apply(config, request);
                    all.addAll(pageInfo.getData().getList());
                } catch (Exception e) {
                    log.error("获取全部数据异常：", e);
                    throw new BusinessRuntimeException("获取全部数据异常");
                }
            }
        }
        return all;
    }
}
