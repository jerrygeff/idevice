package info.zhihui.idevice.core.module.config.mapper;

import info.zhihui.idevice.core.module.config.bo.ConfigBo;
import info.zhihui.idevice.core.module.config.bo.ConfigUpdateBo;
import info.zhihui.idevice.core.module.config.entity.ConfigEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfigMapper {

    List<ConfigBo> entityListToBo(List<ConfigEntity> list);

    ConfigBo entityToBo(ConfigEntity entity);

    ConfigEntity updateBoToEntity(ConfigUpdateBo bo);

}


