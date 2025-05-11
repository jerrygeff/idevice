package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

public enum CardUsageTypeEnum {
    NORMAL_CARD("normalCard"),
    HIJACK_CARD("hijackCard"),
    PATROL_CARD("patrolCard");

    private final String value;

    CardUsageTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}