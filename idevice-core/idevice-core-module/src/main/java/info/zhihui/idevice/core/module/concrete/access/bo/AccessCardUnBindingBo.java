package info.zhihui.idevice.core.module.concrete.access.bo;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 门禁卡解绑
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessCardUnBindingBo {

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 本人人员信息
     */
    private LocalPersonBo localPersonBo;
}
