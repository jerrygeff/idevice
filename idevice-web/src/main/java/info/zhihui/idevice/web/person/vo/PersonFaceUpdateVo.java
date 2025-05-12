package info.zhihui.idevice.web.person.vo;

import info.zhihui.idevice.common.vo.FileResourceVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 人员头像更新VO
 */
@Data
@Accessors(chain = true)
public class PersonFaceUpdateVo {

    /**
     * 本地人员信息
     */
    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;

    /**
     * 人脸图片信息
     */
    @Schema(description = "人脸图片信息")
    @NotNull(message = "人脸图片信息不能为空")
    private FileResourceVo faceImageResource;
}