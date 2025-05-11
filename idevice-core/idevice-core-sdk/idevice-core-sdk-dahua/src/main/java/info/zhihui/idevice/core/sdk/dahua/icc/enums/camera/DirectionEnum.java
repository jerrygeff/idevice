package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

import lombok.Getter;

/**
 * 方向枚举
 * 用于指定摄像头云台控制的方向
 *
 * @author jerryge
 */
@Getter
public enum DirectionEnum {

    /**
     * 上
     */
    UP("1"),

    /**
     * 下
     */
    DOWN("2"),

    /**
     * 左
     */
    LEFT("3"),

    /**
     * 右
     */
    RIGHT("4"),

    /**
     * 左上
     */
    LEFT_UP("5"),

    /**
     * 左下
     */
    LEFT_DOWN("6"),

    /**
     * 右上
     */
    RIGHT_UP("7"),

    /**
     * 右下
     */
    RIGHT_DOWN("8");

    private final String value;

    DirectionEnum(String value) {
        this.value = value;
    }
}
