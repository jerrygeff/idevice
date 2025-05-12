package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

import lombok.Getter;

/**
 * 摄像头码流类型枚举
 * 用于指定摄像头视频流的类型
 *
 * @author jerryge
 */
@Getter
 public enum StreamTypeEnum {

    /**
     * 主码流
     */
    MAIN("1"),

    /**
     * 辅码流
     */
    SUB("2"),

    /**
     * 辅码流2
     */
    SUB2("3");

    private final String value;

    StreamTypeEnum(String value) {
        this.value = value;
    }
}