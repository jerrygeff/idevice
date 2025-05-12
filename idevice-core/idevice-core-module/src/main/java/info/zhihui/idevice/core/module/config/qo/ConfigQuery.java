package info.zhihui.idevice.core.module.config.qo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统参数配置 bo
 * </p>
 *
 * @author wl
 * @date 2025-01-02
 */

@Data
@Accessors(chain = true)
public class ConfigQuery {

    private String eqConfigModuleName;

    private String configName;

    private Boolean isSystem;
}
