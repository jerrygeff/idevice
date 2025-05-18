package info.zhihui.idevice.core.module.concrete.park.bo;

import java.time.LocalDateTime;

/**
 * @author jerryge
 */
public class TemporaryCarQueryBo {
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
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;
}
