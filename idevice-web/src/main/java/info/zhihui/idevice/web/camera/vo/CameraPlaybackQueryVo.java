package info.zhihui.idevice.web.camera.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 摄像头回放查询VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CameraPlaybackQueryVo extends CameraRealtimeQueryVo {

    /**
     * 开始时间
     */
    @Schema(description = "开始时间，例如：2025-04-27T12:00:00", type = "LocalDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间，例如：2025-04-27T12:15:00", type = "LocalDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;
}