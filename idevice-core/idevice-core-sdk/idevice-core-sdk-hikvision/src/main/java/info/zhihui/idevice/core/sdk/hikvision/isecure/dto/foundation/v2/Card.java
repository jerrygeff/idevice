package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CardAuthActionEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CardUsageTypeEnum;
import lombok.Data;

@Data
public class Card {

    /**
     * 卡号，支持8-20位数字+大写字母的卡号；请根据设备实际能力填写卡号
     */
    private String cardNo;

    /**
     * @see CardUsageTypeEnum
     */
    private String cardType;

    /**
     * @see CardAuthActionEnum
     */
    private String cardStatus;
}