package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.PersonData;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.ResourceInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author jerryge
 */
@Getter
@Builder(builderClassName = "Builder")
public class ConfigDeleteRequest extends ISecureJsonRequest<ConfigDeleteResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.CONFIG_DELETE, VERSION);
    private static final String PERSON_DATA_ERROR_MSG = "人员数据不能为空";
    private static final String RESOURCE_INFOS_ERROR_MSG = "资源信息不能为空";

    /**
     * 人员数据列表
     */
    private final List<PersonData> personDatas;

    /**
     * 资源信息列表
     */
    private final List<ResourceInfo> resourceInfos;

    private ConfigDeleteRequest(List<PersonData> personDatas, List<ResourceInfo> resourceInfos) {
        super(API_URL);
        this.personDatas = personDatas;
        this.resourceInfos = resourceInfos;
    }

    @Override
    public Class<ConfigDeleteResponse> getResponseClass() {
        return ConfigDeleteResponse.class;
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
    }
}