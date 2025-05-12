package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

import lombok.Getter;

@Getter
public enum CertificateTypeEnum {
    ID_CARD("111", "身份证"),
    PASSPORT("414", "护照"),
    HOUSEHOLD_REGISTER("113", "户口簿"),
    DRIVING_LICENSE("335", "驾驶证"),
    WORK_CERTIFICATE("131", "工作证"),
    STUDENT_CERTIFICATE("133", "学生证"),
    OTHER("990", "其他");

    private final String code;
    private final String value;

    CertificateTypeEnum(String code, String description) {
        this.code = code;
        this.value = description;
    }

    public static Boolean isValid(String code) {
        for (CertificateTypeEnum type : CertificateTypeEnum.values()) {
            if (type.code.equals(code)) {
                return true;
            }
        }

        return false;
    }
}