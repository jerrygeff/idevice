package info.zhihui.idevice.core.module.concrete.access.bo;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 修改员工门禁的照片
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessPersonFaceUpdateBo {
    /**
     * 本地员工信息
     */
    private LocalPersonBo localPersonBo;

    /**
     * 人脸图片信息
     */
    private FileResource faceImageResource;
}
