package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 摄像头控制命令枚举
 */
@Getter
public enum CommandEnum {
    /** 左转 */
    LEFT("LEFT"),

    /** 右转 */
    RIGHT("RIGHT"),

    /** 上转 */
    UP("UP"),

    /** 下转 */
    DOWN("DOWN"),

    /** 焦距变大 */
    ZOOM_IN("ZOOM_IN"),

    /** 焦距变小 */
    ZOOM_OUT("ZOOM_OUT"),

    /** 左上 */
    LEFT_UP("LEFT_UP"),

    /** 左下 */
    LEFT_DOWN("LEFT_DOWN"),

    /** 右上 */
    RIGHT_UP("RIGHT_UP"),

    /** 右下 */
    RIGHT_DOWN("RIGHT_DOWN"),

    /** 焦点前移 */
    FOCUS_NEAR("FOCUS_NEAR"),

    /** 焦点后移 */
    FOCUS_FAR("FOCUS_FAR"),

    /** 光圈扩大 */
    IRIS_ENLARGE("IRIS_ENLARGE"),

    /** 光圈缩小 */
    IRIS_REDUCE("IRIS_REDUCE"),

    /** 接通雨刷开关 */
    WIPER_SWITCH("WIPER_SWITCH"),

    /** 开始记录路线 */
    START_RECORD_TRACK("START_RECORD_TRACK"),

    /** 停止记录路线 */
    STOP_RECORD_TRACK("STOP_RECORD_TRACK"),

    /** 开始路线 */
    START_TRACK("START_TRACK"),

    /** 停止路线 */
    STOP_TRACK("STOP_TRACK"),

    /** 到预置点 */
    GOTO_PRESET("GOTO_PRESET");

    private final String value;

    CommandEnum(String value) {
        this.value = value;
    }
}