package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder(builderClassName = "Builder")
public class PersonFaceUpdateRequest extends ISecureJsonRequest<PersonFaceUpdateResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.PERSON_FACE_UPDATE, VERSION);
    private static final String FACE_ID_ERROR_MSG = "faceId不能为空";
    private static final String FACE_DATA_ERROR_MSG = "faceData不能为空";

    private final String faceId;
    private final String faceData;

    private PersonFaceUpdateRequest(String faceId, String faceData) {
        super(API_URL);
        this.faceId = faceId;
        this.faceData = faceData;
    }

    @Override
    public Class<PersonFaceUpdateResponse> getResponseClass() {
        return PersonFaceUpdateResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (StringUtils.isBlank(faceId)) {
            throw new ParamException(FACE_ID_ERROR_MSG);
        }

        if (StringUtils.isBlank(faceData)) {
            throw new ParamException(FACE_DATA_ERROR_MSG);
        }
    }
}