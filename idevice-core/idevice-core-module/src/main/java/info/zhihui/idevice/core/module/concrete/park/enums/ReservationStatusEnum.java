package info.zhihui.idevice.core.module.concrete.park.enums;

/**
 * @author jerryge
 */
public enum ReservationStatusEnum {

    /**
     * 已预约
     */
    RESERVED(0),

    /**
     * 用户取消
     */
    CANCELED_BY_USER(1),

    /**
     * 到期
     */
    EXPIRED(2),

    /**
     * 已进场
     */
    ENTERED(3),

    /**
     * 已出场
     */
    EXITED(4),

    /**
     * 访客取消
     */
    CANCELED_BY_VISITOR(5),

    /**
     * 有效
     */
    VALID(1000),

    /**
     * 无效
     */
    INVALID(1001),
    ;

    private final int code;

    ReservationStatusEnum(int code) {
        this.code = code;
    }
}
