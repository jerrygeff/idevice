package info.zhihui.idevice.web.camera.biz;

import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraFetchInfoBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraOperateBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraPlaybackLinkBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraPlaybackQueryBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraQueryBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraRealtimeLinkBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraRealtimeQueryBo;
import info.zhihui.idevice.core.module.concrete.camera.service.Camera;
import info.zhihui.idevice.web.camera.mapstruct.CameraWebMapper;
import info.zhihui.idevice.web.camera.vo.*;
import info.zhihui.idevice.web.camera.vo.CameraFetchInfoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CameraBizServiceTest {

    @InjectMocks
    private CameraBizService cameraBizService;

    @Mock
    private CameraWebMapper cameraWebMapper;

    @Mock
    private DeviceModuleContext deviceModuleContext;

    @Mock
    private Camera cameraService;

    private static final Integer AREA_ID = 1;

    @BeforeEach
    public void setup() {
        when(deviceModuleContext.getService(eq(Camera.class), any(Integer.class)))
                .thenReturn(cameraService);
    }

    @Test
    public void testGetCameraRealTimeLink() {
        // 准备测试数据
        CameraRealtimeQueryVo queryVo = new CameraRealtimeQueryVo();
        queryVo.setAreaId(AREA_ID);

        // 请求BO
        CameraRealtimeQueryBo requestBo = new CameraRealtimeQueryBo();
        when(cameraWebMapper.cameraRealtimeQueryVoToBo(any())).thenReturn(requestBo);

        // 响应BO
        CameraRealtimeLinkBo responseBo = new CameraRealtimeLinkBo();
        when(cameraService.getCameraRealTimeLink(any(CameraRealtimeQueryBo.class))).thenReturn(responseBo);

        // 响应VO
        CameraLinkVo expectedVo = new CameraLinkVo();
        when(cameraWebMapper.cameraRealtimeLinkBoToVo(any(CameraRealtimeLinkBo.class))).thenReturn(expectedVo);

        // 执行方法
        CameraLinkVo result = cameraBizService.getCameraRealTimeLink(queryVo);

        // 验证交互
        verify(deviceModuleContext).getService(Camera.class, AREA_ID);
        verify(cameraWebMapper).cameraRealtimeQueryVoToBo(queryVo);
        verify(cameraService).getCameraRealTimeLink(requestBo);
        verify(cameraWebMapper).cameraRealtimeLinkBoToVo(responseBo);

        // 验证结果
        assertEquals(expectedVo, result);
    }

    @Test
    public void testGetCameraPlaybackLink() {
        // 准备测试数据
        CameraPlaybackQueryVo queryVo = new CameraPlaybackQueryVo();
        queryVo.setAreaId(AREA_ID);

        // 请求BO
        CameraPlaybackQueryBo requestBo = new CameraPlaybackQueryBo();
        when(cameraWebMapper.cameraPlaybackQueryVoToBo(any())).thenReturn(requestBo);

        // 响应BO
        CameraPlaybackLinkBo responseBo = new CameraPlaybackLinkBo();
        when(cameraService.getCameraPlaybackLink(any(CameraPlaybackQueryBo.class))).thenReturn(responseBo);

        // 响应VO
        CameraLinkVo expectedVo = new CameraLinkVo();
        when(cameraWebMapper.cameraPlaybackLinkBoToVo(any(CameraPlaybackLinkBo.class))).thenReturn(expectedVo);

        // 执行方法
        CameraLinkVo result = cameraBizService.getCameraPlaybackLink(queryVo);

        // 验证交互
        verify(deviceModuleContext).getService(Camera.class, AREA_ID);
        verify(cameraWebMapper).cameraPlaybackQueryVoToBo(queryVo);
        verify(cameraService).getCameraPlaybackLink(requestBo);
        verify(cameraWebMapper).cameraPlaybackLinkBoToVo(responseBo);

        // 验证结果
        assertEquals(expectedVo, result);
    }

    @Test
    public void testFindAllCameraChannelList() {
        // 准备测试数据
        CameraQueryVo queryVo = new CameraQueryVo();
        queryVo.setAreaId(AREA_ID);

        // 请求BO
        CameraQueryBo requestBo = new CameraQueryBo();
        when(cameraWebMapper.cameraRequestBaseVoToBo(any())).thenReturn(requestBo);

        // 响应BO列表
        CameraFetchInfoBo infoBoItem1 = new CameraFetchInfoBo();
        infoBoItem1.setThirdPartyDeviceUniqueCode("device1");
        CameraFetchInfoBo infoBoItem2 = new CameraFetchInfoBo();
        infoBoItem2.setThirdPartyDeviceUniqueCode("device2");
        List<CameraFetchInfoBo> infoBoList = List.of(infoBoItem1, infoBoItem2);
        when(cameraService.findAllCameraChannelList(any(CameraQueryBo.class))).thenReturn(infoBoList);

        // 响应VO列表
        CameraFetchInfoVo infoVoItem1 = new CameraFetchInfoVo();
        infoVoItem1.setDeviceCode("device1");
        CameraFetchInfoVo infoVoItem2 = new CameraFetchInfoVo();
        infoVoItem2.setDeviceCode("device2");
        when(cameraWebMapper.cameraFetchInfoBoToVo(infoBoItem1)).thenReturn(infoVoItem1);
        when(cameraWebMapper.cameraFetchInfoBoToVo(infoBoItem2)).thenReturn(infoVoItem2);

        // 执行方法
        List<CameraFetchInfoVo> result = cameraBizService.findAllCameraChannelList(queryVo);

        // 验证交互
        verify(deviceModuleContext).getService(Camera.class, AREA_ID);
        verify(cameraWebMapper).cameraRequestBaseVoToBo(queryVo);
        verify(cameraService).findAllCameraChannelList(requestBo);
        verify(cameraWebMapper).cameraFetchInfoBoToVo(infoBoItem1);
        verify(cameraWebMapper).cameraFetchInfoBoToVo(infoBoItem2);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(infoVoItem1, result.get(0));
        assertEquals(infoVoItem2, result.get(1));
    }

    @Test
    public void testControlDirection() {
        // 准备测试数据
        CameraOperateVo operateVo = new CameraOperateVo();
        operateVo.setAreaId(AREA_ID);

        CameraOperateBo operateBo = new CameraOperateBo();
        when(cameraWebMapper.cameraOperateVoToBo(any())).thenReturn(operateBo);

        // 执行方法
        cameraBizService.controlDirection(operateVo);

        // 验证交互
        verify(deviceModuleContext).getService(Camera.class, AREA_ID);
        verify(cameraWebMapper).cameraOperateVoToBo(operateVo);
        verify(cameraService).controlDirection(operateBo);
    }
}