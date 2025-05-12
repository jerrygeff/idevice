package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

import lombok.Getter;

@Getter
public enum CardTypeEnum {
    IC_CARD(1, "IC卡", 1001),
    CPU_CARD(2, "CPU卡", 1002),
    LONG_DISTANCE_CARD(3, "远距离卡", 1003),
    M1_CARD(4, "M1卡", 1004);

    private final Integer code;
    private final String name;
    private final Integer id;

    CardTypeEnum(Integer code, String name, Integer id) {
        this.code = code;
        this.name = name;
        this.id = id;
    }

}