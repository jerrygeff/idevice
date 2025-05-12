package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

/**
 * 人员数据类型
 * @author jerryge
 */
public enum PersonDataTypeEnum {
    /**
     * 个人
     */
    PERSON("person"),

    /**
     * 组织
     */
    ORG("org");

    private final String value;

    PersonDataTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}