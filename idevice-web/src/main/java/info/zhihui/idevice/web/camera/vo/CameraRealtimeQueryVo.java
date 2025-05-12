package info.zhihui.idevice.web.camera.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 摄像头实时取流查询VO
 */
@Data
@Accessors(chain = true)
public class CameraRealtimeQueryVo {

    /**
     * 取流的协议
     */
    @Schema(description = "取流的协议: rtsp, rtmp, hls")
    @NotBlank(message = "取流协议不能为空")
    private String protocol;

    /**
     * 码流类型
     */
    @Schema(description = "码流类型: 1-主码流, 2-子码流, 3-第三码流")
    @NotNull(message = "码流类型不能为空")
    private Integer streamType;

    /**
     * 通道编码
     */
    @Schema(description = "通道编码")
    @NotBlank(message = "通道编码不能为空")
    private String channelCode;

    /**
     * 区域ID
     */
    @Schema(description = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Integer areaId;
}