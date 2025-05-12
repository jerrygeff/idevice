package info.zhihui.idevice.core.module.common.enums;

import lombok.Getter;

/**
 * 设备类型的枚举
 * 系统依赖这个枚举区分设备
 *
 * @author jerryge
 */

@Getter
public enum DeviceTypeEnum {

    ENERGY_GATEWAY("gateway"),
    ENERGY_ELECTRIC("electricMeter"),
    ENERGY_WATER("waterMeter"),
    ACCESS_DEVICE("access"),
    BEHAVIOR_IDENTIFY_DEVICE("behavior"),
    CAR_DEVICE("car"),
    CAMERA_DEVICE("camera"),
    VISITOR_MACHINE("visitorMachine"),
    ;

    private final String deviceType;

    DeviceTypeEnum(String deviceType) {
        this.deviceType = deviceType;
    }

}
