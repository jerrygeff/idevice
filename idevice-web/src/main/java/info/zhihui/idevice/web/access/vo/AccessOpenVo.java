package info.zhihui.idevice.web.access.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "远程开门请求参数")
public class AccessOpenVo {
    
    @Schema(description = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Integer areaId;
    
    @Schema(description = "门禁设备通道编码")
    @NotBlank(message = "门禁设备通道编码不能为空")
    private String thirdPartyDeviceUniqueCode;
} 