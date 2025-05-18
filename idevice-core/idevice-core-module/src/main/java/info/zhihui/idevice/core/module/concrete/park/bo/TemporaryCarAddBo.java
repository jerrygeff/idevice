package info.zhihui.idevice.core.module.concrete.park.bo;

import java.util.List;

/**
 * @author jerryge
 */
public class TemporaryCarAddBo {
    /**
     * 区域id
     */
    private Integer areaId;

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
     * 预约时间段
     */
    private List<TimePeriod> appointmentTimeList;

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
}
