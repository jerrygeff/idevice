package info.zhihui.idevice.core.module.concrete.camera.service;

import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MockCameraTest {

    private final MockCamera mockCamera = new MockCamera();
    
    @Test
    void getCameraRealTimeLink_Success() {
        // 准备测试数据
        CameraRealtimeQueryBo queryBo = new CameraRealtimeQueryBo();
        queryBo.setChannelCode("testChannel");
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStreamType(CameraStreamTypeEnum.MAIN);
        
        // 执行测试
        CameraRealtimeLinkBo result = mockCamera.getCameraRealTimeLink(queryBo);
        
        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getUrl());
        assertTrue(result.getUrl().startsWith("rtsp://"));
        assertTrue(result.getUrl().contains("/testChannel/"));
    }
    
    @Test
    void getCameraRealTimeLink_DefaultValues() {
        // 准备测试数据 - 空对象应使用默认值
        CameraRealtimeQueryBo queryBo = new CameraRealtimeQueryBo();
        
        // 执行测试
        CameraRealtimeLinkBo result = mockCamera.getCameraRealTimeLink(queryBo);
        
        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getUrl());
        assertTrue(result.getUrl().startsWith("rtsp://"));
        assertTrue(result.getUrl().contains("/cam001/"));
    }
    
    @Test
    void getCameraPlaybackLink_Success() {
        // 准备测试数据
        LocalDateTime startTime = LocalDateTime.now().minusHours(1);
        LocalDateTime endTime = LocalDateTime.now();
        
        CameraPlaybackQueryBo queryBo = new CameraPlaybackQueryBo();
        queryBo.setChannelCode("testChannel");
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStreamType(CameraStreamTypeEnum.MAIN);
        queryBo.setStartTime(startTime);
        queryBo.setEndTime(endTime);
        
        // 预期值 - 使用同样的转换方法
        String expectedStartTime = SysDateUtil.toDateTimeString(startTime);
        String expectedEndTime = SysDateUtil.toDateTimeString(endTime);
        
        // 执行测试
        CameraPlaybackLinkBo result = mockCamera.getCameraPlaybackLink(queryBo);
        
        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getPlaybackUrl());
        assertTrue(result.getPlaybackUrl().startsWith("rtsp://"));
        assertTrue(result.getPlaybackUrl().contains("/testChannel?"));
        assertTrue(result.getPlaybackUrl().contains("start=" + expectedStartTime));
        assertTrue(result.getPlaybackUrl().contains("end=" + expectedEndTime));
    }
    
    @Test
    void getCameraPlaybackLink_DefaultValues() {
        // 准备测试数据 - 空对象应使用默认值
        CameraPlaybackQueryBo queryBo = new CameraPlaybackQueryBo();
        
        // 执行测试
        CameraPlaybackLinkBo result = mockCamera.getCameraPlaybackLink(queryBo);
        
        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getPlaybackUrl());
        assertTrue(result.getPlaybackUrl().startsWith("rtsp://"));
        assertTrue(result.getPlaybackUrl().contains("/cam001?"));
        assertTrue(result.getPlaybackUrl().contains("start=2023-01-01 00:00:00"));
        assertTrue(result.getPlaybackUrl().contains("end=2023-01-01 01:00:00"));
    }
    
    @Test
    void findAllCameraChannelList_Success() {
        // 准备测试数据
        CameraQueryBo queryBo = new CameraQueryBo();
        
        // 执行测试
        List<CameraFetchInfoBo> result = mockCamera.findAllCameraChannelList(queryBo);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // 验证第一个摄像头
        CameraFetchInfoBo camera1 = result.get(0);
        assertEquals("cam001", camera1.getThirdPartyDeviceUniqueCode());
        assertEquals("前门监控", camera1.getThirdPartyDeviceName());
        assertTrue(camera1.getIsPtz());
        assertEquals("NVR001", camera1.getDeviceParentCode());
        assertEquals(1, camera1.getChannelSeq());
        assertEquals("org001", camera1.getOwnerCode());
        
        // 验证第二个摄像头
        CameraFetchInfoBo camera2 = result.get(1);
        assertEquals("cam002", camera2.getThirdPartyDeviceUniqueCode());
        assertEquals("大厅监控", camera2.getThirdPartyDeviceName());
        assertFalse(camera2.getIsPtz());
    }
    
    @Test
    void controlDirection_LogsParameters() {
        // 准备测试数据
        CameraOperateBo operateBo = new CameraOperateBo();
        operateBo.setChannelCode("testChannel");
        
        // 执行测试 - 由于只记录日志，没有返回值，只能确认不抛出异常
        assertDoesNotThrow(() -> mockCamera.controlDirection(operateBo));
    }
} 