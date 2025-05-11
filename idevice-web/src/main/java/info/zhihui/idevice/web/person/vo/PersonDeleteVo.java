package info.zhihui.idevice.web.person.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 人员删除请求VO
 */
@Data
@Accessors(chain = true)
public class PersonDeleteVo {

    /**
     * 本地人员信息
     */
    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;
}