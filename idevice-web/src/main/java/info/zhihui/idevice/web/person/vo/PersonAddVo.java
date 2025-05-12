package info.zhihui.idevice.web.person.vo;

import info.zhihui.idevice.common.vo.FileResourceVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 人员添加请求VO
 */
@Data
@Accessors(chain = true)
public class PersonAddVo {

    /**
     * 本地人员信息
     */
    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;

    /**
     * 指定第三方平台的人员id
     */
    @Schema(description = "指定第三方平台的人员id")
    private String id;

    /**
     * 人员名称
     */
    @Schema(description = "人员名称")
    @NotBlank(message = "人员名称不能为空")
    private String name;

    /**
     * 电话号码
     */
    @Schema(description = "电话号码")
    @NotBlank(message = "电话号码不能为空")
    private String phone;

    /**
     * 证件类型
     */
    @Schema(description = "证件类型：3-户口簿，6-士兵证，12-居住证，111-身份证，114-军官证，115-警官证，116-暂住证，131-工作证，133-学生证，335-机动车驾驶证，414-护照，511-台湾通行证，513-港澳通行证，551-华侨证，554-外国人居留证，-1-其他")
    @NotNull(message = "证件类型不能为空")
    private Integer certificateType;

    /**
     * 证件号码
     */
    @Schema(description = "证件号码")
    @NotBlank(message = "证件号码不能为空")
    private String certificateNo;

    /**
     * 所属组织
     */
    @Schema(description = "所属组织")
    private String organizationCode;

    /**
     * 性别，1：男；2：女；0：未知
     */
    @Schema(description = "性别，1：男；2：女；0：未知")
    private Integer gender;

    /**
     * 人脸信息
     */
    @Schema(description = "人脸信息")
    private FileResourceVo faceImageResource;

    /**
     * 工号
     */
    @Schema(description = "工号")
    private String jobNo;
}