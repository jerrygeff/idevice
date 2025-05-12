package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

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
    SLOWEST("1"),

    /**
     * 快速
     */
    FASTEST("8");

    private final String value;

    SpeedEnum(String value) {
        this.value = value;
    }
}
