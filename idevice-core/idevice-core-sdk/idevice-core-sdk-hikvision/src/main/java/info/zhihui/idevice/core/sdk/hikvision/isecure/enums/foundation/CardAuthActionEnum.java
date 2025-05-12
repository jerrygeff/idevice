package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

public enum CardAuthActionEnum {
    ADD("ADD"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String value;

    CardAuthActionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}