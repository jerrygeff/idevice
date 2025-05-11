package info.zhihui.idevice.core.module.config.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import info.zhihui.idevice.core.module.config.entity.ConfigEntity;
import info.zhihui.idevice.core.module.config.qo.ConfigQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统参数配置 repository
 * </p>
 *
 * @author wl
 * @date 2025-01-02
 */
@Repository
public interface ConfigRepository extends BaseMapper<ConfigEntity> {

    List<ConfigEntity> findList(ConfigQuery query);

    ConfigEntity getByKey(String key);

    void deleteByKey(String configKey);
}

