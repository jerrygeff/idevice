package info.zhihui.idevice.core.module.concrete.access.bo;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 门禁人员授权信息
 * 同一个管理区域（园区），支持多个设备授权
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessPersonAuthBo {

    /**
     * 第三方设备唯一码
     */
    private List<String> thirdPartyDeviceUniqueCodes;

    /**
     * 本地人员信息
     */
    private LocalPersonBo localPersonBo;

    /**
     * 有效期开始时间
     */
    private LocalDateTime startTime;

    /**
     * 有效期结束时间
     */
    private LocalDateTime endTime;
}
