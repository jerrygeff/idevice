package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;

@Data
public class Face {

    /**
     * 人脸编号
     */
    private String faceId;

    /**
     * 人脸数据
     */
    private String picUrl;
}