package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access;

import lombok.Getter;

/**
 * 配置下载任务类型枚举
 */
@Getter
public enum ConfigDownloadTaskEnum {
    /** 卡片 */
    CARD(1),

    /** 指纹 */
    FINGERPRINT(2),

    /** 卡片+指纹（组合） */
    CARD_FINGERPRINT(3),

    /** 人脸 */
    FACE(4),

    /** 卡片+人脸（组合） */
    CARD_FACE(5),

    /** 人脸+指纹（组合） */
    FACE_FINGERPRINT(6),

    /** 卡片+指纹+人脸（组合） */
    CARD_FINGERPRINT_FACE(7);

    private final int code;

    ConfigDownloadTaskEnum(int code) {
        this.code = code;
    }

    public static boolean isValid(int code) {
        for (ConfigDownloadTaskEnum value : values()) {
            if (value.getCode() == code) {
                return true;
            }
        }
        return false;
    }
}
