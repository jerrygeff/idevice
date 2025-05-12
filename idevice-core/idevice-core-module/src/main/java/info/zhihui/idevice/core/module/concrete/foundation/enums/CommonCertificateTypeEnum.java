package info.zhihui.idevice.core.module.concrete.foundation.enums;

import lombok.Getter;

/**
 * 证件类型枚举
 *
 * @author jerryge
 */
@Getter
public enum CommonCertificateTypeEnum {
    /** 户口簿 */
    HOUSEHOLD_REGISTER(3, "户口簿"),
    /** 士兵证 */
    SOLDIER_CERTIFICATE(6, "士兵证"),
    /** 居住证 */
    RESIDENCE_PERMIT(12, "居住证"),
    /** 身份证 */
    ID_CARD(111, "身份证"),
    /** 军官证 */
    OFFICER_CERTIFICATE(114, "军官证"),
    /** 警官证 */
    POLICE_CERTIFICATE(115, "警官证"),
    /** 暂住证 */
    TEMPORARY_RESIDENCE_PERMIT(116, "暂住证"),
    /** 工作证 */
    WORK_PERMIT(131, "工作证"),
    /** 学生证 */
    STUDENT_ID(133, "学生证"),
    /** 机动车驾驶证 */
    DRIVER_LICENSE(335, "机动车驾驶证"),
    /** 护照 */
    PASSPORT(414, "护照"),
    /** 台湾通行证 */
    TAIWAN_PERMIT(511, "台湾通行证"),
    /** 港澳通行证 */
    HK_MO_PERMIT(513, "港澳通行证"),
    /** 华侨证 */
    OVERSEAS_CHINESE_CERTIFICATE(551, "华侨证"),
    /** 外国人居留证 */
    FOREIGNER_RESIDENCE_PERMIT(554, "外国人居留证"),
    /** 其他 */
    OTHER(-1, "其他");

    private final Integer code;
    private final String name;

    CommonCertificateTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}