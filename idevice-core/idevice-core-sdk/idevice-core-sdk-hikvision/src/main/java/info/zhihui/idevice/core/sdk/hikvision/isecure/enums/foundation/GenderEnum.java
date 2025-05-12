package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

public enum GenderEnum {
    // 未知
    UNKNOWN("0"),

    // 男
    MALE("1"),

    // 女
    FEMALE("2");

    private final String code;

    GenderEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Boolean isValid(String code) {
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}