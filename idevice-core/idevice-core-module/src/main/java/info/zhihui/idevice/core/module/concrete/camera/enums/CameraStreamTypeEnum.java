package info.zhihui.idevice.core.module.concrete.camera.enums;

import lombok.Getter;

/**
 * 摄像头码流类型枚举
 * @author jerryge
 */
@Getter
public enum CameraStreamTypeEnum {

    MAIN(1),
    SUB(2),
    THIRD(3);

    private final Integer code;

    CameraStreamTypeEnum(Integer code) {
        this.code = code;
    }
}