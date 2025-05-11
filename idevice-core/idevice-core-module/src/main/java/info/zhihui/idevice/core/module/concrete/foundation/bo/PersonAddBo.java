package info.zhihui.idevice.core.module.concrete.foundation.bo;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.core.module.concrete.foundation.enums.CommonCertificateTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 通用第三方人员
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class PersonAddBo {
    /**
     * 本地人员信息
     */
    private LocalPersonBo localPersonBo;

    /**
     * 指定第三方平台的人员id
     */
    private String id;

    /**
     * 人员名称
     */
    private String name;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 证件类型
     */
    private CommonCertificateTypeEnum certificateType;

    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 所属组织
     */
    private String organizationCode;

    /**
     * 性别，1：男；2：女；0：未知
     */
    private Integer gender;

    /**
     * 人脸信息
     */
    private List<FileResource> faceImageResource;

    /**
     * 工号
     */
    private String jobNo;
}