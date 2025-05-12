package info.zhihui.idevice.web.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 设置区域设备模块配置请求
 */
@Data
@Accessors(chain = true)
@Schema(description = "设置区域设备模块配置请求")
public class DeviceModuleConfigSetVo {

    @Schema(description = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Integer areaId;

    @Schema(description = "设备模块配置列表")
    @NotEmpty(message = "设备模块配置列表不能为空")
    @Valid
    private List<DeviceModuleConfigVo> deviceConfigList;
}