package info.zhihui.idevice.core.module.concrete.foundation.bo;

import info.zhihui.idevice.common.dto.FileResource;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 人员头像变更
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class PersonFaceUpdateBo {
    /**
     * 本地人员信息
     */
    private LocalPersonBo localPersonBo;

    /**
     * 人脸图片信息
     */
    private FileResource faceImageResource;

}
