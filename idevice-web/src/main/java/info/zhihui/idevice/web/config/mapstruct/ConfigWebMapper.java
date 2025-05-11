package info.zhihui.idevice.web.config.mapstruct;

import info.zhihui.idevice.core.module.common.bo.DeviceModuleConfigBo;
import info.zhihui.idevice.web.config.vo.DeviceModuleConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 配置Web层对象映射器
 */
@Mapper(componentModel = "spring")
public interface ConfigWebMapper {

    ConfigWebMapper INSTANCE = Mappers.getMapper(ConfigWebMapper.class);

    /**
     * 设备模块配置VO列表转BO列表
     */
    List<DeviceModuleConfigBo> deviceModuleConfigVoListToBoList(List<DeviceModuleConfigVo> voList);
}