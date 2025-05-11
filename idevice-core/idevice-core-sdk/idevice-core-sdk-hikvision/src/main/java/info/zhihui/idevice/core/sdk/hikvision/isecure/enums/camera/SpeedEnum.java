package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 速度枚举
 * 用于指定摄像头云台控制的速度
 *
 * @author jerryge
 */
@Getter
public enum SpeedEnum {

    /**
     * 慢速
     */
    SLOWEST(1),

    /**
     * 快速
     */
    FASTEST(100);

    private final Integer code;

    SpeedEnum(Integer code) {
        this.code = code;
    }
}
