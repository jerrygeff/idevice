package info.zhihui.idevice.core.module.concrete.access.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 门禁的开门请求参数
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessOpenBo {

    /**
     * 区域id
     */
    private Integer areaId;

    /**
     * 门禁设备通道编码
     */
    private String thirdPartyDeviceUniqueCode;

}
