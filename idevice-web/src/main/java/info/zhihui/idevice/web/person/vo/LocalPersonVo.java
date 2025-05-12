package info.zhihui.idevice.web.person.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 本地人员信息VO
 */
@Data
@Accessors(chain = true)
public class LocalPersonVo {
    
    @Schema(description = "本地人员ID")
    @NotNull(message = "本地人员ID不能为空")
    private Integer localPersonId;
    
    @Schema(description = "本地模块名称")
    @NotBlank(message = "本地模块名称不能为空")
    private String localModuleName;
    
    @Schema(description = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Integer areaId;
} 