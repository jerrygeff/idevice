package info.zhihui.idevice.web.access.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "门禁卡解绑信息")
public class AccessCardUnBindingVo {
    
    @Schema(description = "卡号")
    @NotBlank(message = "卡号不能为空")
    private String cardNumber;
    
    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;
} 