package info.zhihui.idevice.core.module.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.module.common.bo.DeviceModuleAreaConfigBo;
import info.zhihui.idevice.core.module.common.bo.DeviceModuleConfigBo;
import info.zhihui.idevice.core.module.common.constant.SystemConfigConstant;
import info.zhihui.idevice.core.module.config.bo.ConfigBo;
import info.zhihui.idevice.core.module.config.bo.ConfigUpdateBo;
import info.zhihui.idevice.core.module.config.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceModuleConfigServiceImpl implements DeviceModuleConfigService {

    private final ConfigService configService;

    private List<DeviceModuleConfigBo> getDeviceConfigListByAreaId(Integer areaId) {
        String configString = getAllDeviceConfigString();
        Map<Integer, DeviceModuleAreaConfigBo> configMap = buildParkConfigMap(configString);

        DeviceModuleAreaConfigBo deviceModuleAreaConfigBo = configMap.get(areaId);
        if (deviceModuleAreaConfigBo == null || deviceModuleAreaConfigBo.getDeviceConfigList() == null) {
            log.error("区域配置信息异常，区域id:{}；异常配置信息：{}", areaId, deviceModuleAreaConfigBo);
            throw new NotFoundException("该区域下没有配置信息");
        }

        return deviceModuleAreaConfigBo.getDeviceConfigList();
    }

    private Map<Integer, DeviceModuleAreaConfigBo> buildParkConfigMap(String configString) {
        List<DeviceModuleAreaConfigBo> deviceModuleAreaConfigBoList = JacksonUtil.fromJson(configString, new TypeReference<>() {
        });

        return deviceModuleAreaConfigBoList.stream().collect(Collectors.toMap(DeviceModuleAreaConfigBo::getAreaId, Function.identity()));
    }


    @Override
    public <T extends CommonDeviceModule> DeviceModuleConfigBo getDeviceConfigByModule(Class<T> interfaceType, Integer areaId) {
        String name = interfaceType.getSimpleName();

        List<DeviceModuleConfigBo> equipmentConfigBoList = getDeviceConfigListByAreaId(areaId);
        return equipmentConfigBoList.stream().filter(c -> name.equals(c.getModuleServiceName()))
                .findFirst().orElseThrow(() -> new NotFoundException("尚未配置对应的模块"));
    }

    @Override
    public <T extends CommonDeviceModule, E> E getDeviceConfigValue(Class<T> interfaceType, Class<E> returnObject, Integer areaId) {
        DeviceModuleConfigBo deviceConfigByModule = getDeviceConfigByModule(interfaceType, areaId);
        // e.g. configValue形如：
        // [{"areaId":1,"deviceConfigList":[{"moduleServiceName":"Camera","implName":"ICCV5CameraImpl","configValue":{"iccSdkHost":"icc-dev.hibetatest.com","port":"4077","iccSdkClientId":"CompanyName","iccSdkClientSecret":"42bec152-8f04-476a-9aec-e7d616ff3cb3","iccSdkUserName":"TEST","iccSdkPassword":"OGR28u6_cc"}},{"moduleServiceName":"Person","implName":"IccV5FoundationImpl","configValue":{"iccSdkHost":"icc-dev.hibetatest.com","port":"4077","iccSdkClientId":"CompanyName","iccSdkClientSecret":"42bec152-8f04-476a-9aec-e7d616ff3cb3","iccSdkUserName":"TEST","iccSdkPassword":"OGR28u6_cc"}},{"moduleServiceName":"AccessControl","implName":"IccV5AccessControlImpl","configValue":{"iccSdkHost":"icc-dev.hibetatest.com","port":"4077","iccSdkClientId":"CompanyName","iccSdkClientSecret":"42bec152-8f04-476a-9aec-e7d616ff3cb3","iccSdkUserName":"TEST","iccSdkPassword":"OGR28u6_cc"}}]},{"areaId":101,"deviceConfigList":[{"moduleServiceName":"Camera","implName":"ISecureV2CameraImpl","configValue":{"host":"172.168.0.10","appKey":"23473521","appSecret":"D5alJEwtPxAZaIsVLE2o"}},{"moduleServiceName":"AccessControl","implName":"ISecureV2CameraImpl","configValue":{"host":"172.168.0.10","appKey":"23473521","appSecret":"D5alJEwtPxAZaIsVLE2o"}},{"moduleServiceName":"Person","implName":"172.168.0.10","configValue":{"host":"icc-dev.hibetatest.com","appKey":"23473521","appSecret":"D5alJEwtPxAZaIsVLE2o"}}]}]
        String configValue = deviceConfigByModule.getConfigValue();
        try {
            return JacksonUtil.fromJson(configValue, returnObject);
        } catch (Exception e) {
            log.error("设备配置解析异常[大类型：{}, 配置参数:{}]: {}", interfaceType.getSimpleName(), returnObject.getSimpleName(), configValue);
            throw new BusinessRuntimeException("设备配置解析异常");
        }
    }

    @Override
    public void setDeviceConfigByArea(List<DeviceModuleConfigBo> deviceModuleConfigBoList, Integer areaId) {
        DeviceModuleAreaConfigBo deviceModuleAreaConfigBo = new DeviceModuleAreaConfigBo()
                .setAreaId(areaId)
                .setDeviceConfigList(deviceModuleConfigBoList);

        Map<Integer, DeviceModuleAreaConfigBo> configMap = new HashMap<>();
        try {
            String configString = getAllDeviceConfigString();
            configMap = buildParkConfigMap(configString);
        } catch (NotFoundException ignore) {
        }
        configMap.put(areaId, deviceModuleAreaConfigBo);
        List<DeviceModuleAreaConfigBo> res = new ArrayList<>(configMap.values());

        ConfigUpdateBo updateBo = new ConfigUpdateBo()
                .setConfigKey(SystemConfigConstant.DEVICE_CONFIG)
                .setConfigValue(JacksonUtil.toJson(res));
        configService.updateConfig(updateBo);
    }

    private String getAllDeviceConfigString() {
        ConfigBo bo = configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG);
        String configString = bo.getConfigValue();

        if (StringUtils.isBlank(configString)) {
            throw new NotFoundException("设备配置信息不存在");
        }
        return configString;
    }
}
