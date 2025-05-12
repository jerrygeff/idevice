package info.zhihui.idevice.web.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备模块配置信息
 */
@Data
@Accessors(chain = true)
@Schema(description = "设备模块配置信息")
public class DeviceModuleConfigVo {

    @Schema(description = "模块接口名称")
    @NotBlank(message = "模块接口名称不能为空")
    private String moduleServiceName;

    @Schema(description = "实现类名称")
    @NotBlank(message = "实现类名称不能为空")
    private String implName;

    @Schema(description = "配置信息，JSON格式：参见各个sdk模块的dto/config目录里的配置类，序列化配置类")
    @NotBlank(message = "配置信息不能为空")
    private String configValue;
}