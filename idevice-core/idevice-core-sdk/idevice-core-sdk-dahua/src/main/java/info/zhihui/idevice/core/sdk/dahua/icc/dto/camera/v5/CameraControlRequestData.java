package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import info.zhihui.idevice.core.sdk.dahua.icc.enums.camera.ControlCommandEnum;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.camera.DirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 摄像头控制请求数据
 *
 * @author jerryge
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CameraControlRequestData {
    /**
     * 视频通道编码，第一个$后数字代表通道类型，必须是1，且摄像头类型需是球机(cameraType字段必须是2)
     */
    private String channelId;

    /**
     * @see DirectionEnum
     */
    private String direct;

    /*
     * 水平转动速度（1-8的整数），越大代表越快
     */
    private String stepX;

    /**
     * 垂直转动速度（1-8的整数），越大代表越快
     */
    private String stepY;

    /**
     * @see ControlCommandEnum
     */
    private String command;

    /**
     * 扩展数据
     */
    private String extend;
}