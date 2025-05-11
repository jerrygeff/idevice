package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author jerryge
 */
@Getter
@Builder(builderClassName = "Builder")
public class PersonDeleteRequest extends ISecureJsonRequest<PersonDeleteResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.PERSON_BATCH_DELETE, VERSION);
    private static final String PERSON_IDS_ERROR_MSG = "人员ID列表不能为空";

    private final List<String> personIds;

    private PersonDeleteRequest(List<String> personIds) {
        super(API_URL);
        this.personIds = personIds;
    }

    @Override
    public Class<PersonDeleteResponse> getResponseClass() {
        return PersonDeleteResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (personIds == null || personIds.isEmpty()) {
            throw new ParamException(PERSON_IDS_ERROR_MSG);
        }
    }
}