package info.zhihui.idevice.web.camera.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 摄像头取流链接VO
 */
@Data
@Accessors(chain = true)
public class CameraLinkVo {
    
    /**
     * 取流链接
     */
    @Schema(description = "取流链接")
    private String url;
} 