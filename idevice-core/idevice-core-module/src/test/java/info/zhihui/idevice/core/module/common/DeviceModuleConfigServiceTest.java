package info.zhihui.idevice.core.module.common;

import com.fasterxml.jackson.core.type.TypeReference;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.module.common.bo.DeviceModuleAreaConfigBo;
import info.zhihui.idevice.core.module.common.bo.DeviceModuleConfigBo;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigServiceImpl;
import info.zhihui.idevice.core.module.common.testdata.TestCameraService;
import info.zhihui.idevice.core.module.common.testdata.TestCarService;
import info.zhihui.idevice.core.module.common.testdata.TestConfig;
import info.zhihui.idevice.core.module.common.testdata.TestVisitor;
import info.zhihui.idevice.core.module.common.constant.SystemConfigConstant;
import info.zhihui.idevice.core.module.config.bo.ConfigBo;
import info.zhihui.idevice.core.module.config.bo.ConfigUpdateBo;
import info.zhihui.idevice.core.module.config.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class DeviceModuleConfigServiceTest {
    @InjectMocks
    private DeviceModuleConfigServiceImpl deviceModuleConfigServiceImpl;

    @Mock
    private ConfigService configService;

    @Test
    public void testGetDeviceConfigByModule() {
        String value1 = """
                {"key":"some value of config string"}
                """;
        String value2 = """
                {"key":"some value of config string"}
                """;
        DeviceModuleConfigBo deviceModuleConfigBo1 = new DeviceModuleConfigBo()
                .setModuleServiceName(TestCarService.class.getSimpleName())
                .setImplName("testCarServiceImpl")
                .setConfigValue(value1);
        DeviceModuleConfigBo deviceModuleConfigBo2 = new DeviceModuleConfigBo()
                .setModuleServiceName(TestCameraService.class.getSimpleName())
                .setImplName("testCameraServiceImpl")
                .setConfigValue(value2);
        List<DeviceModuleConfigBo> deviceModuleConfigBoList = new ArrayList<>();
        deviceModuleConfigBoList.add(deviceModuleConfigBo1);
        deviceModuleConfigBoList.add(deviceModuleConfigBo2);

        List<DeviceModuleAreaConfigBo> deviceModuleAreaConfigBoList = new ArrayList<>();
        DeviceModuleAreaConfigBo deviceModuleAreaConfigBo1 = new DeviceModuleAreaConfigBo().setAreaId(1);
        DeviceModuleAreaConfigBo deviceModuleAreaConfigBo2 = new DeviceModuleAreaConfigBo().setAreaId(2);
        DeviceModuleAreaConfigBo deviceModuleAreaConfigBo = new DeviceModuleAreaConfigBo()
                .setAreaId(3)
                .setDeviceConfigList(deviceModuleConfigBoList);
        deviceModuleAreaConfigBoList.add(deviceModuleAreaConfigBo1);
        deviceModuleAreaConfigBoList.add(deviceModuleAreaConfigBo2);
        deviceModuleAreaConfigBoList.add(deviceModuleAreaConfigBo);
        log.info(JacksonUtil.toJson(deviceModuleAreaConfigBoList));

        String configValue = """
                [{"areaId":1,"deviceConfigList":null},{"areaId":2,"deviceConfigList":null},{"areaId":3,"deviceConfigList":[{"moduleServiceName":"TestCarService","implName":"testCarServiceImpl","configValue":{"key":"some value of config string"}
                },{"moduleServiceName":"TestCameraService","implName":"testCameraServiceImpl","configValue":{"key":"some value of config string"}
                }]}]
                """;
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(configValue);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        DeviceModuleConfigBo res = deviceModuleConfigServiceImpl.getDeviceConfigByModule(TestCarService.class, 3);
        DeviceModuleConfigBo except = new DeviceModuleConfigBo().setModuleServiceName("TestCarService").setImplName("testCarServiceImpl").setConfigValue("{\"key\":\"some value of config string\"}");
        Assertions.assertEquals(except, res);

        Assertions.assertThrows(NotFoundException.class, () -> {
            deviceModuleConfigServiceImpl.getDeviceConfigByModule(TestVisitor.class, 3);
        });
    }

    @Test
    public void testGetEquipmentConfigValue() {
        String mockConfigValue = "[{\"areaId\":1,\"deviceConfigList\":[{\"moduleServiceName\":\"TestCameraService\",\"implName\":\"testCameraServiceImpl\",\"configValue\":{\"somevalue\":\"aaa-bbb-ccc\"}}]}]";
        List<DeviceModuleConfigBo> deviceModuleConfigBoList = new ArrayList<>();
        deviceModuleConfigBoList.add(new DeviceModuleConfigBo().setModuleServiceName(TestCameraService.class.getSimpleName()).setImplName("testCameraServiceImpl").setConfigValue("{\"somevalue\":\"aaa-bbb-ccc\"}"));

        String mockConfig = JacksonUtil.toJson(deviceModuleConfigBoList);
        log.info(mockConfig);

        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(mockConfigValue);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        TestConfig except = new TestConfig();
        except.setSomevalue("aaa-bbb-ccc");
        TestConfig res = deviceModuleConfigServiceImpl.getDeviceConfigValue(TestCameraService.class, TestConfig.class, 1);
        Assertions.assertEquals(except, res);
    }

    @Test
    public void testAreaDoesNotExist() {
        String configValue = """
                [{"areaId":1,"deviceConfigList":[{"moduleServiceName":"TestCameraService","implName":"testCameraServiceImpl","configValue":{"somevalue":"test-value"}}]}]
                """;
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(configValue);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // Test with area ID that doesn't exist in config
        Assertions.assertThrows(NotFoundException.class, () -> {
            deviceModuleConfigServiceImpl.getDeviceConfigByModule(TestCameraService.class, 999);
        });
    }

    @Test
    public void testDeviceConfigListIsNull() {
        String configValue = """
                [{"areaId":1,"deviceConfigList":null}]
                """;
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(configValue);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // Test with area that has null deviceConfigList
        Assertions.assertThrows(NotFoundException.class, () -> {
            deviceModuleConfigServiceImpl.getDeviceConfigByModule(TestCameraService.class, 1);
        });
    }

    @Test
    public void testGetDeviceConfigValueWithInvalidJson() {
        String configValue = """
                [{"areaId":1,"deviceConfigList":[{"moduleServiceName":"TestCameraService","implName":"testCameraServiceImpl","configValue":"invalid-json"}]}]
                """;
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(configValue);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // Test with invalid JSON in configValue
        Assertions.assertThrows(BusinessRuntimeException.class, () -> {
            deviceModuleConfigServiceImpl.getDeviceConfigValue(TestCameraService.class, TestConfig.class, 1);
        });
    }

    @Test
    public void testGetAllDeviceConfigStringWithBlankConfig() {
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue("");
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // Test with blank config value
        Assertions.assertThrows(NotFoundException.class, () -> {
            deviceModuleConfigServiceImpl.getDeviceConfigByModule(TestCameraService.class, 1);
        });
    }

    @Test
    public void testGetAllDeviceConfigStringWithNullConfig() {
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(null);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // Test with null config value
        Assertions.assertThrows(NotFoundException.class, () -> {
            deviceModuleConfigServiceImpl.getDeviceConfigByModule(TestCameraService.class, 1);
        });
    }

    @Test
    public void testSetDeviceConfigByArea() {
        // 准备测试数据
        Integer areaId = 3;
        String configValue1 = "{\"host\":\"test-host-1\",\"port\":8080}";
        String configValue2 = "{\"apiKey\":\"test-api-key\",\"secret\":\"test-secret\"}";

        DeviceModuleConfigBo deviceModuleConfigBo1 = new DeviceModuleConfigBo()
                .setModuleServiceName(TestCarService.class.getSimpleName())
                .setImplName("testCarServiceImpl")
                .setConfigValue(configValue1);

        DeviceModuleConfigBo deviceModuleConfigBo2 = new DeviceModuleConfigBo()
                .setModuleServiceName(TestCameraService.class.getSimpleName())
                .setImplName("testCameraServiceImpl")
                .setConfigValue(configValue2);

        List<DeviceModuleConfigBo> newDeviceConfigList = new ArrayList<>();
        newDeviceConfigList.add(deviceModuleConfigBo1);
        newDeviceConfigList.add(deviceModuleConfigBo2);

        // 模拟现有配置数据
        String existingConfigJson = """
                [{"areaId":1,"deviceConfigList":[{"moduleServiceName":"TestCameraService","implName":"testCameraServiceImpl","configValue":{"somevalue":"test-value"}}]},
                {"areaId":2,"deviceConfigList":[{"moduleServiceName":"TestCarService","implName":"testCarServiceImpl","configValue":{"somevalue":"test-value"}}]}]
                """;
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(existingConfigJson);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // 执行被测试方法
        deviceModuleConfigServiceImpl.setDeviceConfigByArea(newDeviceConfigList, areaId);

        // 验证结果
        ArgumentCaptor<ConfigUpdateBo> configUpdateBoCaptor = ArgumentCaptor.forClass(ConfigUpdateBo.class);
        Mockito.verify(configService).updateConfig(configUpdateBoCaptor.capture());

        ConfigUpdateBo capturedUpdateBo = configUpdateBoCaptor.getValue();
        Assertions.assertEquals(SystemConfigConstant.DEVICE_CONFIG, capturedUpdateBo.getConfigKey());

        // 解析更新后的配置JSON
        String updatedConfigJson = capturedUpdateBo.getConfigValue();
        List<DeviceModuleAreaConfigBo> updatedAreaConfigs = JacksonUtil.fromJson(updatedConfigJson,
                new TypeReference<>() {
                });

        // 验证更新后的配置列表包含三个区域
        Assertions.assertEquals(3, updatedAreaConfigs.size());

        // 获取更新后的区域配置
        DeviceModuleAreaConfigBo updatedAreaConfig = updatedAreaConfigs.stream()
                .filter(config -> areaId.equals(config.getAreaId()))
                .findFirst()
                .orElse(null);

        // 验证新增的区域ID
        Assertions.assertNotNull(updatedAreaConfig);
        Assertions.assertEquals(areaId, updatedAreaConfig.getAreaId());

        // 验证新区域的配置列表内容
        List<DeviceModuleConfigBo> updatedDeviceConfigList = updatedAreaConfig.getDeviceConfigList();
        Assertions.assertEquals(2, updatedDeviceConfigList.size());

        // 验证Car服务配置
        DeviceModuleConfigBo updatedCarConfig = updatedDeviceConfigList.stream()
                .filter(config -> TestCarService.class.getSimpleName().equals(config.getModuleServiceName()))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(updatedCarConfig);
        Assertions.assertEquals("testCarServiceImpl", updatedCarConfig.getImplName());

        // 验证Camera服务配置
        DeviceModuleConfigBo updatedCameraConfig = updatedDeviceConfigList.stream()
                .filter(config -> TestCameraService.class.getSimpleName().equals(config.getModuleServiceName()))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(updatedCameraConfig);
        Assertions.assertEquals("testCameraServiceImpl", updatedCameraConfig.getImplName());
    }

    @Test
    public void testSetDeviceConfigByAreaWithExistingArea() {
        // 准备测试数据
        Integer areaId = 1; // 使用已存在的区域ID
        String configValue = "{\"host\":\"updated-host\",\"port\":9090}";

        DeviceModuleConfigBo newDeviceConfigBo = new DeviceModuleConfigBo()
                .setModuleServiceName(TestCarService.class.getSimpleName())
                .setImplName("updatedCarServiceImpl")
                .setConfigValue(configValue);

        List<DeviceModuleConfigBo> newDeviceConfigList = new ArrayList<>();
        newDeviceConfigList.add(newDeviceConfigBo);

        // 模拟现有配置数据（区域1已存在）
        String existingConfigJson = """
                [{"areaId":1,"deviceConfigList":[{"moduleServiceName":"TestCameraService","implName":"testCameraServiceImpl","configValue":{"somevalue":"original-value"}}]},
                {"areaId":2,"deviceConfigList":[{"moduleServiceName":"TestCarService","implName":"testCarServiceImpl","configValue":{"somevalue":"test-value"}}]}]
                """;
        ConfigBo mockSystemConfig = new ConfigBo().setConfigValue(existingConfigJson);
        Mockito.when(configService.getConfigByKey(SystemConfigConstant.DEVICE_CONFIG)).thenReturn(mockSystemConfig);

        // 执行被测试方法
        deviceModuleConfigServiceImpl.setDeviceConfigByArea(newDeviceConfigList, areaId);

        // 验证结果
        ArgumentCaptor<ConfigUpdateBo> configUpdateBoCaptor = ArgumentCaptor.forClass(ConfigUpdateBo.class);
        Mockito.verify(configService).updateConfig(configUpdateBoCaptor.capture());

        ConfigUpdateBo capturedUpdateBo = configUpdateBoCaptor.getValue();
        Assertions.assertEquals(SystemConfigConstant.DEVICE_CONFIG, capturedUpdateBo.getConfigKey());

        // 解析更新后的配置JSON
        String updatedConfigJson = capturedUpdateBo.getConfigValue();
        List<DeviceModuleAreaConfigBo> updatedAreaConfigs = JacksonUtil.fromJson(updatedConfigJson,
                new TypeReference<>() {
                });

        // 验证仍然有两个区域
        Assertions.assertEquals(2, updatedAreaConfigs.size());

        // 获取更新后的区域配置
        DeviceModuleAreaConfigBo updatedAreaConfig = updatedAreaConfigs.stream()
                .filter(config -> areaId.equals(config.getAreaId()))
                .findFirst()
                .orElse(null);

        // 验证区域1的配置已被更新
        Assertions.assertNotNull(updatedAreaConfig);
        Assertions.assertEquals(areaId, updatedAreaConfig.getAreaId());

        // 验证区域1现在只有一个配置（TestCarService）
        List<DeviceModuleConfigBo> updatedDeviceConfigList = updatedAreaConfig.getDeviceConfigList();
        Assertions.assertEquals(1, updatedDeviceConfigList.size());

        // 验证Car服务配置已更新
        DeviceModuleConfigBo updatedCarConfig = updatedDeviceConfigList.get(0);
        Assertions.assertEquals(TestCarService.class.getSimpleName(), updatedCarConfig.getModuleServiceName());
        Assertions.assertEquals("updatedCarServiceImpl", updatedCarConfig.getImplName());
        Assertions.assertEquals(configValue, updatedCarConfig.getConfigValue());

        // 验证区域2配置保持不变
        DeviceModuleAreaConfigBo unchangedAreaConfig = updatedAreaConfigs.stream()
                .filter(config -> Integer.valueOf(2).equals(config.getAreaId()))
                .findFirst()
                .orElse(null);
        Assertions.assertNotNull(unchangedAreaConfig);
    }
}
