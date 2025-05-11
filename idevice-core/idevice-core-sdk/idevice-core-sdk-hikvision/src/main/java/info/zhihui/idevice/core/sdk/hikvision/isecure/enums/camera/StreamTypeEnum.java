package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 码流类型
 *
 * @author jerryge
 */
@Getter
public enum StreamTypeEnum {

    /**
     * 主码流
     */
    MAIN(1),

    /**
     * 辅码流
     */
    SUB(2),

    /**
     * 第三码流
     */
    THIRD(3);

    private final Integer code;

    StreamTypeEnum(Integer code) {
        this.code = code;
    }
}