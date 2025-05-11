package info.zhihui.idevice.web.camera.biz;

import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraFetchInfoBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraPlaybackLinkBo;
import info.zhihui.idevice.core.module.concrete.camera.bo.CameraRealtimeLinkBo;
import info.zhihui.idevice.core.module.concrete.camera.service.Camera;
import info.zhihui.idevice.web.camera.mapstruct.CameraWebMapper;
import info.zhihui.idevice.web.camera.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 摄像头业务服务
 */
@Service
@RequiredArgsConstructor
public class CameraBizService {

    private final CameraWebMapper cameraWebMapper;
    private final DeviceModuleContext deviceModuleContext;

    /**
     * 获取摄像头服务
     */
    private Camera getCameraService(Integer areaId) {
        return deviceModuleContext.getService(Camera.class, areaId);
    }

    /**
     * 获取实时取流地址
     */
    public CameraLinkVo getCameraRealTimeLink(CameraRealtimeQueryVo realtimeQueryVo) {
        Camera cameraService = getCameraService(realtimeQueryVo.getAreaId());

        CameraRealtimeLinkBo cameraRealtimeLink = cameraService.getCameraRealTimeLink(cameraWebMapper.cameraRealtimeQueryVoToBo(realtimeQueryVo));
        return cameraWebMapper.cameraRealtimeLinkBoToVo(cameraRealtimeLink);
    }

    /**
     * 获取回放取流地址
     */
    public CameraLinkVo getCameraPlaybackLink(CameraPlaybackQueryVo playbackQueryVo) {
        Camera cameraService = getCameraService(playbackQueryVo.getAreaId());

        CameraPlaybackLinkBo cameraPlaybackLink = cameraService.getCameraPlaybackLink(cameraWebMapper.cameraPlaybackQueryVoToBo(playbackQueryVo));
        return cameraWebMapper.cameraPlaybackLinkBoToVo(cameraPlaybackLink);
    }

    /**
     * 获取全部摄像头通道列表
     */
    public List<CameraFetchInfoVo> findAllCameraChannelList(CameraQueryVo requestBaseVo) {
        Camera cameraService = getCameraService(requestBaseVo.getAreaId());
        List<CameraFetchInfoBo> cameraFetchInfoBoList = cameraService.findAllCameraChannelList(
                cameraWebMapper.cameraRequestBaseVoToBo(requestBaseVo)
        );

        return cameraFetchInfoBoList.stream()
                .map(cameraWebMapper::cameraFetchInfoBoToVo)
                .collect(Collectors.toList());
    }

    /**
     * 控制摄像头方向
     */
    public void controlDirection(CameraOperateVo cameraOperateVo) {
        Camera cameraService = getCameraService(cameraOperateVo.getAreaId());
        cameraService.controlDirection(cameraWebMapper.cameraOperateVoToBo(cameraOperateVo));
    }
}