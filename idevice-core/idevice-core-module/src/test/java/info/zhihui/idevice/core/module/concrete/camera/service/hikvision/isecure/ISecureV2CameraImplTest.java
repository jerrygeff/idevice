package info.zhihui.idevice.core.module.concrete.camera.service.hikvision.isecure;

import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraDirectionEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.ISecureV2SDK;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.CommandEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ISecureV2CameraImplTest {

    @Mock
    private ISecureV2SDK iSecureV2SDK;

    @Mock
    private DeviceModuleConfigService deviceModuleConfigService;


    @InjectMocks
    private ISecureV2CameraImpl cameraImpl;

    private ISecureSDKConfig mockConfig;

    @BeforeEach
    void setUp() {
        mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), any()))
                .thenReturn(mockConfig);
    }

    @Test
    void getCameraRealTimeLink_Success() {
        // 准备测试数据
        String channelCode = "camera001";
        String expectedUrl = "rtsp://example.com/live";

        CameraRealtimeQueryBo queryBo = new CameraRealtimeQueryBo();
        queryBo.setAreaId(1);
        queryBo.setChannelCode(channelCode);
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStreamType(CameraStreamTypeEnum.MAIN);

        // Mock SDK响应
        RealTimeLinkResponse response = new RealTimeLinkResponse();
        RealTimeLinkResponseData responseData = new RealTimeLinkResponseData();
        responseData.setUrl(expectedUrl);
        response.setData(responseData);
        when(iSecureV2SDK.realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class)))
                .thenReturn(response);

        // 执行测试
        CameraRealtimeLinkBo result = cameraImpl.getCameraRealTimeLink(queryBo);

        // 验证结果
        assertNotNull(result);
        assertEquals(expectedUrl, result.getUrl());

        // 验证调用
        verify(iSecureV2SDK).realTimeLink(eq(mockConfig), argThat(request ->
                channelCode.equals(request.getCameraIndexCode()) &&
                        "rtsp".equals(request.getProtocol()) &&
                        1 == request.getStreamType()
        ));
    }

    @Test
    void getCameraRealTimeLink_WithSubStream_Success() {
        // 准备测试数据
        String channelCode = "camera001";
        String expectedUrl = "rtsp://example.com/live/sub";

        CameraRealtimeQueryBo queryBo = new CameraRealtimeQueryBo();
        queryBo.setAreaId(1);
        queryBo.setChannelCode(channelCode);
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStreamType(CameraStreamTypeEnum.SUB);

        // Mock SDK响应
        RealTimeLinkResponse response = new RealTimeLinkResponse();
        response.setData(new RealTimeLinkResponseData());
        response.getData().setUrl(expectedUrl);
        when(iSecureV2SDK.realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class)))
                .thenReturn(response);

        // 执行测试
        CameraRealtimeLinkBo result = cameraImpl.getCameraRealTimeLink(queryBo);

        // 验证结果
        assertNotNull(result);
        assertEquals(expectedUrl, result.getUrl());

        // 验证调用
        verify(iSecureV2SDK).realTimeLink(eq(mockConfig), argThat(request ->
                channelCode.equals(request.getCameraIndexCode()) &&
                        "rtsp".equals(request.getProtocol()) &&
                        2 == request.getStreamType()
        ));
    }

    @Test
    void getCameraPlaybackLink_Success() {
        // 准备测试数据
        String channelCode = "camera001";
        String expectedUrl = "rtsp://example.com/playback";
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
        LocalDateTime endDateTime = LocalDateTime.now();
        String startTimeStr = SysDateUtil.toOffsetDateTime(startDateTime);
        String endTimeStr = SysDateUtil.toOffsetDateTime(endDateTime);

        CameraPlaybackQueryBo queryBo = new CameraPlaybackQueryBo();
        queryBo.setAreaId(1);
        queryBo.setChannelCode(channelCode);
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStartTime(startDateTime);
        queryBo.setEndTime(endDateTime);

        // Mock SDK响应
        PlaybackLinkResponse response = new PlaybackLinkResponse();
        response.setData(new PlaybackLinkResponseData());
        response.getData().setUrl(expectedUrl);
        when(iSecureV2SDK.playbackLink(eq(mockConfig), any(PlaybackLinkRequest.class)))
                .thenReturn(response);

        // 执行测试
        CameraPlaybackLinkBo result = cameraImpl.getCameraPlaybackLink(queryBo);

        // 验证结果
        assertNotNull(result);
        assertEquals(expectedUrl, result.getPlaybackUrl());

        // 验证调用
        verify(iSecureV2SDK).playbackLink(eq(mockConfig), argThat(request ->
                channelCode.equals(request.getCameraIndexCode()) &&
                        "rtsp".equals(request.getProtocol()) &&
                       startTimeStr.equals(request.getBeginTime()) &&
                        endTimeStr.equals(request.getEndTime())
        ));
    }

    @Test
    void getCameraPlaybackLink_EmptyResponse() {
        // 准备测试数据
        String channelCode = "camera001";
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
        LocalDateTime endDateTime = LocalDateTime.now();

        CameraPlaybackQueryBo queryBo = new CameraPlaybackQueryBo();
        queryBo.setAreaId(1);
        queryBo.setChannelCode(channelCode);
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStartTime(startDateTime);
        queryBo.setEndTime(endDateTime);

        // Mock SDK响应
        PlaybackLinkResponse response = new PlaybackLinkResponse();
        response.setData(new PlaybackLinkResponseData());
        when(iSecureV2SDK.playbackLink(eq(mockConfig), any(PlaybackLinkRequest.class)))
                .thenReturn(response);

        // 执行测试
        CameraPlaybackLinkBo result = cameraImpl.getCameraPlaybackLink(queryBo);

        // 验证结果
        assertNotNull(result);
        assertNull(result.getPlaybackUrl());

        // 验证调用
        verify(iSecureV2SDK).playbackLink(eq(mockConfig), any(PlaybackLinkRequest.class));
    }

    @Test
    void findAllCameraChannelList_Success() {
        // 准备测试数据
        Integer areaId = 1;
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // Mock摄像头列表
        CameraInfo camera1 = new CameraInfo();
        camera1.setIndexCode("camera001");
        camera1.setName("Camera 001");
        camera1.setCameraType(0);
        camera1.setCapability("10101");
        camera1.setChanNum(1);
        camera1.setRegionPath("org1@org2");
        camera1.setRegionPathName("Organization 1/Organization 2");
        camera1.setParentIndexCode("device001");

        CameraInfo camera2 = new CameraInfo();
        camera2.setIndexCode("camera002");
        camera2.setName("Camera 002");
        camera2.setCameraType(1);
        camera2.setCapability("@event_audio@motiontrack@io@event_gis@event_rule@gis@event_ias@event_vss@record@vss@ptz@event_io@net@maintenance@event_device@status@");
        camera2.setChanNum(2);
        camera2.setRegionPath("org1@org3");
        camera2.setRegionPathName("Organization 1/Organization 3");
        camera2.setParentIndexCode("device002");

        List<CameraInfo> cameraList = List.of(camera1, camera2);

        // Mock在线状态
        CameraOnlineInfo onlineInfo1 = new CameraOnlineInfo();
        onlineInfo1.setIndexCode("camera001");
        onlineInfo1.setOnline(1);

        CameraOnlineInfo onlineInfo2 = new CameraOnlineInfo();
        onlineInfo2.setIndexCode("camera002");
        onlineInfo2.setOnline(0);

        List<CameraOnlineInfo> onlineList = List.of(onlineInfo1, onlineInfo2);

        // Mock SDK响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(cameraList);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any()))
                .thenReturn(onlineList);

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(2, result.size());

        // 验证第一个摄像头
        CameraFetchInfoBo cameraBo1 = result.get(0);
        assertEquals("Camera 001", cameraBo1.getThirdPartyDeviceName());
        assertEquals("camera001", cameraBo1.getThirdPartyDeviceUniqueCode());
        assertEquals(0, cameraBo1.getCameraType());
        assertEquals(1, cameraBo1.getChannelSeq());
        assertEquals("device001", cameraBo1.getDeviceParentCode());
        assertEquals(1, cameraBo1.getOnlineStatus());
        assertFalse(cameraBo1.getIsPtz());
        assertEquals(2, cameraBo1.getRegionPath().size());
        assertEquals(2, cameraBo1.getRegionPathName().size());

        // 验证第二个摄像头
        CameraFetchInfoBo cameraBo2 = result.get(1);
        assertEquals("Camera 002", cameraBo2.getThirdPartyDeviceName());
        assertEquals("camera002", cameraBo2.getThirdPartyDeviceUniqueCode());
        assertEquals(1, cameraBo2.getCameraType());
        assertEquals(2, cameraBo2.getChannelSeq());
        assertEquals("device002", cameraBo2.getDeviceParentCode());
        assertEquals(0, cameraBo2.getOnlineStatus());
        assertEquals(List.of("自动追踪能力", "IO能力", "智能应用事件能力", "可视域能力", "入侵报警能力（报警主机）", "视频能力", "云台能力"), cameraBo2.getDeviceCapacity());
        assertTrue(cameraBo2.getIsPtz());
        assertEquals(2, cameraBo2.getRegionPath().size());
        assertEquals(2, cameraBo2.getRegionPathName().size());

        // 验证调用
        verify(iSecureV2SDK, times(2)).findAllByPage(eq(mockConfig), any(), any());
    }

    @Test
    void findAllCameraChannelList_EmptyList() {
        // 准备测试数据
        Integer areaId = 1;
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // Mock空列表
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(List.of());

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertTrue(result.isEmpty());

        // 验证调用
        verify(iSecureV2SDK).findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any());
        verify(iSecureV2SDK, never()).findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any());
    }

    @Test
    void findAllCameraChannelList_EmptyChannelList() {
        // 准备测试数据
        CameraQueryBo queryBo = new CameraQueryBo();
        queryBo.setAreaId(1);

        // Mock SDK响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(List.of());

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // 验证调用
        verify(iSecureV2SDK).findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any());
        verify(iSecureV2SDK, never()).findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any());
    }

    @Test
    void findAllCameraChannelList_EmptyOnlineStatus() {
        // 准备测试数据
        CameraQueryBo queryBo = new CameraQueryBo();
        queryBo.setAreaId(1);

        // Mock 摄像头列表响应
        CameraInfo camera = new CameraInfo();
        camera.setIndexCode("camera001");
        camera.setName("Camera 001");
        camera.setChanNum(1);
        camera.setCameraType(0);
        camera.setCapability("PTZ");
        camera.setParentIndexCode("device001");
        camera.setRegionPath("@root@region1@");
        camera.setRegionPathName("/根节点/区域1/");

        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(List.of(camera));

        // Mock 在线状态响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any()))
                .thenReturn(List.of());

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());

        CameraFetchInfoBo cameraBo = result.get(0);
        assertEquals("Camera 001", cameraBo.getThirdPartyDeviceName());
        assertEquals("camera001", cameraBo.getThirdPartyDeviceUniqueCode());
        assertEquals(0, cameraBo.getCameraType());
        assertEquals(1, cameraBo.getChannelSeq());
        assertEquals("device001", cameraBo.getDeviceParentCode());
        assertEquals(0, cameraBo.getOnlineStatus());
        assertTrue(cameraBo.getIsPtz());
        assertEquals(2, cameraBo.getRegionPath().size());
        assertEquals(2, cameraBo.getRegionPathName().size());

        // 验证调用
        verify(iSecureV2SDK).findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any());
        verify(iSecureV2SDK).findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any());
    }

    @Test
    void controlDirection_Success() {
        // 准备测试数据
        String channelCode = "camera001";
        CameraOperateBo operateBo = new CameraOperateBo();
        operateBo.setAreaId(1);
        operateBo.setChannelCode(channelCode);
        operateBo.setOperation(CameraDirectionEnum.UP);
        operateBo.setAction(true);
        operateBo.setSpeed(5);

        // Mock SDK响应
        CameraControlResponse response = new CameraControlResponse();
        when(iSecureV2SDK.cameraControl(eq(mockConfig), any(CameraControlRequest.class)))
                .thenReturn(response);

        // 执行测试
        cameraImpl.controlDirection(operateBo);

        // 验证调用
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                channelCode.equals(request.getCameraIndexCode()) &&
                        "UP".equals(request.getCommand()) &&
                        Objects.equals(0, request.getAction()) &&
                        5 == request.getSpeed()
        ));
    }

    @Test
    void controlDirection_Stop_Success() {
        // 准备测试数据
        String channelCode = "camera001";
        CameraOperateBo operateBo = new CameraOperateBo();
        operateBo.setAreaId(1);
        operateBo.setChannelCode(channelCode);
        operateBo.setOperation(CameraDirectionEnum.DOWN);
        operateBo.setAction(false);
        operateBo.setSpeed(0);

        // Mock SDK响应
        CameraControlResponse response = new CameraControlResponse();
        when(iSecureV2SDK.cameraControl(eq(mockConfig), any(CameraControlRequest.class)))
                .thenReturn(response);

        // 执行测试
        cameraImpl.controlDirection(operateBo);

        // 验证调用
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                channelCode.equals(request.getCameraIndexCode()) &&
                        "DOWN".equals(request.getCommand()) &&
                        Objects.equals(request.getAction(), 1) &&
                        0 == request.getSpeed()
        ));
    }

    @Test
    void getCameraRealTimeLink_WithDifferentStreamTypes() {
        // Test MAIN stream type
        CameraRealtimeQueryBo queryBoMain = new CameraRealtimeQueryBo();
        queryBoMain.setAreaId(1);
        queryBoMain.setChannelCode("camera001");
        queryBoMain.setStreamType(CameraStreamTypeEnum.MAIN);
        queryBoMain.setProtocol(CameraPlayProtocolEnum.RTSP);

        RealTimeLinkResponse responseMain = new RealTimeLinkResponse();
        responseMain.setData(new RealTimeLinkResponseData());
        responseMain.getData().setUrl("rtsp://example.com/main");
        when(iSecureV2SDK.realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class)))
                .thenReturn(responseMain);

        CameraRealtimeLinkBo resultMain = cameraImpl.getCameraRealTimeLink(queryBoMain);
        assertEquals("rtsp://example.com/main", resultMain.getUrl());
        verify(iSecureV2SDK).realTimeLink(eq(mockConfig), argThat(request -> 1 == request.getStreamType()));

        // Test SUB stream type
        CameraRealtimeQueryBo queryBoSub = new CameraRealtimeQueryBo();
        queryBoSub.setAreaId(1);
        queryBoSub.setChannelCode("camera001");
        queryBoSub.setStreamType(CameraStreamTypeEnum.SUB);
        queryBoSub.setProtocol(CameraPlayProtocolEnum.RTSP);

        RealTimeLinkResponse responseSub = new RealTimeLinkResponse();
        responseSub.setData(new RealTimeLinkResponseData());
        responseSub.getData().setUrl("rtsp://example.com/sub");
        when(iSecureV2SDK.realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class)))
                .thenReturn(responseSub);

        CameraRealtimeLinkBo resultSub = cameraImpl.getCameraRealTimeLink(queryBoSub);
        assertEquals("rtsp://example.com/sub", resultSub.getUrl());
        verify(iSecureV2SDK).realTimeLink(eq(mockConfig), argThat(request -> 2 == request.getStreamType()));

        // Test THIRD stream type
        CameraRealtimeQueryBo queryBoThird = new CameraRealtimeQueryBo();
        queryBoThird.setAreaId(1);
        queryBoThird.setChannelCode("camera001");
        queryBoThird.setStreamType(CameraStreamTypeEnum.THIRD);
        queryBoThird.setProtocol(CameraPlayProtocolEnum.RTSP);

        RealTimeLinkResponse responseThird = new RealTimeLinkResponse();
        responseThird.setData(new RealTimeLinkResponseData());
        responseThird.getData().setUrl("rtsp://example.com/third");
        when(iSecureV2SDK.realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class)))
                .thenReturn(responseThird);

        CameraRealtimeLinkBo resultThird = cameraImpl.getCameraRealTimeLink(queryBoThird);
        assertEquals("rtsp://example.com/third", resultThird.getUrl());
        verify(iSecureV2SDK).realTimeLink(eq(mockConfig), argThat(request -> 3 == request.getStreamType()));
    }

    @Test
    void findAllCameraChannelList_WithEmptyRegionPath() {
        // 准备测试数据
        Integer areaId = 1;
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // Mock摄像头列表
        CameraInfo camera = new CameraInfo();
        camera.setIndexCode("camera001");
        camera.setName("Camera 001");
        camera.setCameraType(0);
        camera.setCapability("10101");
        camera.setChanNum(1);
        camera.setRegionPath(""); // Empty region path
        camera.setRegionPathName(""); // Empty region path name
        camera.setParentIndexCode("device001");

        List<CameraInfo> cameraList = List.of(camera);

        // Mock在线状态
        CameraOnlineInfo onlineInfo = new CameraOnlineInfo();
        onlineInfo.setIndexCode("camera001");
        onlineInfo.setOnline(1);

        List<CameraOnlineInfo> onlineList = List.of(onlineInfo);

        // Mock SDK响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(cameraList);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any()))
                .thenReturn(onlineList);

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(1, result.size());
        CameraFetchInfoBo cameraBo = result.get(0);
        assertTrue(cameraBo.getRegionPath().isEmpty());
        assertTrue(cameraBo.getRegionPathName().isEmpty());
    }

    @Test
    void findAllCameraChannelList_WithDifferentCapabilities() {
        // 准备测试数据
        Integer areaId = 1;
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // Mock摄像头列表
        CameraInfo camera1 = new CameraInfo();
        camera1.setIndexCode("camera001");
        camera1.setName("Camera 001");
        camera1.setCameraType(0);
        camera1.setCapability("ptz"); // PTZ capability
        camera1.setChanNum(1);
        camera1.setRegionPath("org1@org2");
        camera1.setRegionPathName("Organization 1/Organization 2");
        camera1.setParentIndexCode("device001");

        CameraInfo camera2 = new CameraInfo();
        camera2.setIndexCode("camera002");
        camera2.setName("Camera 002");
        camera2.setCameraType(1);
        camera2.setCapability(""); // Empty capability
        camera2.setChanNum(2);
        camera2.setRegionPath("org1@org3");
        camera2.setRegionPathName("Organization 1/Organization 3");
        camera2.setParentIndexCode("device002");

        List<CameraInfo> cameraList = List.of(camera1, camera2);

        // Mock在线状态
        CameraOnlineInfo onlineInfo1 = new CameraOnlineInfo();
        onlineInfo1.setIndexCode("camera001");
        onlineInfo1.setOnline(1);

        CameraOnlineInfo onlineInfo2 = new CameraOnlineInfo();
        onlineInfo2.setIndexCode("camera002");
        onlineInfo2.setOnline(0);

        List<CameraOnlineInfo> onlineList = List.of(onlineInfo1, onlineInfo2);

        // Mock SDK响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(cameraList);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any()))
                .thenReturn(onlineList);

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(2, result.size());
        assertTrue(result.get(0).getIsPtz()); // First camera has PTZ
        assertFalse(result.get(1).getIsPtz()); // Second camera doesn't have PTZ
    }

    @Test
    void controlDirection_WithDifferentDirections() {
        // Test UP direction
        CameraOperateBo operateBoUp = new CameraOperateBo();
        operateBoUp.setAreaId(1);
        operateBoUp.setChannelCode("camera001");
        operateBoUp.setOperation(CameraDirectionEnum.UP);
        operateBoUp.setAction(true);
        operateBoUp.setSpeed(50);

        cameraImpl.controlDirection(operateBoUp);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.UP.getValue().equals(request.getCommand())
        ));

        // Test DOWN direction
        CameraOperateBo operateBoDown = new CameraOperateBo();
        operateBoDown.setAreaId(1);
        operateBoDown.setChannelCode("camera001");
        operateBoDown.setOperation(CameraDirectionEnum.DOWN);
        operateBoDown.setAction(true);
        operateBoDown.setSpeed(50);

        cameraImpl.controlDirection(operateBoDown);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.DOWN.getValue().equals(request.getCommand())
        ));

        // Test LEFT direction
        CameraOperateBo operateBoLeft = new CameraOperateBo();
        operateBoLeft.setAreaId(1);
        operateBoLeft.setChannelCode("camera001");
        operateBoLeft.setOperation(CameraDirectionEnum.LEFT);
        operateBoLeft.setAction(true);
        operateBoLeft.setSpeed(50);

        cameraImpl.controlDirection(operateBoLeft);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.LEFT.getValue().equals(request.getCommand())
        ));

        // Test RIGHT direction
        CameraOperateBo operateBoRight = new CameraOperateBo();
        operateBoRight.setAreaId(1);
        operateBoRight.setChannelCode("camera001");
        operateBoRight.setOperation(CameraDirectionEnum.RIGHT);
        operateBoRight.setAction(true);
        operateBoRight.setSpeed(50);

        cameraImpl.controlDirection(operateBoRight);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.RIGHT.getValue().equals(request.getCommand())
        ));

        // Test LEFT_UP direction
        CameraOperateBo operateBoLeftUp = new CameraOperateBo();
        operateBoLeftUp.setAreaId(1);
        operateBoLeftUp.setChannelCode("camera001");
        operateBoLeftUp.setOperation(CameraDirectionEnum.LEFT_UP);
        operateBoLeftUp.setAction(true);
        operateBoLeftUp.setSpeed(50);

        cameraImpl.controlDirection(operateBoLeftUp);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.LEFT_UP.getValue().equals(request.getCommand())
        ));

        // Test LEFT_DOWN direction
        CameraOperateBo operateBoLeftDown = new CameraOperateBo();
        operateBoLeftDown.setAreaId(1);
        operateBoLeftDown.setChannelCode("camera001");
        operateBoLeftDown.setOperation(CameraDirectionEnum.LEFT_DOWN);
        operateBoLeftDown.setAction(true);
        operateBoLeftDown.setSpeed(50);

        cameraImpl.controlDirection(operateBoLeftDown);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.LEFT_DOWN.getValue().equals(request.getCommand())
        ));

        // Test RIGHT_UP direction
        CameraOperateBo operateBoRightUp = new CameraOperateBo();
        operateBoRightUp.setAreaId(1);
        operateBoRightUp.setChannelCode("camera001");
        operateBoRightUp.setOperation(CameraDirectionEnum.RIGHT_UP);
        operateBoRightUp.setAction(true);
        operateBoRightUp.setSpeed(50);

        cameraImpl.controlDirection(operateBoRightUp);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.RIGHT_UP.getValue().equals(request.getCommand())
        ));

        // Test RIGHT_DOWN direction
        CameraOperateBo operateBoRightDown = new CameraOperateBo();
        operateBoRightDown.setAreaId(1);
        operateBoRightDown.setChannelCode("camera001");
        operateBoRightDown.setOperation(CameraDirectionEnum.RIGHT_DOWN);
        operateBoRightDown.setAction(true);
        operateBoRightDown.setSpeed(50);

        cameraImpl.controlDirection(operateBoRightDown);
        verify(iSecureV2SDK).cameraControl(eq(mockConfig), argThat(request ->
                CommandEnum.RIGHT_DOWN.getValue().equals(request.getCommand())
        ));
    }

    @Test
    void controlDirection_WithInvalidDirection() {
        CameraOperateBo operateBo = new CameraOperateBo();
        operateBo.setAreaId(1);
        operateBo.setChannelCode("camera001");
        operateBo.setOperation(null); // Invalid direction
        operateBo.setAction(true);
        operateBo.setSpeed(50);

        assertThrows(RuntimeException.class, () -> cameraImpl.controlDirection(operateBo));
    }

    @Test
    void findAllCameraChannelList_WithDifferentOnlineStatus() {
        // 准备测试数据
        Integer areaId = 1;
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // Mock摄像头列表
        CameraInfo camera1 = new CameraInfo();
        camera1.setIndexCode("camera001");
        camera1.setName("Camera 001");
        camera1.setCameraType(0);
        camera1.setCapability("10101");
        camera1.setChanNum(1);
        camera1.setRegionPath("org1@org2");
        camera1.setRegionPathName("Organization 1/Organization 2");
        camera1.setParentIndexCode("device001");

        CameraInfo camera2 = new CameraInfo();
        camera2.setIndexCode("camera002");
        camera2.setName("Camera 002");
        camera2.setCameraType(1);
        camera2.setCapability("10110");
        camera2.setChanNum(2);
        camera2.setRegionPath("org1@org3");
        camera2.setRegionPathName("Organization 1/Organization 3");
        camera2.setParentIndexCode("device002");

        List<CameraInfo> cameraList = List.of(camera1, camera2);

        // Mock在线状态 - 只包含一个摄像头的在线状态
        CameraOnlineInfo onlineInfo1 = new CameraOnlineInfo();
        onlineInfo1.setIndexCode("camera001");
        onlineInfo1.setOnline(1);

        List<CameraOnlineInfo> onlineList = List.of(onlineInfo1);

        // Mock SDK响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(cameraList);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any()))
                .thenReturn(onlineList);

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(2, result.size());

        // 验证第一个摄像头的在线状态
        CameraFetchInfoBo cameraBo1 = result.stream()
                .filter(c -> "camera001".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(cameraBo1);
        assertEquals(1, cameraBo1.getOnlineStatus());

        // 验证第二个摄像头的在线状态（应该使用默认值0）
        CameraFetchInfoBo cameraBo2 = result.stream()
                .filter(c -> "camera002".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(cameraBo2);
        assertEquals(0, cameraBo2.getOnlineStatus());
    }

    @Test
    void findAllCameraChannelList_WithDifferentCapabilityValues() {
        // 准备测试数据
        Integer areaId = 1;
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // Mock摄像头列表
        CameraInfo camera1 = new CameraInfo();
        camera1.setIndexCode("camera001");
        camera1.setName("Camera 001");
        camera1.setCameraType(0);
        camera1.setCapability("unknown_capability"); // 未知的能力值
        camera1.setChanNum(1);
        camera1.setRegionPath("org1@org2");
        camera1.setRegionPathName("Organization 1/Organization 2");
        camera1.setParentIndexCode("device001");

        CameraInfo camera2 = new CameraInfo();
        camera2.setIndexCode("camera002");
        camera2.setName("Camera 002");
        camera2.setCameraType(1);
        camera2.setCapability(null); // 空的能力值
        camera2.setChanNum(2);
        camera2.setRegionPath("org1@org3");
        camera2.setRegionPathName("Organization 1/Organization 3");
        camera2.setParentIndexCode("device002");

        List<CameraInfo> cameraList = List.of(camera1, camera2);

        // Mock在线状态
        CameraOnlineInfo onlineInfo1 = new CameraOnlineInfo();
        onlineInfo1.setIndexCode("camera001");
        onlineInfo1.setOnline(1);

        CameraOnlineInfo onlineInfo2 = new CameraOnlineInfo();
        onlineInfo2.setIndexCode("camera002");
        onlineInfo2.setOnline(1);

        List<CameraOnlineInfo> onlineList = List.of(onlineInfo1, onlineInfo2);

        // Mock SDK响应
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraSearchRequest.class), any()))
                .thenReturn(cameraList);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(CameraOnlineSearchRequest.class), any()))
                .thenReturn(onlineList);

        // 执行测试
        List<CameraFetchInfoBo> result = cameraImpl.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(2, result.size());

        // 验证第一个摄像头的设备能力
        CameraFetchInfoBo cameraBo1 = result.stream()
                .filter(c -> "camera001".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(cameraBo1);
        assertEquals(0, cameraBo1.getDeviceCapacity().size());

        // 验证第二个摄像头的设备能力
        CameraFetchInfoBo cameraBo2 = result.stream()
                .filter(c -> "camera002".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(cameraBo2);
        assertEquals(0, cameraBo2.getDeviceCapacity().size());
    }

    @Test
    void getCameraRealTimeLink_SdkException() {
        // 准备测试数据
        String channelCode = "camera001";
        String errorMessage = "Failed to get real time link";

        CameraRealtimeQueryBo queryBo = new CameraRealtimeQueryBo();
        queryBo.setAreaId(1);
        queryBo.setChannelCode(channelCode);
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStreamType(CameraStreamTypeEnum.MAIN);

        // Mock SDK异常
        when(iSecureV2SDK.realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class)))
                .thenThrow(new RuntimeException(errorMessage));

        // 执行测试并验证异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cameraImpl.getCameraRealTimeLink(queryBo);
        });
        assertEquals(errorMessage, exception.getMessage());

        // 验证调用
        verify(iSecureV2SDK).realTimeLink(eq(mockConfig), any(RealTimeLinkRequest.class));
    }

    @Test
    void controlDirection_InvalidOperation() {
        // 准备测试数据
        String channelCode = "camera001";
        CameraOperateBo operateBo = new CameraOperateBo();
        operateBo.setAreaId(1);
        operateBo.setChannelCode(channelCode);
        operateBo.setOperation(null);
        operateBo.setAction(true);
        operateBo.setSpeed(5);

        // 执行测试并验证异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cameraImpl.controlDirection(operateBo);
        });
        assertTrue(exception.getMessage().contains("操作不能为空"));

        // 验证调用
        verify(iSecureV2SDK, never()).cameraControl(any(), any());
    }

}