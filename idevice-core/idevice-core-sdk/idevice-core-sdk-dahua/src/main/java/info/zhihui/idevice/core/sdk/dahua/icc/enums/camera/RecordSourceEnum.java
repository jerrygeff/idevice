package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

import lombok.Getter;

/**
 * 录像来源枚举
 * 用于指定回放视频的来源类型
 *
 * @author jerryge
 */
@Getter
public enum RecordSourceEnum {

    /**
     * 设备
     */
    DEVICE("2"),

    /**
     * 中心
     */
    CENTER("3");

    private final String value;

    RecordSourceEnum(String value) {
        this.value = value;
    }
}