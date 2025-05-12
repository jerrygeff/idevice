package info.zhihui.idevice.web.camera.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 摄像头操作VO
 */
@Data
@Accessors(chain = true)
public class CameraOperateVo {

    /**
     * 操作方向
     */
    @Schema(description = "操作方向: UP-上, DOWN-下, LEFT-左, RIGHT-右, LEFT_UP-左上, LEFT_DOWN-左下, RIGHT_UP-右上, RIGHT_DOWN-右下")
    @NotBlank(message = "操作方向不能为空")
    private String operation;

    /**
     * 动作状态
     */
    @Schema(description = "动作状态: true-开启, false-停止")
    @NotNull(message = "动作状态不能为空")
    private Boolean action;

    /**
     * 速度
     */
    @Schema(description = "速度：1-100。1最慢，100最快")
    @Min(value = 1, message = "速度不能小于1")
    @Max(value = 100, message = "速度不能大于100")
    private Integer speed;

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