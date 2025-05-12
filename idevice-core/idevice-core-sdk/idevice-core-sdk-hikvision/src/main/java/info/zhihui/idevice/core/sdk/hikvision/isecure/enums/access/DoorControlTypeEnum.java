package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access;

import lombok.Getter;

/**
 * @author jerryge
 * 门禁控制类型
 */
@Getter
public enum DoorControlTypeEnum {
    ALWAYS_OPEN(0),
    DOOR_CLOSED(1),
    DOOR_OPEN(2),
    ALWAYS_CLOSED(3);

    private final int value;

    DoorControlTypeEnum(int value) {
        this.value = value;
    }

}