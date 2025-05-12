package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

import lombok.Getter;

/**
 * 录像类型枚举
 * 用于指定回放视频的类型
 *
 * @author jerryge
 */
@Getter
public enum RecordTypeEnum {

    /**
     * 普通录像
     */
    NORMAL("1"),

    /**
     * 报警录像
     */
    ALARM("2");

    private final String value;

    RecordTypeEnum(String value) {
        this.value = value;
    }
}