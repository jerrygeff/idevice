package info.zhihui.idevice.core.module.config.service;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.config.bo.ConfigBo;
import info.zhihui.idevice.core.module.config.bo.ConfigUpdateBo;

public interface ConfigService {
    void addConfig(ConfigUpdateBo updateBo);

    void updateConfig(ConfigUpdateBo updateBo);

    void deleteConfig(String key);

    ConfigBo getConfigByKey(String key);

    <T> T getConfigByKey(String key, Class<T> clazz) throws BusinessRuntimeException;

}
