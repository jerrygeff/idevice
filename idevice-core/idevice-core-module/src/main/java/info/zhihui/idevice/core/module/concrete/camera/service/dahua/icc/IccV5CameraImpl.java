package info.zhihui.idevice.core.module.concrete.camera.service.dahua.icc;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dahuatech.icc.oauth.http.IccTokenResponse;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraDirectionEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraTypeEnum;
import info.zhihui.idevice.core.module.concrete.camera.service.Camera;
import info.zhihui.idevice.core.module.concrete.foundation.service.dahua.icc.IccV5FoundationImpl;
import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmChannelPageRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.Channel;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.TreeItem;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.brm.UnitTypeEnum;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.camera.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_DEVICE_TREE_QUERY_ID;
import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_CAMERA_TREE_QUERY_TYPE;

@Service
public class IccV5CameraImpl implements Camera {

    private final IccV5SDK iccV5SDK;
    private final DeviceModuleConfigService deviceModuleConfigService;
    private final IccV5FoundationImpl iccV5Foundation;

    private static final String PLAY_LINK_FORMAT = "%s?token=%s";

    @Autowired
    public IccV5CameraImpl(DeviceModuleConfigService deviceModuleConfigService,
                           IccV5SDK iccV5SDK,
                           IccV5FoundationImpl iccV5Foundation
    ) {
        this.deviceModuleConfigService = deviceModuleConfigService;
        this.iccV5SDK = iccV5SDK;
        this.iccV5Foundation = iccV5Foundation;
    }

    @Override
    public CameraRealtimeLinkBo getCameraRealTimeLink(CameraRealtimeQueryBo realTimeQueryBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(Camera.class, IccSdkConfig.class, realTimeQueryBo.getAreaId());

        RealTimeLinkRequest request = RealTimeLinkRequest.builder()
                .channelId(realTimeQueryBo.getChannelCode())
                .streamType(realTimeQueryBo.getStreamType().getCode().toString())
                .type(realTimeQueryBo.getProtocol().getValue())
                .build();
        RealTimeLinkResponse response = iccV5SDK.cameraRealTimeLink(config, request);

        if (CameraPlayProtocolEnum.RTSP.equals(realTimeQueryBo.getProtocol())) {
            return new CameraRealtimeLinkBo()
                    .setUrl(response.getData().getUrl());
        } else {
            // 非rtsp需要拼接access_token
            IccTokenResponse.IccToken iccToken = iccV5SDK.getAccessToken(config);

            return new CameraRealtimeLinkBo()
                    .setUrl(String.format(PLAY_LINK_FORMAT, response.getData().getUrl(), iccToken.getAccess_token()));
        }
    }

    @Override
    public CameraPlaybackLinkBo getCameraPlaybackLink(CameraPlaybackQueryBo playbackQueryBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(Camera.class, IccSdkConfig.class, playbackQueryBo.getAreaId());

        PlaybackLinkRequest request = PlaybackLinkRequest.builder()
                .channelId(playbackQueryBo.getChannelCode())
                .streamType(playbackQueryBo.getStreamType().getCode().toString())
                .type(playbackQueryBo.getProtocol().getValue())
                .recordType(RecordTypeEnum.NORMAL.getValue()) // 固定设置
                .recordSource(RecordSourceEnum.DEVICE.getValue()) // 固定设置
                .beginTime(SysDateUtil.toDateTimeString(playbackQueryBo.getStartTime()))
                .endTime(SysDateUtil.toDateTimeString(playbackQueryBo.getEndTime()))
                .build();
        PlaybackLinkResponse response = iccV5SDK.cameraPlaybackLink(config, request);

        IccTokenResponse.IccToken iccToken = iccV5SDK.getAccessToken(config);

        return new CameraPlaybackLinkBo()
                .setPlaybackUrl(String.format(PLAY_LINK_FORMAT, response.getData().getUrl(), iccToken.getAccess_token()));
    }

    @Override
    public List<CameraFetchInfoBo> findAllCameraChannelList(CameraQueryBo queryBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(Camera.class, IccSdkConfig.class, queryBo.getAreaId());

        // 获取所有视频通道
        BrmChannelPageRequest pageRequest = new BrmChannelPageRequest();
        pageRequest.setUnitTypeList(List.of(UnitTypeEnum.VIDEO_CHANNEL.getCode()));
        List<Channel> channels = iccV5SDK.findAllByPage(config, pageRequest, iccV5SDK::brmChannelPage);
        if (CollectionUtils.isEmpty(channels)) {
            return List.of();
        }

        // 获取完整设备树并构建映射
        Map<String, TreeItem> treeItemMap = iccV5Foundation.getDeviceTreeMap(config, DEFAULT_DEVICE_TREE_QUERY_ID, DEFAULT_CAMERA_TREE_QUERY_TYPE);

        // 将通道信息转换为摄像头信息
        List<CameraFetchInfoBo> cameraList = convertChannelListToCameraFetchInfoBoList(channels, queryBo.getAreaId());

        // 设置组织路径信息
        return iccV5Foundation.setTreeItemPathData(
                cameraList,
                treeItemMap,
                CameraFetchInfoBo::getOwnerCode,
                CameraFetchInfoBo::setRegionPath,
                CameraFetchInfoBo::setRegionPathName
        );
    }

