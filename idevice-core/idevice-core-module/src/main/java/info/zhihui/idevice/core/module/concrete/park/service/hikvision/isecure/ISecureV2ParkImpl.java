package info.zhihui.idevice.core.module.concrete.park.service.hikvision.isecure;

import info.zhihui.idevice.core.module.concrete.park.bo.*;
import info.zhihui.idevice.core.module.concrete.park.service.Park;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 海康的停车场结构：停车场->出入口->车道
 * 用车道唯一标识（roadwayIndexCode）来控制对应的道闸</br>
 *
 * @author jerryge
 */
@Service
public class ISecureV2ParkImpl implements Park {

    @Override
    public List<ParkBo> getParkInfo(Integer areaId) {
        return List.of();
    }

    @Override
    public List<GateBo> getGateInfo(String parkId) {
        return List.of();
    }

    @Override
    public void controlGate(GateOperateBo gateOperateBo) {

    }

    @Override
    public ReserveResultBo reserveTemporaryCar(TemporaryCarAddBo temporaryCarAddBo) {
        return null;
    }

    @Override
    public void cancelTemporaryCar(String reserveOrderSn) {

    }

    @Override
    public TemporaryCarPageBo getTemporaryCarRecordPage(TemporaryCarQueryBo temporaryCarQueryBo) {
        return null;
    }

    @Override
    public LongTermCarAddResultBo addLongTermCar(LongTermCarAddBo longTermCarAddBo) {
        return null;
    }

    @Override
    public void updateLongTermCarValidityPeriod(LongTermCarPeriodUpdateBo longTermCarPeriodUpdateBo) {

    }

    @Override
    public void cancelLongTermCar(LongTermCarCancelBo longTermCarCancelBo) {

    }

    @Override
    public LongTermCarPageBo getLongTermCarRecordList(LongTermCarQueryBo longTermCarQueryBo) {
        return null;
    }
}