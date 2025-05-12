package info.zhihui.idevice.core.module.concrete.access.service.hikvision.isecure;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.Person;
import info.zhihui.idevice.core.sdk.hikvision.isecure.ISecureV2SDK;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.PersonData;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.ResourceInfo;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access.ConfigDownloadTaskEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access.DoorControlTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CardTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.DeviceChannelTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.PersonDataTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.util.ISecureBrmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CardTypeEnum.*;

@Service
@Slf4j
public class ISecureV2AccessControlImpl implements AccessControl {

    private final ISecureV2SDK iSecureV2SDK;
    private final Person person;
    private final DeviceModuleConfigService deviceModuleConfigService;

    @Value("${device.module.config.max-wait-config-seconds:30}")
    private int MAX_WAIT_CONFIG_SECONDS;

    // 官方建议等待3-5秒
    private static final int CONFIG_CHECK_INTERVAL_SECONDS = 3;

    @Autowired
    public ISecureV2AccessControlImpl(
            DeviceModuleConfigService deviceModuleConfigService,
            ISecureV2SDK iSecureV2SDK,
            @Qualifier("ISecureV2FoundationImpl") Person person
    ) {

        this.person = person;
        this.iSecureV2SDK = iSecureV2SDK;
        this.deviceModuleConfigService = deviceModuleConfigService;
    }

    @Override
    public void grantAccess(AccessPersonAuthBo accessPersonAuthBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, accessPersonAuthBo.getLocalPersonBo().getAreaId());

        ConfigAddRequest configAddRequest = buildConfigAddRequest(accessPersonAuthBo);
        ConfigAddResponse configAddResponse = iSecureV2SDK.configAdd(config, configAddRequest);

