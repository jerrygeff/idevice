package info.zhihui.idevice.core.module.concrete.park.bo;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;

import java.util.List;

/**
 * @author jerryge
 */
public class LongTermCarAddBo {

    private LocalPersonBo localPersonBo;

    /**
     * 停车场编号
     */
    private List<String> parkCodeList;

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
     * 有效时间段
     */
    private TimePeriod timePeriod;

}
