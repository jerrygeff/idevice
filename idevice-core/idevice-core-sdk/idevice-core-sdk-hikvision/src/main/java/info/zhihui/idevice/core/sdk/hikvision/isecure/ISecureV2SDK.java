package info.zhihui.idevice.core.sdk.hikvision.isecure;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.*;
import org.springframework.stereotype.Component;

/**
 * @author jerryge
 */
@Component
public class ISecureV2SDK extends BaseISecure {
    // VERSION = "2.1.1";

    // 门禁控制
    public DoorControlResponse doorControl(ISecureSDKConfig config, DoorControlRequest request) {
        return doActionWithConfig(config, request, DoorControlResponse.class);
    }

    // 查询门禁点
    public DoorSearchResponse doorSearch(ISecureSDKConfig config, DoorSearchRequest request) {
        return doActionWithConfig(config, request, DoorSearchResponse.class);
    }

    // 查询门禁设备
    public AccessDeviceSearchResponse accessDeviceSearch(ISecureSDKConfig config, AccessDeviceSearchRequest request) {
        return doActionWithConfig(config, request, AccessDeviceSearchResponse.class);
    }

    // 查询门禁设备在线状态
    public DoorOnlineStatusResponse doorOnlineStatus(ISecureSDKConfig config, DoorOnlineStatusRequest request) {
        return doActionWithConfig(config, request, DoorOnlineStatusResponse.class);
    }

    // 用户权限下发到设备
    public PersonAuthResponse personAuthActionDiy(ISecureSDKConfig config, PersonAuthRequest request) {
        return doActionWithConfig(config, request, PersonAuthResponse.class);
    }

    // 添加人员
    public PersonAddResponse personAdd(ISecureSDKConfig config, PersonAddRequest request) {
        return doActionWithConfig(config, request, PersonAddResponse.class);
    }

    // 删除人员
    public PersonDeleteResponse personDelete(ISecureSDKConfig config, PersonDeleteRequest request) {
        return doActionWithConfig(config, request, PersonDeleteResponse.class);
    }

    // 查询人员
    public PersonUniqueKeySearchResponse personUniqueKeySearch(ISecureSDKConfig config, PersonUniqueKeySearchRequest request) {
        return doActionWithConfig(config, request, PersonUniqueKeySearchResponse.class);
    }

    // 更新人脸
    public PersonFaceUpdateResponse personFaceUpdate(ISecureSDKConfig config, PersonFaceUpdateRequest request) {
        return doActionWithConfig(config, request, PersonFaceUpdateResponse.class);
    }

    // 添加人脸
    public PersonFaceAddResponse personFaceAdd(ISecureSDKConfig config, PersonFaceAddRequest request) {
        return doActionWithConfig(config, request, PersonFaceAddResponse.class);
    }

    // 绑定卡
    public CardBindingResponse cardBinding(ISecureSDKConfig config, CardBindingRequest request) {
        return doActionWithConfig(config, request, CardBindingResponse.class);
    }

    // 退卡
    public CardReturnResponse cardReturn(ISecureSDKConfig config, CardReturnRequest request) {
        return doActionWithConfig(config, request, CardReturnResponse.class);
    }

    // 添加权限配置
    public ConfigAddResponse configAdd(ISecureSDKConfig config, ConfigAddRequest request) {
        return doActionWithConfig(config, request, ConfigAddResponse.class);
    }

    // 移除权限配置
    public ConfigDeleteResponse configDelete(ISecureSDKConfig config, ConfigDeleteRequest request) {
        return doActionWithConfig(config, request, ConfigDeleteResponse.class);
    }

    // 查询权限配置单进度
    public ConfigRateSearchResponse configRateSearch(ISecureSDKConfig config, ConfigRateSearchRequest request) {
        return doActionWithConfig(config, request, ConfigRateSearchResponse.class);
    }

    // 创建下载任务
    public ConfigDownloadTaskCreateResponse configDownloadTaskCreate(ISecureSDKConfig config, ConfigDownloadTaskCreateRequest request) {
        return doActionWithConfig(config, request, ConfigDownloadTaskCreateResponse.class);
    }

    // 添加待下载的设备
    public ConfigDownloadTaskAddDeviceResponse configDownloadTaskAddDevice(ISecureSDKConfig config, ConfigDownloadTaskAddDeviceRequest request) {
        return doActionWithConfig(config, request, ConfigDownloadTaskAddDeviceResponse.class);
    }

    // 启动下载任务
    public ConfigDownloadTaskStartResponse configDownloadTaskStart(ISecureSDKConfig config, ConfigDownloadTaskStartRequest request) {
        return doActionWithConfig(config, request, ConfigDownloadTaskStartResponse.class);
    }

    // 视频实时预览
    public RealTimeLinkResponse realTimeLink(ISecureSDKConfig config, RealTimeLinkRequest request) {
        return doActionWithConfig(config, request, RealTimeLinkResponse.class);
    }

    // 视频回放
    public PlaybackLinkResponse playbackLink(ISecureSDKConfig config, PlaybackLinkRequest request) {
        return doActionWithConfig(config, request, PlaybackLinkResponse.class);
    }

    // 云台操作
    public CameraControlResponse cameraControl(ISecureSDKConfig config, CameraControlRequest request) {
        return doActionWithConfig(config, request, CameraControlResponse.class);
    }

    // 视频通道列表
    public CameraSearchResponse cameraSearch(ISecureSDKConfig config, CameraSearchRequest request) {
        return doActionWithConfig(config, request, CameraSearchResponse.class);
    }

    // 视频编码设备
    public CameraEncodeDeviceSearchResponse cameraEncodeDeviceSearch(ISecureSDKConfig config, CameraEncodeDeviceSearchRequest request) {
        return doActionWithConfig(config, request, CameraEncodeDeviceSearchResponse.class);
    }

    // 视频在线状态数据列表
    public CameraOnlineSearchResponse cameraOnlineSearch(ISecureSDKConfig config, CameraOnlineSearchRequest request) {
        return doActionWithConfig(config, request, CameraOnlineSearchResponse.class);
    }

}
