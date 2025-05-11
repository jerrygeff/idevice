package info.zhihui.idevice.core.module.concrete.camera.service.hikvision.isecure;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraDirectionEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import info.zhihui.idevice.core.module.concrete.camera.service.Camera;
import info.zhihui.idevice.core.sdk.hikvision.isecure.ISecureV2SDK;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.ActionEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.CommandEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.OnlineStatusEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.StreamTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.util.ISecureBrmUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant.PTZ;

/**
 * @author jerryge
 */
@Service
public class ISecureV2CameraImpl implements Camera {
    private final ISecureV2SDK iSecureV2SDK;
    private final DeviceModuleConfigService deviceModuleConfigService;

    @Autowired
    public ISecureV2CameraImpl(
            ISecureV2SDK iSecureV2SDK,
            DeviceModuleConfigService deviceModuleConfigService
    ) {
        this.deviceModuleConfigService = deviceModuleConfigService;
        this.iSecureV2SDK = iSecureV2SDK;
    }

    @Override
    public CameraRealtimeLinkBo getCameraRealTimeLink(CameraRealtimeQueryBo realTimeQueryBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, realTimeQueryBo.getAreaId());

        RealTimeLinkRequest request = RealTimeLinkRequest.builder()
                .cameraIndexCode(realTimeQueryBo.getChannelCode())
                .streamType(getStreamType(realTimeQueryBo.getStreamType()))
                .protocol(realTimeQueryBo.getProtocol().getValue())
                .build();
        RealTimeLinkResponse response = iSecureV2SDK.realTimeLink(config, request);

        return new CameraRealtimeLinkBo().setUrl(response.getData().getUrl());
    }

    private Integer getStreamType(CameraStreamTypeEnum streamType) {
        return switch (streamType) {
            case MAIN -> StreamTypeEnum.MAIN.getCode();
            case SUB -> StreamTypeEnum.SUB.getCode();
            case THIRD -> StreamTypeEnum.THIRD.getCode();
        };
    }

    @Override
    public CameraPlaybackLinkBo getCameraPlaybackLink(CameraPlaybackQueryBo playbackQueryBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, playbackQueryBo.getAreaId());

        PlaybackLinkRequest request = PlaybackLinkRequest.builder()
                .cameraIndexCode(playbackQueryBo.getChannelCode())
                .protocol(playbackQueryBo.getProtocol().getValue())
                .beginTime(SysDateUtil.toOffsetDateTime(playbackQueryBo.getStartTime()))
                .endTime(SysDateUtil.toOffsetDateTime(playbackQueryBo.getEndTime()))
                .build();
        PlaybackLinkResponse response = iSecureV2SDK.playbackLink(config, request);

        return new CameraPlaybackLinkBo().setPlaybackUrl(response.getData().getUrl());
    }

    @Override
    public List<CameraFetchInfoBo> findAllCameraChannelList(CameraQueryBo queryBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, queryBo.getAreaId());

        CameraSearchRequest cameraSearchRequest = CameraSearchRequest.builder().build();
        List<CameraInfo> cameraChannelList = iSecureV2SDK.findAllByPage(config, cameraSearchRequest, iSecureV2SDK::cameraSearch);
        if (CollectionUtils.isEmpty(cameraChannelList)) {
            return List.of();
        }

        CameraOnlineSearchRequest cameraOnlineSearchRequest = CameraOnlineSearchRequest
                .builder()
                .status(OnlineStatusEnum.ONLINE.getValue())
                .build();
        List<CameraOnlineInfo> cameraOnlineInfos = iSecureV2SDK.findAllByPage(config, cameraOnlineSearchRequest, iSecureV2SDK::cameraOnlineSearch);
        Map<String, CameraOnlineInfo> OnlineStatusMap = cameraOnlineInfos.stream().collect(Collectors.toMap(CameraOnlineInfo::getIndexCode, cameraOnlineInfo -> cameraOnlineInfo));

        return buildCameraFetchInfoBoList(cameraChannelList, OnlineStatusMap, queryBo.getAreaId());
    }

    private List<CameraFetchInfoBo> buildCameraFetchInfoBoList(List<CameraInfo> cameraInfos, Map<String, CameraOnlineInfo> OnlineStatusMap, Integer areaId) {
        List<CameraFetchInfoBo> res = new ArrayList<>();
        for (CameraInfo cameraInfo : cameraInfos) {
            CameraFetchInfoBo cameraFetchInfoBo = new CameraFetchInfoBo();
            cameraFetchInfoBo.setThirdPartyAreaId(areaId);
            cameraFetchInfoBo.setThirdPartyDeviceName(cameraInfo.getName());
            cameraFetchInfoBo.setThirdPartyDeviceUniqueCode(cameraInfo.getIndexCode());
            cameraFetchInfoBo.setCameraType(cameraInfo.getCameraType());
            cameraFetchInfoBo.setDeviceCapacity(ISecureBrmUtil.getCapacityList(cameraInfo.getCapability()));
            cameraFetchInfoBo.setChannelSeq(cameraInfo.getChanNum());
            cameraFetchInfoBo.setRegionPath(ISecureBrmUtil.getPathList(cameraInfo.getRegionPath(), "@"));
            cameraFetchInfoBo.setRegionPathName(ISecureBrmUtil.getPathList(cameraInfo.getRegionPathName(), "/"));
            cameraFetchInfoBo.setIsPtz(StringUtils.isNoneBlank(cameraInfo.getCapability()) && StringUtils.containsIgnoreCase(cameraInfo.getCapability(), PTZ));
            cameraFetchInfoBo.setDeviceParentCode(cameraInfo.getParentIndexCode());

            CameraOnlineInfo onlineInfo = OnlineStatusMap.getOrDefault(cameraInfo.getIndexCode(), new CameraOnlineInfo().setOnline(0).setManufacturer(""));
            cameraFetchInfoBo.setOnlineStatus(onlineInfo.getOnline());

            res.add(cameraFetchInfoBo);
        }

        return res;
    }

    @Override
    public void controlDirection(CameraOperateBo cameraOperateBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, cameraOperateBo.getAreaId());

        CameraControlRequest request = CameraControlRequest.builder()
                .cameraIndexCode(cameraOperateBo.getChannelCode())
                .action(Boolean.TRUE.equals(cameraOperateBo.getAction()) ? ActionEnum.START.getCode() : ActionEnum.STOP.getCode())
                .command(getDirect(cameraOperateBo.getOperation()))
                .speed(cameraOperateBo.getSpeed())
                .build();
        iSecureV2SDK.cameraControl(config, request);
    }

    private String getDirect(CameraDirectionEnum operation) {
        if (operation == null) {
            throw new ParamException("操作不能为空");
        }

        return switch (operation) {
            case UP -> CommandEnum.UP.getValue();
            case DOWN -> CommandEnum.DOWN.getValue();
            case LEFT -> CommandEnum.LEFT.getValue();
            case RIGHT -> CommandEnum.RIGHT.getValue();
            case LEFT_UP -> CommandEnum.LEFT_UP.getValue();
            case LEFT_DOWN -> CommandEnum.LEFT_DOWN.getValue();
            case RIGHT_UP -> CommandEnum.RIGHT_UP.getValue();
            case RIGHT_DOWN -> CommandEnum.RIGHT_DOWN.getValue();
            default -> throw new RuntimeException("不支持的操作: " + operation);
        };
    }

}