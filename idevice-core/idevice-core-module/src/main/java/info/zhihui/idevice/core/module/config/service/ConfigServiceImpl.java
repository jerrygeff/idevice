package info.zhihui.idevice.core.module.config.service;

import cn.hutool.core.util.BooleanUtil;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.DataException;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.module.config.bo.ConfigBo;
import info.zhihui.idevice.core.module.config.bo.ConfigUpdateBo;
import info.zhihui.idevice.core.module.config.entity.ConfigEntity;
import info.zhihui.idevice.core.module.config.mapper.ConfigMapper;
import info.zhihui.idevice.core.module.config.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 全局配置信息处理类
 *
 * @author jerryge
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {
    private final ConfigMapper mapper;
    private final ConfigRepository repository;

    @Override
    public void addConfig(ConfigUpdateBo updateBo) {
        ConfigEntity entity = mapper.updateBoToEntity(updateBo);
        repository.insert(entity);
    }

    @Override
    public void updateConfig(ConfigUpdateBo updateBo) {
        ConfigEntity old = repository.getByKey(updateBo.getConfigKey());
        if (old == null) {
            throw DataException.notExist("系统参数配置");
        }
        ConfigEntity entity = mapper.updateBoToEntity(updateBo);
        entity.setId(old.getId());
        int rows = repository.updateById(entity);
        if (rows == 0) {
            log.warn("配置信息更新不成功：{}", updateBo);
        }
    }

    @Override
    public void deleteConfig(String key) {
        ConfigEntity old = repository.getByKey(key);
        if (old != null) {
            if (BooleanUtil.isTrue(old.getIsSystem())) {
                throw new BusinessRuntimeException("内置配置不允许删除");
            }
            repository.deleteByKey(key);
        }
    }

    @Override
    public ConfigBo getConfigByKey(String key) {
        ConfigEntity entity = repository.getByKey(key);
        if (entity == null) {
            throw DataException.notExist("系统配置");
        }
        return mapper.entityToBo(entity);
    }

    @Override
    public <T> T getConfigByKey(String key, Class<T> clazz) throws BusinessRuntimeException {
        try {
            ConfigBo systemConfig = getConfigByKey(key);
            return JacksonUtil.fromJson(systemConfig.getConfigValue(), clazz);
        } catch (Exception e) {
            log.error("系统配置{}解析异常：", key, e);
            throw new BusinessRuntimeException("获取系统配置异常，请联系管理员");
        }
    }
}
