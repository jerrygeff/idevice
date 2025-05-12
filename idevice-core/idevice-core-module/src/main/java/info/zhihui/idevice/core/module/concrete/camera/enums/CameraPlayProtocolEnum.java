package info.zhihui.idevice.core.module.concrete.camera.enums;

import lombok.Getter;

/**
 * @author jerryge
 */
@Getter
public enum CameraPlayProtocolEnum {

    RTSP("rtsp"),
    RTMP("rtmp"),
    HLS("hls");

    private final String value;

    CameraPlayProtocolEnum(String value) {
        this.value = value;
    }
}
