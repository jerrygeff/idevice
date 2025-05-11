package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 动作枚举
 * 用于指定摄像头云台控制的动作
 *
 * @author jerryge
 */
@Getter
public enum ActionEnum {
    /**
     * 开始
     */
    START(0),

    /**
     * 停止
     */
    STOP(1);

    private final Integer code;

    ActionEnum(Integer code) {
        this.code = code;
    }
}