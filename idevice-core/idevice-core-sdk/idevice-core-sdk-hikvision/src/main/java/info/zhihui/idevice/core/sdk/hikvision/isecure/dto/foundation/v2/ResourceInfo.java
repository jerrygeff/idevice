package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.DeviceChannelTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResourceInfo {

    /**
     * 可以通过接口获取
     * {@see /api/irds/v2/resource/resourcesByParams}
     */
    private String resourceIndexCode;

    /**
     * 设备通道类型
     * @see DeviceChannelTypeEnum
     */
    private String resourceType;

    /**
     * 资源通道号， 当资源类型为设备时，无特殊说明必填。
     * 传参数说明： 表示此次数据需要配置/下载到设备的哪些通道上；
     * 门禁的人证设备、可视对讲设备等无通道的设备，默认通道号为1
     */
    private List<Integer> channelNos;
}