    /**
     * 将通道列表转换为摄像头信息列表
     *
     * @param channels 通道列表
     * @param areaId   区域ID
     * @return 摄像头信息列表
     */
    private List<CameraFetchInfoBo> convertChannelListToCameraFetchInfoBoList(List<Channel> channels, Integer areaId) {
        List<CameraFetchInfoBo> cameraFetchInfoBoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(channels)) {
            for (Channel channel : channels) {
                CameraFetchInfoBo cameraFetchInfoBo = new CameraFetchInfoBo();
                cameraFetchInfoBo.setThirdPartyDeviceName(channel.getChannelName());
                cameraFetchInfoBo.setThirdPartyDeviceUniqueCode(channel.getChannelCode());
                cameraFetchInfoBo.setThirdPartyAreaId(areaId);
                cameraFetchInfoBo.setChannelSeq(channel.getChannelSeq());
                cameraFetchInfoBo.setCameraType(getCameraTypeCode(channel.getChannelType()));
                cameraFetchInfoBo.setDeviceCapacity(iccV5SDK.getCapabilities(channel.getCapability()));
                cameraFetchInfoBo.setDeviceParentCode(channel.getDeviceCode());
                cameraFetchInfoBo.setIsPtz(CameraTypeEnum.SPEED_DOME_CAMERA.getInfo().equals(channel.getCameraType()));
                cameraFetchInfoBo.setOwnerCode(channel.getOwnerCode());
                cameraFetchInfoBo.setOnlineStatus(channel.getIsOnline());
                cameraFetchInfoBoList.add(cameraFetchInfoBo);
            }
        }

        return cameraFetchInfoBoList;
    }

    private Integer getCameraTypeCode(String cameraType) {
        for (CameraTypeEnum cameraTypeEnum : CameraTypeEnum.values()) {
            if (cameraTypeEnum.getInfo().equals(cameraType)) {
                return cameraTypeEnum.getCode();
            }
        }

        return CameraTypeEnum.GUN_CAMERA.getCode();
    }

    @Override
    public void controlDirection(CameraOperateBo cameraOperateBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(Camera.class, IccSdkConfig.class, cameraOperateBo.getAreaId());
        String speed = getIccSpeed(cameraOperateBo.getSpeed());

        CameraControlRequest request = CameraControlRequest.builder()
                .channelId(cameraOperateBo.getChannelCode())
                .direct(getDirect(cameraOperateBo.getOperation()))
                .command(Boolean.TRUE.equals(cameraOperateBo.getAction()) ? ControlCommandEnum.START.getValue() : ControlCommandEnum.STOP.getValue())
                .stepX(speed)
                .stepY(speed)
                .extend("")
                .build();
        iccV5SDK.cameraControl(config, request);
    }

    /**
     * 把1-100的速度值，转成1-8的速度值
     */
    private String getIccSpeed(Integer speed) {
        int iccSpeed = (int) Math.ceil(speed * 8.0 / 100);
        if (iccSpeed < Integer.parseInt(SpeedEnum.SLOWEST.getValue()) || iccSpeed > Integer.parseInt(SpeedEnum.FASTEST.getValue())) {
            throw new ParamException("速度参数不正确：" + speed);
        }

        return String.valueOf(iccSpeed);
    }

    private String getDirect(CameraDirectionEnum operation) {
        return switch (operation) {
            case UP -> DirectionEnum.UP.getValue();
            case DOWN -> DirectionEnum.DOWN.getValue();
            case LEFT -> DirectionEnum.LEFT.getValue();
            case RIGHT -> DirectionEnum.RIGHT.getValue();
            case LEFT_UP -> DirectionEnum.LEFT_UP.getValue();
            case LEFT_DOWN -> DirectionEnum.LEFT_DOWN.getValue();
            case RIGHT_UP -> DirectionEnum.RIGHT_UP.getValue();
            case RIGHT_DOWN -> DirectionEnum.RIGHT_DOWN.getValue();

            default -> throw new BusinessRuntimeException("不支持的操作: " + operation);
        };
    }

}
