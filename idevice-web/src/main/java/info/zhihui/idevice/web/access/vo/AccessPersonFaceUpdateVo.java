package info.zhihui.idevice.web.access.vo;

import info.zhihui.idevice.common.vo.FileResourceVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "门禁人员照片更新信息")
public class AccessPersonFaceUpdateVo {

    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;

    @Schema(description = "人脸图片资源信息")
    @NotNull(message = "人脸图片资源信息不能为空")
    private FileResourceVo faceImageResource;
}