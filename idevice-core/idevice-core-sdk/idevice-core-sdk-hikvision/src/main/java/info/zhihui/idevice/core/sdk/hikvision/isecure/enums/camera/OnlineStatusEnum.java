package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 在线状态枚举
 * 用于指定摄像头的在线状态
 *
 * @author jerryge
 */
@Getter
public enum OnlineStatusEnum {
    /**
     * 在线
     */
    ONLINE("1"),

    /**
     * 离线
     */
    OFFLINE("0"),

    /**
     * 未检测
     */
    UNDETECTED("-1");

    private final String value;

    OnlineStatusEnum(String value) {
        this.value = value;
    }
}