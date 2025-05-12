package info.zhihui.idevice.web.access.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "门禁人员撤销授权信息")
public class AccessPersonRevokeVo {
    
    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;
    
    @Schema(description = "第三方设备唯一码列表")
    @NotEmpty(message = "设备唯一码列表不能为空")
    private List<String> thirdPartyDeviceUniqueCodes;
} 