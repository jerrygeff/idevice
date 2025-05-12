package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

import lombok.Getter;

@Getter
public enum PersonTypeEnum {
    COMMON("COMMON"),
    GUEST("GUEST"),
    BLACKLIST("BLACKLIST"),
    SUPER("SUPER"),
    DISABLED("DISABLED");

    private final String value;

    PersonTypeEnum(String value) {
        this.value = value;
    }

}