package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

import lombok.Getter;

/**
 * 设备和通道类型枚举
 */
public enum DeviceChannelTypeEnum {
    // 设备
    ACS_DEVICE("acsDevice", "门禁设备"),
    // 通道
    DOOR("door", "门禁点"),
    // 设备
    ECS_DEVICE("ecsDevice", "梯控设备"),
    // 通道
    FLOOR("floor", "梯控楼层"),
    // 设备
    VIS_DEVICE("visDevice", "可视对讲设备"),
    // 设备
    VIS_DEVICE_IN_DOOR("visDeviceInDoor", "可视对讲-室内机"),
    // 设备
    VIS_DEVICE_OUT_DOOR("visDeviceOutDoor", "可视对讲-门口机"),
    // 设备
    VIS_DEVICE_WALL_DOOR("visDeviceWallDoor", "可视对讲-围墙机"),
    // 设备
    VIS_DEVICE_MANAGER("visDeviceManager", "可视对讲-管理机"),
    // 设备
    CCS_DEVICE("ccsDevice", "人脸消费设备");

    @Getter
    private final String code;
    @Getter
    private final String name;

    DeviceChannelTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
