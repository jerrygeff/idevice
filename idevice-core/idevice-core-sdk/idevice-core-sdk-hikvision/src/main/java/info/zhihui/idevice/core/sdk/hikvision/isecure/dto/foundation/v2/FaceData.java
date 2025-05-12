package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FaceData {

    /**
     * 人脸图片base64编码后的字符
     */
    private String faceData;
}