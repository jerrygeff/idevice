package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder(builderClassName = "Builder")
public class PersonFaceAddRequest extends ISecureJsonRequest<PersonFaceAddResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.PERSON_FACE_ADD, VERSION);
    private static final String PERSON_ID_ERROR_MSG = "personId不能为空";
    private static final String FACE_DATA_ERROR_MSG = "faceData不能为空";

    private final String personId;
    private final String faceData;

    private PersonFaceAddRequest(String personId, String faceData) {
        super(API_URL);
        this.personId = personId;
        this.faceData = faceData;
    }

    @Override
    public Class<PersonFaceAddResponse> getResponseClass() {
        return PersonFaceAddResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (StringUtils.isBlank(personId)) {
            throw new ParamException(PERSON_ID_ERROR_MSG);
        }

        if (StringUtils.isBlank(faceData)) {
            throw new ParamException(FACE_DATA_ERROR_MSG);
        }
    }
}