        waitConfigFinish(config, configAddResponse.getData().getTaskId());
    }

    private ConfigAddRequest buildConfigAddRequest(AccessPersonAuthBo accessPersonAuthBo) {
        String startTime = null, endTime = null;
        if (accessPersonAuthBo.getStartTime() != null && accessPersonAuthBo.getEndTime() != null) {
            startTime = SysDateUtil.toDateTimeZoneString(accessPersonAuthBo.getStartTime());
            endTime = SysDateUtil.toDateTimeZoneString(accessPersonAuthBo.getEndTime());
        }
        PersonData personData = new PersonData()
                .setPersonDataType(PersonDataTypeEnum.PERSON.getValue())
                .setIndexCodes(List.of(person.getThirdPartyPersonId(accessPersonAuthBo.getLocalPersonBo())));

        return ConfigAddRequest.builder()
                .personDatas(List.of(personData))
                .resourceInfos(buildResourceInfos(accessPersonAuthBo.getThirdPartyDeviceUniqueCodes()))
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    // @NOTICE 如果资源类型是ACS_DEVICE，那么channel必填
    private List<ResourceInfo> buildResourceInfos(List<String> thirdPartyDeviceUniqueCodes) {
        List<ResourceInfo> resourceInfos = new ArrayList<>();
        for (String deviceUniqueCode : thirdPartyDeviceUniqueCodes) {
            ResourceInfo resourceInfo = new ResourceInfo()
                    .setResourceType(DeviceChannelTypeEnum.DOOR.getCode())
                    .setResourceIndexCode(deviceUniqueCode)
            ;
            resourceInfos.add(resourceInfo);
        }

        return resourceInfos;
    }

    private void waitConfigFinish(ISecureSDKConfig config, String taskId) {
        LocalDateTime endTime = LocalDateTime.now().plusSeconds(MAX_WAIT_CONFIG_SECONDS);

        int attempt = 1;
        while (LocalDateTime.now().isBefore(endTime)) {
            try {
                ConfigRateSearchResponse response = iSecureV2SDK.configRateSearch(config, ConfigRateSearchRequest.builder().taskId(taskId).build());

                if (Boolean.TRUE.equals(response.getData().getIsConfigFinished())) {
                    log.info("配置下发成功，taskId: {}", taskId);
                    return;
                }

                log.info("第{}次查询配置下发状态，taskId: {}, detail: {}", attempt, taskId, response);
                Thread.sleep(CONFIG_CHECK_INTERVAL_SECONDS * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("配置下发被中断，taskId: {}", taskId, e);
                throw new BusinessRuntimeException("配置下发被中断");
            } catch (Exception e) {
                log.error("查询配置下发状态异常，taskId: {}", taskId, e);
            }

            attempt++;
        }

        throw new BusinessRuntimeException("配置下发超时，taskId: " + taskId);
    }

    @Override
    public void revokeAccess(AccessPersonRevokeBo accessPersonRevokeBo) {
        String personId;
        try {
            personId = person.getThirdPartyPersonId(accessPersonRevokeBo.getLocalPersonBo());
        } catch (NotFoundException e) {
            log.warn("没有找到需要取消授权的关联数据");
            return;
        }

        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, accessPersonRevokeBo.getLocalPersonBo().getAreaId());
        ConfigDeleteRequest configDeleteRequest = ConfigDeleteRequest.builder().personDatas(List.of(new PersonData().setPersonDataType(PersonDataTypeEnum.PERSON.getValue()).setIndexCodes(List.of(personId)))).resourceInfos(buildResourceInfos(accessPersonRevokeBo.getThirdPartyDeviceUniqueCodes())).build();
        ConfigDeleteResponse configDeleteResponse = iSecureV2SDK.configDelete(config, configDeleteRequest);

        waitConfigFinish(config, configDeleteResponse.getData().getTaskId());
    }

    @Override
    public void syncPermissions(AccessPermissionSyncBo accessPermissionSyncBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, accessPermissionSyncBo.getAreaId());
        if (config.getExtendConfig() != null && Boolean.TRUE.equals(config.getExtendConfig().getAccessPermissionAutoSync())) {
            log.info("当前平台配置了自动同步权限");
            return;
        }

        // 创建任务
        int taskType = getTaskType(accessPermissionSyncBo.getTaskType());
        ConfigDownloadTaskCreateRequest configDownloadTaskCreateRequest = ConfigDownloadTaskCreateRequest.builder().taskType(taskType).build();
        ConfigDownloadTaskCreateResponse configDownloadTaskCreateResponse = iSecureV2SDK.configDownloadTaskCreate(config, configDownloadTaskCreateRequest);

        // 添加要同步的设备
        if (accessPermissionSyncBo.getThirdPartyDeviceUniqueCodes() != null && !accessPermissionSyncBo.getThirdPartyDeviceUniqueCodes().isEmpty()) {
            ConfigDownloadTaskAddDeviceRequest configDownloadTaskAddDeviceRequest = ConfigDownloadTaskAddDeviceRequest.builder()
                    .taskId(configDownloadTaskCreateResponse.getData().getTaskId())
                    .resourceInfos(buildResourceInfos(accessPermissionSyncBo.getThirdPartyDeviceUniqueCodes()))
                    .build();
            iSecureV2SDK.configDownloadTaskAddDevice(config, configDownloadTaskAddDeviceRequest);
        }

        // 启动任务
        ConfigDownloadTaskStartRequest configDownloadTaskStartRequest = ConfigDownloadTaskStartRequest.builder().taskId(configDownloadTaskCreateResponse.getData().getTaskId()).build();
        iSecureV2SDK.configDownloadTaskStart(config, configDownloadTaskStartRequest);
    }

    private int getTaskType(Integer typeId) {
        // 处理null值或无效值，默认返回卡片+指纹+人脸组合类型
        if (typeId == null || !ConfigDownloadTaskEnum.isValid(typeId)) {
            return ConfigDownloadTaskEnum.CARD_FINGERPRINT_FACE.getCode();
        }
        return typeId;
    }

    @Override
    public void updateAccessImg(AccessPersonFaceUpdateBo accessPersonFaceUpdateBo) {
        PersonFaceUpdateBo personFaceUpdateBo = new PersonFaceUpdateBo().setLocalPersonBo(accessPersonFaceUpdateBo.getLocalPersonBo()).setFaceImageResource(accessPersonFaceUpdateBo.getFaceImageResource());

        person.updatePersonFace(personFaceUpdateBo);
    }

    @Override
    public void cardBinding(AccessCardBindingBo cardBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, cardBo.getLocalPersonBo().getAreaId());

        CardBindingRequest request = buildCardBindingRequest(cardBo);
        CardBindingResponse response = iSecureV2SDK.cardBinding(config, request);
        log.info("海康平台开卡成功：{}", response);
    }

    private CardBindingRequest buildCardBindingRequest(AccessCardBindingBo cardBo) {
        String personId = person.getThirdPartyPersonId(cardBo.getLocalPersonBo());
        CardBindingInfo cardBindingInfo = new CardBindingInfo()
                .setPersonId(personId)
                .setCardNo(cardBo.getCardNumber())
                .setCardType(getCardType(cardBo.getCardType()));
        String startDate = null;
        String endDate = null;
        if (cardBo.getStartTime() != null && cardBo.getEndTime() != null) {
            startDate = SysDateUtil.toDateString(cardBo.getStartTime());
            endDate = SysDateUtil.toDateString(cardBo.getEndTime());
        }

        return CardBindingRequest.builder().startDate(startDate).endDate(endDate).cardList(List.of(cardBindingInfo)).build();
    }

    private Integer getCardType(Integer typeId) {
        if (typeId == null) {
            return IC_CARD.getCode();
        }

        for (CardTypeEnum type : CardTypeEnum.values()) {
            if (type.getId().equals(typeId)) {
                return type.getCode();
            }
        }

        return IC_CARD.getCode();
    }

    @Override
    public void cardUnbind(AccessCardUnBindingBo cardBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, cardBo.getLocalPersonBo().getAreaId());

        CardReturnRequest request = CardReturnRequest.builder().cardNumber(cardBo.getCardNumber()).personId(person.getThirdPartyPersonId(cardBo.getLocalPersonBo())).build();
        CardReturnResponse response = iSecureV2SDK.cardReturn(config, request);
        log.info("海康平台退卡成功：{}", response);
    }

    @Override
    public List<AccessFetchInfoBo> findAllDeviceChannelList(AccessDeviceQueryBo accessDeviceQueryBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, accessDeviceQueryBo.getAreaId());

        List<DoorInfo> doorInfoList = iSecureV2SDK.findAllByPage(config, DoorSearchRequest.builder().build(), iSecureV2SDK::doorSearch);
        if (CollectionUtils.isEmpty(doorInfoList)) {
            return List.of();
        }

        // 分步构建门禁通道的上级设备map
        List<AccessDevice> accessDevices = iSecureV2SDK.findAllByPage(config, AccessDeviceSearchRequest.builder().build(), iSecureV2SDK::accessDeviceSearch);
        Map<String, AccessDevice> accessDeviceMap = accessDevices.stream().collect(Collectors.toMap(AccessDevice::getIndexCode, Function.identity()));
        // 分步构建在线状态Map
        List<DoorOnlineStatusInfo> doorOnlineStatusInfos = iSecureV2SDK.findAllByPage(config, DoorOnlineStatusRequest.builder().build(), iSecureV2SDK::doorOnlineStatus);
        Map<String, Integer> doorOnlineStatusMap = doorOnlineStatusInfos.stream().collect(Collectors.toMap(DoorOnlineStatusInfo::getIndexCode, DoorOnlineStatusInfo::getOnline));

        // 转换对象
        return doorInfoList.stream().map(doorInfo -> {
            AccessDevice accessDevice = accessDeviceMap.getOrDefault(doorInfo.getParentIndexCode(), new AccessDevice().setName("").setManufacturer(""));

            AccessFetchInfoBo device = new AccessFetchInfoBo();
            device.setThirdPartyAreaId(accessDeviceQueryBo.getAreaId());
            device.setDeviceParentCode(doorInfo.getParentIndexCode());
            device.setDeviceParentName(accessDevice.getName());
            device.setThirdPartyChannelNo(doorInfo.getChannelNo());
            device.setThirdPartyDeviceName(doorInfo.getName());
            device.setThirdPartyDeviceUniqueCode(doorInfo.getIndexCode());
            device.setOnlineStatus(doorOnlineStatusMap.getOrDefault(doorInfo.getParentIndexCode(), 0));
            device.setDeviceCapacity(ISecureBrmUtil.getCapacityList(accessDevice.getCapability()));
            device.setRegionPath(ISecureBrmUtil.getPathList(accessDevice.getRegionPath(), "@"));
            device.setRegionPathName(ISecureBrmUtil.getPathList(accessDevice.getRegionPathName(), "/"));
            return device;
        }).collect(Collectors.toList());
    }

    @Override
    public void openDoor(AccessOpenBo accessOpenDoorBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, accessOpenDoorBo.getAreaId());
        DoorControlRequest request = DoorControlRequest.builder().doorIndexCodes(List.of(accessOpenDoorBo.getThirdPartyDeviceUniqueCode())).controlType(DoorControlTypeEnum.DOOR_OPEN.getValue()).build();

        DoorControlResponse res = iSecureV2SDK.doorControl(config, request);
        if (res.getData() == null || res.getData().isEmpty()) {
            throw new BusinessRuntimeException("远程开门失败：数据解析不正确");
        }

        DoorControlResponseData data = res.getData().get(0);
        if (data.getControlResultCode() != 0) {
            log.error("远程开门失败，错误码：{}，错误信息：{}", data.getControlResultCode(), data.getControlResultDesc());
            throw new BusinessRuntimeException("远程开门失败: " + data.getControlResultDesc());
        }
    }
}