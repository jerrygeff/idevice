package info.zhihui.idevice.core.module.concrete.access.enums;

import lombok.Getter;

/**
 * 开门方式
 *
 * @author jerryge
 */
@Getter
public enum AccessOpenTypeEnum {
    FACE_RECOGNITION(1, "人脸识别"),
    CARD(2, "刷卡"),
    FINGERPRINT(3, "指纹"),
    UNKNOWN(4, "未知"),
    ;

    private final Integer code;
    private final String info;

    AccessOpenTypeEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }
}
