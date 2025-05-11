package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

import lombok.Getter;

/**
 * 摄像头控制命令枚举
 * 用于指定摄像头控制的开启和停止
 *
 * @author jerryge
 */
@Getter
public enum ControlCommandEnum {

    /**
     * 停止
     */
    STOP("0"),

    /**
     * 开启
     */
    START("1");

    private final String value;

    ControlCommandEnum(String value) {
        this.value = value;
    }
}