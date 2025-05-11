package info.zhihui.idevice.core.module.concrete.camera.bo;

import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CameraRealtimeQueryBo {
    /**
     * 取流的协议
     */
    private CameraPlayProtocolEnum protocol;

    /**
     * 码流类型
     */
    private CameraStreamTypeEnum streamType;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 区域id
     */
    private Integer areaId;
}
