package info.zhihui.idevice.core.module.concrete.park.bo;

import info.zhihui.idevice.core.module.concrete.park.enums.ReservationStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jerryge
 */
public class TemporaryCarRecordBo {
    /**
     * 预约订单号
     */
    private String orderSn;

    /**
     * 停车场编号
     */
    private String parkCode;

    /**
     * 车牌号码
     */
    private String carNumber;

    /**
     * 车身颜色
     */
    private String carColor;

    /**
     * 车牌颜色
     */
    private String carNumberColor;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 出入道闸
     */
    private List<String> gateCodeList;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 预约状态
     */
    private ReservationStatusEnum status;
}
