package info.zhihui.idevice.core.module.concrete.camera.service;

import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author jerryge
 */
@Service
@Slf4j
public class MockCamera implements Camera {
    @Override
    public CameraRealtimeLinkBo getCameraRealTimeLink(CameraRealtimeQueryBo realTimeQueryBo) {
        CameraRealtimeLinkBo linkBo = new CameraRealtimeLinkBo();
        
        String protocol = realTimeQueryBo.getProtocol() != null ? 
                realTimeQueryBo.getProtocol().getValue() : CameraPlayProtocolEnum.RTSP.getValue();
        String channelCode = realTimeQueryBo.getChannelCode() != null ? 
                realTimeQueryBo.getChannelCode() : "cam001";
        Integer streamType = realTimeQueryBo.getStreamType() != null ? 
                realTimeQueryBo.getStreamType().getCode() : CameraStreamTypeEnum.MAIN.getCode();
        
        String url = String.format("%s://192.168.1.100:554/live/%s/stream_%d?auth=mock_token_%s", 
                protocol, channelCode, streamType, UUID.randomUUID().toString().substring(0, 8));
        
        return linkBo.setUrl(url);
    }

    @Override
    public CameraPlaybackLinkBo getCameraPlaybackLink(CameraPlaybackQueryBo playbackQueryBo) {
        CameraPlaybackLinkBo linkBo = new CameraPlaybackLinkBo();
        
        String protocol = playbackQueryBo.getProtocol() != null ? 
                playbackQueryBo.getProtocol().getValue() : CameraPlayProtocolEnum.RTSP.getValue();
        String channelCode = playbackQueryBo.getChannelCode() != null ? 
                playbackQueryBo.getChannelCode() : "cam001";
        
        // 格式化开始和结束时间
        String startTime = playbackQueryBo.getStartTime() != null ? 
                SysDateUtil.toDateTimeString(playbackQueryBo.getStartTime()) : "2023-01-01 00:00:00";
        String endTime = playbackQueryBo.getEndTime() != null ? 
                SysDateUtil.toDateTimeString(playbackQueryBo.getEndTime()) : "2023-01-01 01:00:00";
        
        String playbackUrl = String.format("%s://192.168.1.100:554/playback/%s?start=%s&end=%s&auth=mock_token_%s", 
                protocol, channelCode, startTime, endTime, UUID.randomUUID().toString().substring(0, 8));
        
        return linkBo.setPlaybackUrl(playbackUrl);
    }

    @Override
    public List<CameraFetchInfoBo> findAllCameraChannelList(CameraQueryBo queryBo) {
        List<CameraFetchInfoBo> cameraList = new ArrayList<>();
        
        // 模拟三个不同类型的摄像头
        cameraList.add(createMockCamera("cam001", "前门监控", CameraTypeEnum.GUN_CAMERA, true, "NVR001", 1, "org001"));
        cameraList.add(createMockCamera("cam002", "大厅监控", CameraTypeEnum.HEMISPHERE_CAMERA, false, "NVR001", 2, "org001"));
        cameraList.add(createMockCamera("cam003", "后门监控", CameraTypeEnum.SPEED_DOME_CAMERA, true, "NVR002", 1, "org002"));
        
        return cameraList;
    }

    @Override
    public void controlDirection(CameraOperateBo cameraOperateBo) {
        log.info("mock camera control direction, param: {}", cameraOperateBo);
    }
    
    /**
     * 创建模拟摄像头信息
     */
    private CameraFetchInfoBo createMockCamera(String code, String name, CameraTypeEnum type, 
                                             Boolean isPtz, String parentCode, Integer channelSeq, 
                                             String ownerCode) {
        CameraFetchInfoBo camera = new CameraFetchInfoBo();
        // 设置基本信息
        camera.setThirdPartyDeviceUniqueCode(code);
        camera.setThirdPartyDeviceName(name);
        camera.setOnlineStatus(1);
        
        // 设置摄像头特有信息
        camera.setCameraType(type.getCode());
        camera.setIsPtz(isPtz);
        camera.setDeviceParentCode(parentCode);
        camera.setChannelSeq(channelSeq);
        camera.setOwnerCode(ownerCode);
        camera.setDeviceCapacity(Arrays.asList("video", "audio", isPtz ? "ptz" : ""));
        camera.setRegionPath(Arrays.asList("region1", "region2"));
        camera.setRegionPathName(Arrays.asList("区域1", "区域2"));
        
        return camera;
    }
}
