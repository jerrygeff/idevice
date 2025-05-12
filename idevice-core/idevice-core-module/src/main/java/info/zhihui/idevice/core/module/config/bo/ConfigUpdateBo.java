package info.zhihui.idevice.core.module.config.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfigUpdateBo {

    private String configKey;
    private String configValue;
    private String showName;

    private String moduleName;
}
