package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureCameraConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class RealTimeLinkRequest extends ISecureJsonRequest<RealTimeLinkResponse> {
    private static final String VERSION = "v2";
    private static final String API_URL = String.format(ISecureCameraConstant.CAMERA_URL_REALTIME, VERSION);
    private static final String CAMERA_INDEX_CODE_ERROR_MSG = "cameraIndexCode不能为空";

    private final String cameraIndexCode;
    private final Integer streamType;
    private final String protocol;
    private final Integer transmode;
    private final String expand;
    private final String streamform;

    private RealTimeLinkRequest(String cameraIndexCode, Integer streamType, String protocol, Integer transmode, String expand, String streamform) {
        super(API_URL);
        this.cameraIndexCode = cameraIndexCode;
        this.streamType = streamType;
        this.protocol = protocol;
        this.transmode = transmode;
        this.expand = expand;
        this.streamform = streamform;
    }

    @Override
    public void validate() {
        super.validate();
        if (StringUtils.isBlank(cameraIndexCode)) {
            throw new ParamException(CAMERA_INDEX_CODE_ERROR_MSG);
        }
    }

    @Override
    public Class<RealTimeLinkResponse> getResponseClass() {
        return RealTimeLinkResponse.class;
    }

}