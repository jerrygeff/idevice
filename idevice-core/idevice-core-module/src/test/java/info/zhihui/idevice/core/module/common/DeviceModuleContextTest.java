package info.zhihui.idevice.core.module.common;

import info.zhihui.idevice.core.module.common.bo.DeviceModuleConfigBo;
import info.zhihui.idevice.core.module.common.service.CommonDeviceModule;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.common.testdata.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class DeviceModuleContextTest {

    private final Map<String, CommonDeviceModule> serviceMap;

    @InjectMocks
    private DeviceModuleContext deviceModuleContext;
    private final DeviceModuleConfigService deviceConfigService = Mockito.mock(DeviceModuleConfigService.class);

    public DeviceModuleContextTest() {
        serviceMap = new HashMap<>();
        serviceMap.put(TestCarServiceImpl.class.getSimpleName().toLowerCase(), new TestCarServiceImpl());
        serviceMap.put(TestVisitorImpl.class.getSimpleName().toLowerCase(), new TestVisitorImpl());
        serviceMap.put(MockCameraServiceImpl.class.getSimpleName().toLowerCase(), new MockCameraServiceImpl());

        deviceModuleContext = new DeviceModuleContext(serviceMap, deviceConfigService);
    }

    @Test
    public void testGetServiceByModule() {
        ReflectionTestUtils.setField(deviceModuleContext, "useRealDevice", true);
        DeviceModuleConfigBo deviceModuleConfigBo = new DeviceModuleConfigBo().setImplName(TestCarServiceImpl.class.getSimpleName().toLowerCase());
        Mockito.when(deviceConfigService.getDeviceConfigByModule(TestCarService.class, 1)).thenReturn(deviceModuleConfigBo);

        TestCarService carService = deviceModuleContext.getService(TestCarService.class, 1);
        Assertions.assertEquals(serviceMap.get(TestCarServiceImpl.class.getSimpleName().toLowerCase()), carService);

        // 测试mock场景
        ReflectionTestUtils.setField(deviceModuleContext, "useRealDevice", false);
        TestCameraService cameraService = deviceModuleContext.getService(TestCameraService.class, 1);
        Assertions.assertEquals(serviceMap.get(MockCameraServiceImpl.class.getSimpleName().toLowerCase()), cameraService);
    }


    @Test
    public void testGetModuleAndServiceName() {
        Map<String, List<String>> res = deviceModuleContext.getModuleAndServiceName();
        log.info("{}", res);

        Map<String, List<String>> expect = new HashMap<>();
        List<String> carService = new ArrayList<>();
        carService.add(TestCarServiceImpl.class.getSimpleName().toLowerCase());
        List<String> cameraService = new ArrayList<>();
        cameraService.add(MockCameraServiceImpl.class.getSimpleName().toLowerCase());
        List<String> visitorService = new ArrayList<>();
        visitorService.add(TestVisitorImpl.class.getSimpleName().toLowerCase());

        expect.put(TestCarService.class.getSimpleName(), carService);
        expect.put(TestCameraService.class.getSimpleName(), cameraService);
        expect.put(TestVisitor.class.getSimpleName(), visitorService);

        Assertions.assertEquals(expect, res);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            res.remove(TestCarService.class.getSimpleName());
        });

    }

}
