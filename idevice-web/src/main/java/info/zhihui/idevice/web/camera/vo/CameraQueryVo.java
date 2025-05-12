package info.zhihui.idevice.web.camera.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 摄像头请求基础VO
 */
@Data
@Accessors(chain = true)
public class CameraQueryVo {

    /**
     * 区域ID
     */
    @Schema(description = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Integer areaId;
}