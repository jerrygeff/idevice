package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

public enum PersonAuthActionEnum {
    ADD("ADD"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String value;

    PersonAuthActionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
