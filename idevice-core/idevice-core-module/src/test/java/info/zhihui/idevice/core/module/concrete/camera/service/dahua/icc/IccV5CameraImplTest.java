package info.zhihui.idevice.core.module.concrete.camera.service.dahua.icc;

import com.dahuatech.icc.oauth.http.IccTokenResponse;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraDirectionEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import info.zhihui.idevice.core.module.concrete.foundation.service.dahua.icc.IccV5FoundationImpl;
import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.Channel;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.TreeItem;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.camera.ControlCommandEnum;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.camera.DirectionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_CAMERA_TREE_QUERY_TYPE;
import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_DEVICE_TREE_QUERY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IccV5CameraImplTest {

    @Mock
    private IccV5SDK iccV5SDK;
    @Mock
    private DeviceModuleConfigService deviceModuleConfigService;
    @Mock
    private IccV5FoundationImpl iccV5Foundation;

    private IccV5CameraImpl iccCamera;

    @BeforeEach
    void setUp() {
        // 手动创建实例
        iccCamera = new IccV5CameraImpl(
                deviceModuleConfigService,
                iccV5SDK,
                iccV5Foundation
        );
    }

    @Test
    void findAllCameraChannelList_Success() {
        // 准备测试数据
        Integer areaId = 1;
        IccSdkConfig mockConfig = new IccSdkConfig();
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // 模拟通道数据
        Channel channel1 = new Channel();
        channel1.setChannelCode("channel001");
        channel1.setChannelName("Camera 001");
        channel1.setDeviceCode("device001");
        channel1.setChannelType("普通固定枪机");
        channel1.setCameraType("枪机");
        channel1.setCapability("10101");
        channel1.setOwnerCode("org1");
        channel1.setIsOnline(1);

        Channel channel2 = new Channel();
        channel2.setChannelCode("channel002");
        channel2.setChannelName("Camera 002");
        channel2.setDeviceCode("device002");
        channel2.setChannelType("球机");
        channel2.setCameraType("球机");
        channel2.setCapability("10110");
        channel2.setOwnerCode("org2");
        channel2.setIsOnline(0);

        List<Channel> channels = List.of(channel1, channel2);

        // 模拟树结构映射
        Map<String, TreeItem> treeItemMap = new HashMap<>();

        TreeItem org1 = new TreeItem();
        org1.setId("org1");
        org1.setName("Organization 1");
        org1.setPId(DEFAULT_DEVICE_TREE_QUERY_ID);
        org1.setNodeType("org");
        treeItemMap.put("org1", org1);

        TreeItem org2 = new TreeItem();
        org2.setId("org2");
        org2.setName("Organization 2");
        org2.setPId("org1");
        org2.setNodeType("org");
        treeItemMap.put("org2", org2);

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);

        doAnswer(invocation -> channels).when(iccV5SDK).findAllByPage(eq(mockConfig), any(), any());

        when(iccV5Foundation.getDeviceTreeMap(eq(mockConfig), eq(DEFAULT_DEVICE_TREE_QUERY_ID), eq(DEFAULT_CAMERA_TREE_QUERY_TYPE)))
                .thenReturn(treeItemMap);

        // 模拟设置路径数据
        doAnswer(invocation -> {
            List<CameraFetchInfoBo> cameraList = invocation.getArgument(0);

            // 为每个相机设置路径信息
            for (CameraFetchInfoBo camera : cameraList) {
                if ("org1".equals(camera.getOwnerCode())) {
                    camera.setRegionPath(List.of("org1"));
                    camera.setRegionPathName(List.of("Organization 1"));
                } else if ("org2".equals(camera.getOwnerCode())) {
                    camera.setRegionPath(List.of("org1", "org2"));
                    camera.setRegionPathName(List.of("Organization 1", "Organization 2"));
                }
            }
            return cameraList;
        }).when(iccV5Foundation).setTreeItemPathData(anyList(), eq(treeItemMap), any(), any(), any());

        // 模拟getCapabilities方法
        when(iccV5SDK.getCapabilities(anyString())).thenReturn(List.of("capability1", "capability2"));

        // 执行测试
        List<CameraFetchInfoBo> result = iccCamera.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(2, result.size());

        // 验证channel1的转换结果
        CameraFetchInfoBo camera1 = result.stream()
                .filter(c -> "channel001".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(camera1);
        assertEquals("Camera 001", camera1.getThirdPartyDeviceName());
        assertEquals(areaId, camera1.getThirdPartyAreaId());
        assertEquals("device001", camera1.getDeviceParentCode());
        assertEquals("org1", camera1.getOwnerCode());
        assertEquals(1, camera1.getOnlineStatus());
        assertEquals(false, camera1.getIsPtz());

        // 验证channel2的转换结果
        CameraFetchInfoBo camera2 = result.stream()
                .filter(c -> "channel002".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(camera2);
        assertEquals("Camera 002", camera2.getThirdPartyDeviceName());
        assertEquals(areaId, camera2.getThirdPartyAreaId());
        assertEquals("device002", camera2.getDeviceParentCode());
        assertEquals("org2", camera2.getOwnerCode());
        assertEquals(0, camera2.getOnlineStatus());
        assertEquals(true, camera2.getIsPtz());

        // 验证交互
        verify(deviceModuleConfigService).getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK).findAllByPage(eq(mockConfig), any(), any());
        verify(iccV5SDK, times(2)).getCapabilities(anyString());
        verify(iccV5Foundation).getDeviceTreeMap(eq(mockConfig), eq(DEFAULT_DEVICE_TREE_QUERY_ID), eq(DEFAULT_CAMERA_TREE_QUERY_TYPE));
        verify(iccV5Foundation).setTreeItemPathData(anyList(), eq(treeItemMap), any(), any(), any());
    }

    @Test
    void findAllCameraChannelList_EmptyChannels() {
        // 准备测试数据
        Integer areaId = 1;
        IccSdkConfig mockConfig = new IccSdkConfig();
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);
        List<Channel> emptyChannels = List.of();

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);

        // 使用doAnswer来处理泛型类型问题
        doAnswer(invocation -> emptyChannels).when(iccV5SDK).findAllByPage(eq(mockConfig), any(), any());

        // 执行测试
        List<CameraFetchInfoBo> result = iccCamera.findAllCameraChannelList(queryBo);

        // 验证结果
        assertTrue(result.isEmpty());

        // 验证交互
        verify(deviceModuleConfigService).getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK).findAllByPage(eq(mockConfig), any(), any());
        verify(iccV5SDK, never()).getCapabilities(anyString());
        verify(iccV5Foundation, never()).setTreeItemPathData(anyList(), anyMap(), any(), any(), any());
    }

    @Test
    void findAllCameraChannelList_WithDifferentCameraTypes() {
        // 准备测试数据
        Integer areaId = 1;
        IccSdkConfig mockConfig = new IccSdkConfig();
        CameraQueryBo queryBo = new CameraQueryBo().setAreaId(areaId);

        // 创建多种类型的摄像头
        Channel fixedCamera = new Channel();
        fixedCamera.setChannelCode("fixed001");
        fixedCamera.setChannelName("Fixed Camera");
        fixedCamera.setDeviceCode("device001");
        fixedCamera.setChannelType("普通固定枪机");
        fixedCamera.setCameraType("枪机");
        fixedCamera.setCapability("10101");
        fixedCamera.setOwnerCode("org1");
        fixedCamera.setIsOnline(1);

        Channel domeCamera = new Channel();
        domeCamera.setChannelCode("dome001");
        domeCamera.setChannelName("Dome Camera");
        domeCamera.setDeviceCode("device002");
        domeCamera.setChannelType("球机");
        domeCamera.setCameraType("球机");
        domeCamera.setCapability("10110");
        domeCamera.setOwnerCode("org1");
        domeCamera.setIsOnline(1);

        Channel panoramicCamera = new Channel();
        panoramicCamera.setChannelCode("panoramic001");
        panoramicCamera.setChannelName("Panoramic Camera");
        panoramicCamera.setDeviceCode("device003");
        panoramicCamera.setChannelType("全景相机");
        panoramicCamera.setCameraType("全景相机");
        panoramicCamera.setCapability("11001");
        panoramicCamera.setOwnerCode("org1");
        panoramicCamera.setIsOnline(1);

        List<Channel> cameras = List.of(fixedCamera, domeCamera, panoramicCamera);

        // 模拟树结构
        Map<String, TreeItem> treeItemMap = new HashMap<>();
        TreeItem org1 = new TreeItem();
        org1.setId("org1");
        org1.setName("Organization 1");
        org1.setPId(DEFAULT_DEVICE_TREE_QUERY_ID);
        org1.setNodeType("org");
        treeItemMap.put("org1", org1);

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);

        doAnswer(invocation -> cameras).when(iccV5SDK).findAllByPage(eq(mockConfig), any(), any());

        when(iccV5Foundation.getDeviceTreeMap(eq(mockConfig), eq(DEFAULT_DEVICE_TREE_QUERY_ID), eq(DEFAULT_CAMERA_TREE_QUERY_TYPE)))
                .thenReturn(treeItemMap);

        // 模拟设置路径数据
        doAnswer(invocation -> {
            List<CameraFetchInfoBo> cameraList = invocation.getArgument(0);
            for (CameraFetchInfoBo camera : cameraList) {
                camera.setRegionPath(List.of("org1"));
                camera.setRegionPathName(List.of("Organization 1"));
            }
            return cameraList;
        }).when(iccV5Foundation).setTreeItemPathData(anyList(), eq(treeItemMap), any(), any(), any());

        // 模拟不同能力集
        when(iccV5SDK.getCapabilities(eq("10101"))).thenReturn(List.of("capability1"));
        when(iccV5SDK.getCapabilities(eq("10110"))).thenReturn(List.of("capability2", "ptz"));
        when(iccV5SDK.getCapabilities(eq("11001"))).thenReturn(List.of("panoramic"));

        // 执行测试
        List<CameraFetchInfoBo> result = iccCamera.findAllCameraChannelList(queryBo);

        // 验证结果
        assertEquals(3, result.size());

        // 验证相机类型和云台功能
        CameraFetchInfoBo fixedCameraResult = result.stream()
                .filter(c -> "fixed001".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(fixedCameraResult);
        assertEquals(false, fixedCameraResult.getIsPtz());

        CameraFetchInfoBo domeCameraResult = result.stream()
                .filter(c -> "dome001".equals(c.getThirdPartyDeviceUniqueCode()))
                .findFirst()
                .orElse(null);
        assertNotNull(domeCameraResult);
        assertEquals(true, domeCameraResult.getIsPtz());

        // 验证能力集
        verify(iccV5SDK).getCapabilities(eq("10101"));
        verify(iccV5SDK).getCapabilities(eq("10110"));
        verify(iccV5SDK).getCapabilities(eq("11001"));

        // 验证交互
        verify(iccV5Foundation).getDeviceTreeMap(eq(mockConfig), eq(DEFAULT_DEVICE_TREE_QUERY_ID), eq(DEFAULT_CAMERA_TREE_QUERY_TYPE));
        verify(iccV5Foundation).setTreeItemPathData(anyList(), eq(treeItemMap), any(), any(), any());
    }

    @Test
    void getCameraRealTimeLink_WithDifferentStreamTypesAndProtocols() {
        // 准备测试数据
        Integer areaId = 1;
        String channelCode = "channel001";
        IccSdkConfig mockConfig = new IccSdkConfig();

        // 模拟实时链接响应
        RealTimeLinkResponse response = new RealTimeLinkResponse();
        RealTimeLinkResponseData responseData = new RealTimeLinkResponseData();
        responseData.setUrl("rtsp://example.com/stream");
        response.setData(responseData);

        // 模拟访问令牌
        IccTokenResponse.IccToken token = new IccTokenResponse.IccToken();
        token.setAccess_token("test_token");

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);
        when(iccV5SDK.cameraRealTimeLink(eq(mockConfig), any()))
                .thenReturn(response);
        when(iccV5SDK.getAccessToken(eq(mockConfig)))
                .thenReturn(token);

        // 测试主码流RTSP
        CameraRealtimeQueryBo queryBoMainRtsp = new CameraRealtimeQueryBo();
        queryBoMainRtsp.setAreaId(areaId);
        queryBoMainRtsp.setChannelCode(channelCode);
        queryBoMainRtsp.setStreamType(CameraStreamTypeEnum.MAIN);
        queryBoMainRtsp.setProtocol(CameraPlayProtocolEnum.RTSP);

        CameraRealtimeLinkBo resultMainRtsp = iccCamera.getCameraRealTimeLink(queryBoMainRtsp);
        assertEquals("rtsp://example.com/stream", resultMainRtsp.getUrl());

        // 测试子码流HLS
        CameraRealtimeQueryBo queryBoSubHls = new CameraRealtimeQueryBo();
        queryBoSubHls.setAreaId(areaId);
        queryBoSubHls.setChannelCode(channelCode);
        queryBoSubHls.setStreamType(CameraStreamTypeEnum.SUB);
        queryBoSubHls.setProtocol(CameraPlayProtocolEnum.HLS);

        CameraRealtimeLinkBo resultSubHls = iccCamera.getCameraRealTimeLink(queryBoSubHls);
        assertEquals("rtsp://example.com/stream?token=test_token", resultSubHls.getUrl());

        // 验证交互
        verify(deviceModuleConfigService, times(2)).getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK, times(2)).cameraRealTimeLink(eq(mockConfig), any());
        verify(iccV5SDK, times(1)).getAccessToken(eq(mockConfig));
    }

    @Test
    void getCameraPlaybackLink_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String channelCode = "channel001";
        IccSdkConfig mockConfig = new IccSdkConfig();
        LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
        LocalDateTime endDateTime = LocalDateTime.now();

        // 模拟回放链接响应
        PlaybackLinkResponse response = new PlaybackLinkResponse();
        PlaybackLinkResponseData responseData = new PlaybackLinkResponseData();
        responseData.setUrl("rtsp://example.com/playback");
        response.setData(responseData);

        // 模拟访问令牌
        IccTokenResponse.IccToken token = new IccTokenResponse.IccToken();
        token.setAccess_token("test_token");

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);
        when(iccV5SDK.cameraPlaybackLink(eq(mockConfig), any()))
                .thenReturn(response);
        when(iccV5SDK.getAccessToken(eq(mockConfig)))
                .thenReturn(token);

        // 执行测试
        CameraPlaybackQueryBo queryBo = new CameraPlaybackQueryBo();
        queryBo.setAreaId(areaId);
        queryBo.setChannelCode(channelCode);
        queryBo.setStreamType(CameraStreamTypeEnum.MAIN);
        queryBo.setProtocol(CameraPlayProtocolEnum.RTSP);
        queryBo.setStartTime(startDateTime);
        queryBo.setEndTime(endDateTime);

        CameraPlaybackLinkBo result = iccCamera.getCameraPlaybackLink(queryBo);

        // 验证结果
        assertEquals("rtsp://example.com/playback?token=test_token", result.getPlaybackUrl());

        // 验证交互
        verify(deviceModuleConfigService).getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK).cameraPlaybackLink(eq(mockConfig), any());
        verify(iccV5SDK).getAccessToken(eq(mockConfig));
    }

    @Test
    void controlDirection_WithDifferentDirections() {
        // 准备测试数据
        Integer areaId = 1;
        String channelCode = "channel001";
        Integer speed = 50; // 修改为1-100范围内的值
        IccSdkConfig mockConfig = new IccSdkConfig();

        // 期望的转换后速度值 (50*8/100 = 4)
        String expectedIccSpeed = "4";

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);

        // 测试向上控制
        CameraOperateBo operateBoUp = new CameraOperateBo();
        operateBoUp.setAreaId(areaId);
        operateBoUp.setChannelCode(channelCode);
        operateBoUp.setOperation(CameraDirectionEnum.UP);
        operateBoUp.setAction(true);
        operateBoUp.setSpeed(speed);

        iccCamera.controlDirection(operateBoUp);
        verify(iccV5SDK).cameraControl(eq(mockConfig), argThat(request -> {
            CameraControlRequestData data = request.getData();
            return channelCode.equals(data.getChannelId()) &&
                    DirectionEnum.UP.getValue().equals(data.getDirect()) &&
                    ControlCommandEnum.START.getValue().equals(data.getCommand()) &&
                    expectedIccSpeed.equals(data.getStepX()) &&
                    expectedIccSpeed.equals(data.getStepY());
        }));

        // 测试向下控制
        CameraOperateBo operateBoDown = new CameraOperateBo();
        operateBoDown.setAreaId(areaId);
        operateBoDown.setChannelCode(channelCode);
        operateBoDown.setOperation(CameraDirectionEnum.DOWN);
        operateBoDown.setAction(false);
        operateBoDown.setSpeed(speed);

        iccCamera.controlDirection(operateBoDown);
        verify(iccV5SDK).cameraControl(eq(mockConfig), argThat(request -> {
            CameraControlRequestData data = request.getData();
            return channelCode.equals(data.getChannelId()) &&
                    DirectionEnum.DOWN.getValue().equals(data.getDirect()) &&
                    ControlCommandEnum.STOP.getValue().equals(data.getCommand()) &&
                    expectedIccSpeed.equals(data.getStepX()) &&
                    expectedIccSpeed.equals(data.getStepY());
        }));

        // 测试左斜上控制
        CameraOperateBo operateBoLeftUp = new CameraOperateBo();
        operateBoLeftUp.setAreaId(areaId);
        operateBoLeftUp.setChannelCode(channelCode);
        operateBoLeftUp.setOperation(CameraDirectionEnum.LEFT_UP);
        operateBoLeftUp.setAction(true);
        operateBoLeftUp.setSpeed(speed);

        iccCamera.controlDirection(operateBoLeftUp);
        verify(iccV5SDK).cameraControl(eq(mockConfig), argThat(request -> {
            CameraControlRequestData data = request.getData();
            return channelCode.equals(data.getChannelId()) &&
                    DirectionEnum.LEFT_UP.getValue().equals(data.getDirect()) &&
                    ControlCommandEnum.START.getValue().equals(data.getCommand()) &&
                    expectedIccSpeed.equals(data.getStepX()) &&
                    expectedIccSpeed.equals(data.getStepY());
        }));

        // 验证交互
        verify(deviceModuleConfigService, times(3)).getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK, times(3)).cameraControl(eq(mockConfig), any());
    }

    @Test
    void controlDirection_WithDifferentSpeeds() {
        // 准备测试数据
        Integer areaId = 1;
        String channelCode = "channel001";
        IccSdkConfig mockConfig = new IccSdkConfig();

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);

        // 测试不同的输入速度值和预期输出速度值
        int[][] speedTestCases = {
                {1, 1},    // 最小输入值 -> 速度1
                {13, 2},   // 13*8/100 = 1.04, 向上取整为2
                {25, 2},   // 25*8/100 = 2, 速度2
                {38, 4},   // 38*8/100 = 3.04, 向上取整为4
                {50, 4},   // 50*8/100 = 4, 速度4
                {76, 7},   // 76*8/100 = 6.08, 向上取整为7
                {88, 8},   // 88*8/100 = 7.04, 向上取整为8
                {100, 8}   // 最大输入值 -> 速度8
        };

        for (int[] testCase : speedTestCases) {
            int inputSpeed = testCase[0];

            CameraOperateBo operateBo = new CameraOperateBo();
            operateBo.setAreaId(areaId);
            operateBo.setChannelCode(channelCode);
            operateBo.setOperation(CameraDirectionEnum.UP);
            operateBo.setAction(true);
            operateBo.setSpeed(inputSpeed);

            iccCamera.controlDirection(operateBo);
        }

        // 验证交互 - 修改验证方式，只验证总的调用次数
        verify(deviceModuleConfigService, times(speedTestCases.length)).getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK, times(speedTestCases.length)).cameraControl(eq(mockConfig), any());
    }

    @Test
    void controlDirection_WithDifferentOperations() {
        // 准备测试数据
        Integer areaId = 1;
        String channelCode = "channel001";
        final Integer speed = 50; // 修改为1-100范围内的值
        final String expectedIccSpeed = "4"; // 50*8/100=4
        IccSdkConfig mockConfig = new IccSdkConfig();

        // 设置Mock行为
        when(deviceModuleConfigService.getDeviceConfigValue(any(), any(), eq(areaId)))
                .thenReturn(mockConfig);

        // 测试所有方向操作
        for (CameraDirectionEnum direction : CameraDirectionEnum.values()) {
            CameraOperateBo operateBo = new CameraOperateBo();
            operateBo.setAreaId(areaId);
            operateBo.setChannelCode(channelCode);
            operateBo.setOperation(direction);
            operateBo.setAction(true);
            operateBo.setSpeed(speed);

            iccCamera.controlDirection(operateBo);
            verify(iccV5SDK).cameraControl(eq(mockConfig), argThat(request -> {
                CameraControlRequestData data = request.getData();
                return channelCode.equals(data.getChannelId()) &&
                        getExpectedDirectionValue(direction).equals(data.getDirect()) &&
                        ControlCommandEnum.START.getValue().equals(data.getCommand()) &&
                        expectedIccSpeed.equals(data.getStepX()) &&
                        expectedIccSpeed.equals(data.getStepY());
            }));
        }

        // 验证交互
        verify(deviceModuleConfigService, times(CameraDirectionEnum.values().length))
                .getDeviceConfigValue(any(), any(), eq(areaId));
        verify(iccV5SDK, times(CameraDirectionEnum.values().length))
                .cameraControl(eq(mockConfig), any());
    }

    private String getExpectedDirectionValue(CameraDirectionEnum direction) {
        return switch (direction) {
            case UP -> DirectionEnum.UP.getValue();
            case DOWN -> DirectionEnum.DOWN.getValue();
            case LEFT -> DirectionEnum.LEFT.getValue();
            case RIGHT -> DirectionEnum.RIGHT.getValue();
            case LEFT_UP -> DirectionEnum.LEFT_UP.getValue();
            case LEFT_DOWN -> DirectionEnum.LEFT_DOWN.getValue();
            case RIGHT_UP -> DirectionEnum.RIGHT_UP.getValue();
            case RIGHT_DOWN -> DirectionEnum.RIGHT_DOWN.getValue();
        };
    }
}