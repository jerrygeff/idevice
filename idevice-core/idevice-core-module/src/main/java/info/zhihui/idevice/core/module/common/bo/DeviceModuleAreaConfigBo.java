package info.zhihui.idevice.core.module.common.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 模块区域配置信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class DeviceModuleAreaConfigBo {
    private Integer areaId;
    private List<DeviceModuleConfigBo> deviceConfigList;
}
