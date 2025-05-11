package info.zhiuhi.idevice.feature.data.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 某一类设备的统计信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class DeviceStateStatisticsBo {
    /**
     * 在线数量
     */
    private Integer onlineNumber;

    /**
     * 离线数量
     */
    private Integer offlineNumber;
}
