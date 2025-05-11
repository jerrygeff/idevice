package info.zhihui.idevice.web.config.biz;

import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.core.module.common.bo.DeviceModuleConfigBo;
import info.zhihui.idevice.core.module.common.constant.SystemConfigConstant;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.config.bo.ConfigBo;
import info.zhihui.idevice.core.module.config.service.ConfigService;
import info.zhihui.idevice.web.config.mapstruct.ConfigWebMapper;
import info.zhihui.idevice.web.config.vo.DeviceModuleConfigSetVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配置业务服务
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigBizService {

    private final ConfigWebMapper configWebMapper;
    private final DeviceModuleConfigService deviceModuleConfigService;
    private final ConfigService configService;

    /**
     * 设置区域设备模块配置
     *
     * @param configSetVo 设置区域设备模块配置请求
     */
    public void setDeviceConfigByArea(DeviceModuleConfigSetVo configSetVo) {
        List<DeviceModuleConfigBo> deviceConfigList = configWebMapper.deviceModuleConfigVoListToBoList(configSetVo.getDeviceConfigList());
        deviceModuleConfigService.setDeviceConfigByArea(deviceConfigList, configSetVo.getAreaId());
    }

    /**
     * 获取区域设备模块配置信息
     *
     * @return 配置信息字符串
     */
    public String getDeviceConfigValue() {
        try {
            ConfigBo bo = configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG);
            return bo.getConfigValue();
        } catch (NotFoundException e) {
            return "";
        }
    }

}