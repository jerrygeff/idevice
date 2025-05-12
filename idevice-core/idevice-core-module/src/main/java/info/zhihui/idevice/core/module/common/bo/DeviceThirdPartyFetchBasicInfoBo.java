package info.zhihui.idevice.core.module.common.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 从第三方获取的基础信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class DeviceThirdPartyFetchBasicInfoBo {
    /**
     * 区域id
     */
    private Integer thirdPartyAreaId;

    /**
     * 设备唯一编码
     */
    private String thirdPartyDeviceUniqueCode;

    /**
     * 设备名称
     */
    private String thirdPartyDeviceName;

    /**
     * 在线状态   0、离线   1、在线
     */
    private Integer onlineStatus;

}
