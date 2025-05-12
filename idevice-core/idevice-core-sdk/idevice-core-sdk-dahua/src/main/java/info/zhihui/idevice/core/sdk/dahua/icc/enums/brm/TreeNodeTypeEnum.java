package info.zhihui.idevice.core.sdk.dahua.icc.enums.brm;

import lombok.Getter;

/**
 * @author jerryge
 */
@Getter
public enum TreeNodeTypeEnum {
    ORG("org"),

    DEV("dev"),

    CH("ch");

    private final String value;

    TreeNodeTypeEnum(String value) {
        this.value = value;
    }
}
