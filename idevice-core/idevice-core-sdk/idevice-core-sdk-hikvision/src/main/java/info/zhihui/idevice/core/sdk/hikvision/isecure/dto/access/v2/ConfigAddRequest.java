package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.PersonData;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.ResourceInfo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author jerryge
 */
@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class ConfigAddRequest extends ISecureJsonRequest<ConfigAddResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.CONFIG_ADD, VERSION);
    private static final String PERSON_DATA_ERROR_MSG = "人员数据不能为空";
    private static final String RESOURCE_INFOS_ERROR_MSG = "资源信息不能为空";
    private static final String PERSON_DATA_MAX_SIZE_ERROR_MSG = "人员数据不能超过%d条";
    private static final String RESOURCE_INFOS_MAX_SIZE_ERROR_MSG = "资源信息不能超过%d条";
    private static final int MAX_SIZE = 1000;


    /**
     * 人员数据列表
     */
    private final List<PersonData> personDatas;

    /**
     * 资源信息列表
     */
    private final List<ResourceInfo> resourceInfos;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    private ConfigAddRequest(List<PersonData> personDatas, List<ResourceInfo> resourceInfos, String startTime, String endTime) {
        super(API_URL);
        this.personDatas = personDatas;
        this.resourceInfos = resourceInfos;

        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    @Override
    public Class<ConfigAddResponse> getResponseClass() {
        return ConfigAddResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (personDatas == null || personDatas.isEmpty()) {
            throw new ParamException(PERSON_DATA_ERROR_MSG);
        }

        if (resourceInfos == null || resourceInfos.isEmpty()) {
            throw new ParamException(RESOURCE_INFOS_ERROR_MSG);
        }

        if (personDatas.size() > MAX_SIZE) {
            throw new ParamException(String.format(PERSON_DATA_MAX_SIZE_ERROR_MSG, MAX_SIZE));
        }

        if (resourceInfos.size() > MAX_SIZE) {
            throw new ParamException(String.format(RESOURCE_INFOS_MAX_SIZE_ERROR_MSG, MAX_SIZE));
        }
    }


}