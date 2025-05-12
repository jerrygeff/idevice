package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 传输层协议枚举：
 * UDP(0): UDP传输，GB28181 2011及以前版本只支持UDP传输
 * TCP(1): TCP传输，默认
 */
@Getter
public enum TansmodeEnum {
    UDP(0),
    TCP(1);

    private final int code;

    TansmodeEnum(int code) {
        this.code = code;
    }

}