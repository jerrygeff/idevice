package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class PersonAuthRequest extends ISecureJsonRequest<PersonAuthResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.PERSON_AUTH, VERSION);
    private static final Integer MAX_INFOS_SIZE = 1000;
    private static final String RESOURCE_INFOS_EXCEED_MSG = "资源信息不能超过：%d个";
    private static final String PERSON_INFOS_EXCEED_MSG = "人员信息不能超过：%d个";

    private final List<ResourceInfo> resourceInfos;
    private final List<PersonInfo> personInfos;
    private final Integer priority;

    private PersonAuthRequest(List<ResourceInfo> resourceInfos, List<PersonInfo> personInfos, Integer priority) {
        super(API_URL);
        this.resourceInfos = resourceInfos;
        this.personInfos = personInfos;
        this.priority = priority;
    }

    @Override
    public Class<PersonAuthResponse> getResponseClass() {
        return PersonAuthResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (resourceInfos != null && resourceInfos.size() > MAX_INFOS_SIZE) {
            throw new ParamException(String.format(RESOURCE_INFOS_EXCEED_MSG, MAX_INFOS_SIZE));
        }
        if (personInfos != null && personInfos.size() > MAX_INFOS_SIZE) {
            throw new ParamException(String.format(PERSON_INFOS_EXCEED_MSG, MAX_INFOS_SIZE));
        }
    }
}