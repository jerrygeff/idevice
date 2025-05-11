package info.zhihui.idevice.core.module.concrete.camera.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CameraPlaybackQueryBo extends CameraRealtimeQueryBo {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
