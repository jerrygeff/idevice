package info.zhihui.idevice.core.module.concrete.access.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 门禁信息查询对象
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessDeviceQueryBo {

    private Integer areaId;
}
