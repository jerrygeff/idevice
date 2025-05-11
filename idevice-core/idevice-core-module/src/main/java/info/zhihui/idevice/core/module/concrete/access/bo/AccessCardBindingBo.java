package info.zhihui.idevice.core.module.concrete.access.bo;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 门禁卡绑定
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessCardBindingBo {

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 本人人员信息
     */
    private LocalPersonBo localPersonBo;

    /**
     * 卡类型 对应关系见枚举id
     * @see info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CardTypeEnum
     * @see info.zhihui.idevice.core.sdk.dahua.icc.enums.brm.CardType
     */
    private Integer cardType;

    /**
     * 有效期开始时间
     */
    private LocalDateTime startTime;

    /**
     * 有效期结束时间
     */
    private LocalDateTime endTime;
}
