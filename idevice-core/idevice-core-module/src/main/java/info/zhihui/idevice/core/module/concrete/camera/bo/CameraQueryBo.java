package info.zhihui.idevice.core.module.concrete.camera.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CameraQueryBo {

    /**
     * 区域id
     */
    private Integer areaId;
}
