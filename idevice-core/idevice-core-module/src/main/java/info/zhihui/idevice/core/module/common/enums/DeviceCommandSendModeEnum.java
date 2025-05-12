package info.zhihui.idevice.core.module.common.enums;

import lombok.Getter;

/**
 * 命令发送模式
 */
@Getter
public enum DeviceCommandSendModeEnum {

    PLATFORM(1, "平台统一发送"),
    DEVICE_BY_DEVICE(2, "一个个设备单个发送");

    private final Integer code;

    private final String info;

    DeviceCommandSendModeEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

}
