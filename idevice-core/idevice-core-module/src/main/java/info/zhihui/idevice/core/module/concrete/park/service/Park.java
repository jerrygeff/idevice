package info.zhihui.idevice.core.module.concrete.park.service;

import info.zhihui.idevice.core.module.concrete.park.bo.*;

import java.util.List;

/**
 * 每个区域下可以有多个停车场</br>
 * 每个停车场可以有多个出入口</br>
 * 每个出入口可以有多个车道</br>
 *
 * @author jerryge
 */
public interface Park {
    /**
     * 获取停车场以及车位信息
     */
    List<ParkBo> getParkInfo(Integer areaId);

    /**
     * 获取道闸信息
     */
    List<GateBo> getGateInfo(String parkId);

    /**
     * 道闸控制
     */
    void controlGate(GateOperateBo gateOperateBo);

    /**
     * 增加临时车辆预约
     *
     * @return String 预约编号
     */
    ReserveResultBo reserveTemporaryCar(TemporaryCarAddBo temporaryCarAddBo);

    /**
     * 临时车辆取消预约
     */
    void cancelTemporaryCar(String reserveOrderSn);

    /**
     * 查询预约记录
     */
    TemporaryCarPageBo getTemporaryCarRecordPage(TemporaryCarQueryBo temporaryCarQueryBo);

    /**
     * 增加固定车
     */
    LongTermCarAddResultBo addLongTermCar(LongTermCarAddBo longTermCarAddBo);

    /**
     * 调整固定车有效时间
     */
    void updateLongTermCarValidityPeriod(LongTermCarPeriodUpdateBo longTermCarPeriodUpdateBo);

    /**
     * 取消固定车
     */
    void cancelLongTermCar(LongTermCarCancelBo longTermCarCancelBo);

    /**
     * 查询固定车记录
     */
    LongTermCarPageBo getLongTermCarRecordList(LongTermCarQueryBo longTermCarQueryBo);

}
