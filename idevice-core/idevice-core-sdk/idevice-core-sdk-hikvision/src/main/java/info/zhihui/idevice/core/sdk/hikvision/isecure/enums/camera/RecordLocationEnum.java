package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 存储类型枚举：
 * CENTER(0): 中心存储，默认
 * DEVICE(1): 设备存储
 */
@Getter
public enum RecordLocationEnum {
    CENTER(0),
    DEVICE(1);

    private final int code;

    RecordLocationEnum(int code) {
        this.code = code;
    }
}