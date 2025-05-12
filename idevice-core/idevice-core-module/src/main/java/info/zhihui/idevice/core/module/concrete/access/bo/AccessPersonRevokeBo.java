package info.zhihui.idevice.core.module.concrete.access.bo;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 门禁人员取消授权
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessPersonRevokeBo {

    /**
     * 本地人员信息
     */
    private LocalPersonBo localPersonBo;

    /**
     * 第三方设备唯一码
     */
    private List<String> thirdPartyDeviceUniqueCodes;

}
