package info.zhihui.idevice.core.module.concrete.access.service.hikvision.isecure;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.Person;
import info.zhihui.idevice.core.sdk.hikvision.isecure.ISecureV2SDK;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureExtendConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.PersonData;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.ResourceInfo;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access.ConfigDownloadTaskEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access.DoorControlTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CardTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.DeviceChannelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ISecureV2AccessControlImplTest {

    @Mock
    private ISecureV2SDK iSecureV2SDK;

    @Mock
    private Person person;

    @Mock
    private DeviceModuleConfigService deviceModuleConfigService;

    @InjectMocks
    private ISecureV2AccessControlImpl accessControl;

    @BeforeEach
    void setUp() {
        accessControl = new ISecureV2AccessControlImpl(
                deviceModuleConfigService,
                iSecureV2SDK,
                person
        );
    }

    @Test
    void grantAccess_Success() {
        // 准备测试数据
        ReflectionTestUtils.setField(accessControl, "MAX_WAIT_CONFIG_SECONDS", 4);
        Integer areaId = 1;
        String thirdPartyPersonId = "100";
        String thirdPartyDeviceUniqueCode = "device_code_001";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonAuthBo authBo = new AccessPersonAuthBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock人员ID查询
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock配置下发
        ConfigAddResponse configAddResponse = new ConfigAddResponse();
        configAddResponse.setData(new ConfigOperateResponseData());
        configAddResponse.getData().setTaskId("task_001");
        when(iSecureV2SDK.configAdd(eq(mockConfig), any(ConfigAddRequest.class))).thenReturn(configAddResponse);

        // Mock配置下发状态查询
        ConfigRateSearchResponse configRateSearchResponse = new ConfigRateSearchResponse();
        configRateSearchResponse.setData(new ConfigRateSearchResponseData());
        configRateSearchResponse.getData().setIsConfigFinished(true);
        when(iSecureV2SDK.configRateSearch(eq(mockConfig), any(ConfigRateSearchRequest.class))).thenReturn(configRateSearchResponse);

        // 执行测试
        accessControl.grantAccess(authBo);

        // 验证调用
        verify(iSecureV2SDK).configAdd(eq(mockConfig), argThat(request -> {
            List<PersonData> personDatas = request.getPersonDatas();
            List<ResourceInfo> resourceInfos = request.getResourceInfos();

            return personDatas != null && personDatas.size() == 1 &&
                    personDatas.get(0).getIndexCodes().contains(thirdPartyPersonId) &&
                    resourceInfos != null && resourceInfos.size() == 1 &&
                    resourceInfos.get(0).getResourceIndexCode().equals(thirdPartyDeviceUniqueCode) &&
                    Objects.equals(resourceInfos.get(0).getResourceType(), DeviceChannelTypeEnum.DOOR.getCode());
        }));

        verify(iSecureV2SDK).configRateSearch(eq(mockConfig), argThat(request ->
                request.getTaskId().equals("task_001")
        ));
    }

    @Test
    void grantAccess_ConfigTimeout() {
        ReflectionTestUtils.setField(accessControl, "MAX_WAIT_CONFIG_SECONDS", 4);
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyPersonId = "100";
        String thirdPartyDeviceUniqueCode = "device_code_001";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonAuthBo authBo = new AccessPersonAuthBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));


        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock人员ID查询
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock配置下发
        ConfigAddResponse configAddResponse = new ConfigAddResponse();
        configAddResponse.setData(new ConfigOperateResponseData());
        configAddResponse.getData().setTaskId("task_001");
        when(iSecureV2SDK.configAdd(eq(mockConfig), any(ConfigAddRequest.class))).thenReturn(configAddResponse);

        // Mock配置下发状态查询 - 始终返回未完成
        ConfigRateSearchResponse configRateSearchResponse = new ConfigRateSearchResponse();
        configRateSearchResponse.setData(new ConfigRateSearchResponseData());
        configRateSearchResponse.getData().setIsConfigFinished(false);
        when(iSecureV2SDK.configRateSearch(eq(mockConfig), any(ConfigRateSearchRequest.class))).thenReturn(configRateSearchResponse);

        // 执行测试并验证异常
        BusinessRuntimeException exception = assertThrows(BusinessRuntimeException.class, () -> accessControl.grantAccess(authBo));
        assertEquals("配置下发超时，taskId: task_001", exception.getMessage());
    }

    @Test
    void revokeAccess_Success() {
        // 准备测试数据
        ReflectionTestUtils.setField(accessControl, "MAX_WAIT_CONFIG_SECONDS", 4);
        Integer areaId = 1;
        String thirdPartyPersonId = "100";
        String thirdPartyDeviceUniqueCode = "device_code_001";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonRevokeBo revokeBo = new AccessPersonRevokeBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock人员ID查询
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock配置下发
        ConfigDeleteResponse configDeleteResponse = new ConfigDeleteResponse();
        configDeleteResponse.setData(new ConfigOperateResponseData());
        configDeleteResponse.getData().setTaskId("task_002");
        when(iSecureV2SDK.configDelete(eq(mockConfig), any(ConfigDeleteRequest.class))).thenReturn(configDeleteResponse);

        // Mock配置下发状态查询
        ConfigRateSearchResponse configRateSearchResponse = new ConfigRateSearchResponse();
        configRateSearchResponse.setData(new ConfigRateSearchResponseData());
        configRateSearchResponse.getData().setIsConfigFinished(true);
        when(iSecureV2SDK.configRateSearch(eq(mockConfig), any(ConfigRateSearchRequest.class))).thenReturn(configRateSearchResponse);

        // 执行测试
        accessControl.revokeAccess(revokeBo);

        // 验证调用
        verify(iSecureV2SDK).configDelete(eq(mockConfig), argThat(request -> {
            List<PersonData> personDatas = request.getPersonDatas();
            List<ResourceInfo> resourceInfos = request.getResourceInfos();

            return personDatas != null && personDatas.size() == 1 &&
                    personDatas.get(0).getIndexCodes().contains(thirdPartyPersonId) &&
                    resourceInfos != null && resourceInfos.size() == 1 &&
                    resourceInfos.get(0).getResourceIndexCode().equals(thirdPartyDeviceUniqueCode) &&
                    Objects.equals(resourceInfos.get(0).getResourceType(), DeviceChannelTypeEnum.DOOR.getCode());
        }));

        verify(iSecureV2SDK).configRateSearch(eq(mockConfig), argThat(request ->
                request.getTaskId().equals("task_002")
        ));
    }

    @Test
    void revokeAccess_PersonNotFound() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "device_code_001";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonRevokeBo revokeBo = new AccessPersonRevokeBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));

        // Mock人员ID查询抛出异常
        when(person.getThirdPartyPersonId(localPersonBo)).thenThrow(new NotFoundException());

        // 执行测试
        accessControl.revokeAccess(revokeBo);

        // 验证没有调用SDK方法
        verify(iSecureV2SDK, never()).configDelete(any(), any());
        verify(iSecureV2SDK, never()).configRateSearch(any(), any());
    }

    @Test
    void syncPermissions_AutoSyncEnabled() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "device_code_001";

        AccessPermissionSyncBo syncBo = new AccessPermissionSyncBo()
                .setAreaId(areaId)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode))
                .setTaskType(7);

        // Mock配置服务 - 启用了自动同步
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        mockConfig.setExtendConfig(new ISecureExtendConfig().setAccessPermissionAutoSync(true));
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // 执行测试
        accessControl.syncPermissions(syncBo);

        // 验证没有调用SDK方法
        verify(iSecureV2SDK, never()).configDownloadTaskCreate(any(), any());
        verify(iSecureV2SDK, never()).configDownloadTaskAddDevice(any(), any());
        verify(iSecureV2SDK, never()).configDownloadTaskStart(any(), any());
    }

    @Test
    void syncPermissions_ManualSync_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "device_code_001";

        // 测试场景1：传入有效的taskType（卡片类型）
        testSyncPermissions(areaId, thirdPartyDeviceUniqueCode, ConfigDownloadTaskEnum.CARD.getCode(), ConfigDownloadTaskEnum.CARD.getCode());

        // 测试场景2：传入null值
        testSyncPermissions(areaId, thirdPartyDeviceUniqueCode, null, ConfigDownloadTaskEnum.CARD_FINGERPRINT_FACE.getCode());

        // 测试场景3：传入无效的值
        testSyncPermissions(areaId, thirdPartyDeviceUniqueCode, 999, ConfigDownloadTaskEnum.CARD_FINGERPRINT_FACE.getCode());
    }

    /**
     * 测试权限同步的私有辅助方法，用于测试不同的任务类型情况
     */
    private void testSyncPermissions(Integer areaId, String deviceCode, Integer inputTaskType, Integer expectedTaskType) {
        // 创建同步参数
        AccessPermissionSyncBo syncBo = new AccessPermissionSyncBo()
                .setAreaId(areaId)
                .setThirdPartyDeviceUniqueCodes(List.of(deviceCode))
                .setTaskType(inputTaskType);

        // Mock配置服务 - 未启用自动同步
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock创建任务
        ConfigDownloadTaskCreateResponse createResponse = new ConfigDownloadTaskCreateResponse();
        createResponse.setData(new ConfigOperateResponseData());
        createResponse.getData().setTaskId("task_003");
        when(iSecureV2SDK.configDownloadTaskCreate(eq(mockConfig), any(ConfigDownloadTaskCreateRequest.class)))
                .thenReturn(createResponse);

        // Mock添加设备
        ConfigDownloadTaskAddDeviceResponse addDeviceResponse = new ConfigDownloadTaskAddDeviceResponse();
        when(iSecureV2SDK.configDownloadTaskAddDevice(eq(mockConfig), any(ConfigDownloadTaskAddDeviceRequest.class)))
                .thenReturn(addDeviceResponse);

        // Mock启动任务
        ConfigDownloadTaskStartResponse startResponse = new ConfigDownloadTaskStartResponse();
        when(iSecureV2SDK.configDownloadTaskStart(eq(mockConfig), any(ConfigDownloadTaskStartRequest.class)))
                .thenReturn(startResponse);

        // 执行测试
        accessControl.syncPermissions(syncBo);

        // 验证调用 - 特别验证任务类型是否正确转换
        verify(iSecureV2SDK).configDownloadTaskCreate(eq(mockConfig), argThat(request ->
                Objects.equals(request.getTaskType(), expectedTaskType)
        ));

        verify(iSecureV2SDK).configDownloadTaskAddDevice(eq(mockConfig), argThat(request -> {
            List<ResourceInfo> resourceInfos = request.getResourceInfos();
            return request.getTaskId().equals("task_003") &&
                    resourceInfos != null && resourceInfos.size() == 1 &&
                    resourceInfos.get(0).getResourceIndexCode().equals(deviceCode) &&
                    Objects.equals(resourceInfos.get(0).getResourceType(), DeviceChannelTypeEnum.DOOR.getCode());
        }));

        verify(iSecureV2SDK).configDownloadTaskStart(eq(mockConfig), argThat(request ->
                request.getTaskId().equals("task_003")
        ));

        // 重置所有mock以供下一次测试使用
        reset(iSecureV2SDK, deviceModuleConfigService);
    }

    @Test
    void updateAccessImg_Success() {
        // 准备测试数据
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonFaceUpdateBo updateBo = new AccessPersonFaceUpdateBo()
                .setLocalPersonBo(localPersonBo);

        // 执行测试
        accessControl.updateAccessImg(updateBo);

        // 验证调用
        verify(person).updatePersonFace(argThat(personFaceUpdateBo ->
                personFaceUpdateBo.getLocalPersonBo().equals(localPersonBo)
        ));
    }

    @Test
    void cardBinding_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyPersonId = "100";
        String cardNumber = "88822221";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessCardBindingBo cardBo = new AccessCardBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock人员ID查询
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock开卡
        CardBindingResponse cardBindingResponse = new CardBindingResponse();
        when(iSecureV2SDK.cardBinding(eq(mockConfig), any(CardBindingRequest.class))).thenReturn(cardBindingResponse);

        // 执行测试
        accessControl.cardBinding(cardBo);

        // 验证调用
        verify(iSecureV2SDK).cardBinding(eq(mockConfig), argThat(request -> {
            List<CardBindingInfo> cardList = request.getCardList();
            return cardList != null && cardList.size() == 1 &&
                    cardList.get(0).getPersonId().equals(thirdPartyPersonId) &&
                    cardList.get(0).getCardNo().equals(cardNumber) &&
                    Objects.equals(cardList.get(0).getCardType(), CardTypeEnum.IC_CARD.getCode());
        }));
    }

    @Test
    void cardUnbind_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyPersonId = "100";
        String cardNumber = "88822221";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessCardUnBindingBo cardBo = new AccessCardUnBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock人员ID查询
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock退卡
        CardReturnResponse cardReturnResponse = new CardReturnResponse();
        when(iSecureV2SDK.cardReturn(eq(mockConfig), any(CardReturnRequest.class))).thenReturn(cardReturnResponse);

        // 执行测试
        accessControl.cardUnbind(cardBo);

        // 验证调用
        verify(iSecureV2SDK).cardReturn(eq(mockConfig), argThat(request ->
                request.getCardNumber().equals(cardNumber) &&
                        request.getPersonId().equals(thirdPartyPersonId)
        ));
    }

    @Test
    void findAllDeviceChannelList_Success() {
        Integer areaId = 1;
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock 门禁通道
        DoorInfo door1 = new DoorInfo();
        door1.setName("门1");
        door1.setIndexCode("door_code_1");
        door1.setChannelNo("001");
        door1.setInstallLocation("一楼大厅");
        door1.setParentIndexCode("device_code_1");
        DoorInfo door2 = new DoorInfo();
        door2.setName("门2");
        door2.setIndexCode("door_code_2");
        door2.setChannelNo("002");
        door2.setInstallLocation("二楼入口");
        door2.setParentIndexCode("device_code_2");
        List<DoorInfo> doorInfoList = List.of(door1, door2);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(DoorSearchRequest.class), any())).thenReturn(doorInfoList);

        // Mock 设备
        AccessDevice device1 = new AccessDevice();
        device1.setIndexCode("device_code_1");
        device1.setName("设备A");
        AccessDevice device2 = new AccessDevice();
        device2.setIndexCode("device_code_2");
        device2.setName("设备B");
        List<AccessDevice> accessDevices = List.of(device1, device2);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(AccessDeviceSearchRequest.class), any())).thenReturn(accessDevices);

        // Mock 在线状态
        DoorOnlineStatusInfo status1 = new DoorOnlineStatusInfo();
        status1.setIndexCode("device_code_1");
        status1.setOnline(1);
        DoorOnlineStatusInfo status2 = new DoorOnlineStatusInfo();
        status2.setIndexCode("device_code_2");
        status2.setOnline(0);
        List<DoorOnlineStatusInfo> onlineStatusInfos = List.of(status1, status2);
        when(iSecureV2SDK.findAllByPage(eq(mockConfig), any(DoorOnlineStatusRequest.class), any())).thenReturn(onlineStatusInfos);

        AccessDeviceQueryBo queryBo = new AccessDeviceQueryBo();
        queryBo.setAreaId(areaId);
        List<AccessFetchInfoBo> result = accessControl.findAllDeviceChannelList(queryBo);

        assertEquals(2, result.size());
        AccessFetchInfoBo bo1 = result.get(0);
        assertEquals("门1", bo1.getThirdPartyDeviceName());
        assertEquals("door_code_1", bo1.getThirdPartyDeviceUniqueCode());
        assertEquals("001", bo1.getThirdPartyChannelNo());
        assertEquals("设备A", bo1.getDeviceParentName());
        assertEquals("device_code_1", bo1.getDeviceParentCode());
        assertEquals(1, bo1.getOnlineStatus());
        AccessFetchInfoBo bo2 = result.get(1);
        assertEquals("门2", bo2.getThirdPartyDeviceName());
        assertEquals("door_code_2", bo2.getThirdPartyDeviceUniqueCode());
        assertEquals("002", bo2.getThirdPartyChannelNo());
        assertEquals("设备B", bo2.getDeviceParentName());
        assertEquals("device_code_2", bo2.getDeviceParentCode());
        assertEquals(0, bo2.getOnlineStatus());
    }

    @Test
    void openDoor_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "door001";

        AccessOpenBo openBo = new AccessOpenBo()
                .setAreaId(areaId)
                .setThirdPartyDeviceUniqueCode(thirdPartyDeviceUniqueCode);

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock开门
        DoorControlResponse doorControlResponse = new DoorControlResponse();
        DoorControlResponseData doorControlResult = new DoorControlResponseData();
        doorControlResult.setControlResultCode(0);
        doorControlResponse.setData(List.of(doorControlResult));
        when(iSecureV2SDK.doorControl(eq(mockConfig), any(DoorControlRequest.class))).thenReturn(doorControlResponse);

        // 执行测试
        accessControl.openDoor(openBo);

        // 验证调用
        verify(iSecureV2SDK).doorControl(eq(mockConfig), argThat(request ->
                request.getDoorIndexCodes().contains(thirdPartyDeviceUniqueCode) &&
                        request.getControlType() == DoorControlTypeEnum.DOOR_OPEN.getValue()
        ));
    }

    @Test
    void openDoor_EmptyResponse() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "door001";

        AccessOpenBo openBo = new AccessOpenBo()
                .setAreaId(areaId)
                .setThirdPartyDeviceUniqueCode(thirdPartyDeviceUniqueCode);

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock开门 - 返回空数据
        DoorControlResponse doorControlResponse = new DoorControlResponse();
        when(iSecureV2SDK.doorControl(eq(mockConfig), any(DoorControlRequest.class))).thenReturn(doorControlResponse);

        // 执行测试并验证异常
        BusinessRuntimeException exception = assertThrows(BusinessRuntimeException.class, () -> accessControl.openDoor(openBo));
        assertEquals("远程开门失败：数据解析不正确", exception.getMessage());
    }

    @Test
    void openDoor_ErrorResponse() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "door001";

        AccessOpenBo openBo = new AccessOpenBo()
                .setAreaId(areaId)
                .setThirdPartyDeviceUniqueCode(thirdPartyDeviceUniqueCode);

        // Mock配置服务
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock开门 - 返回错误
        DoorControlResponse doorControlResponse = new DoorControlResponse();
        DoorControlResponseData doorControlResult = new DoorControlResponseData();
        doorControlResult.setControlResultCode(1);
        doorControlResult.setControlResultDesc("设备离线");
        doorControlResponse.setData(List.of(doorControlResult));
        when(iSecureV2SDK.doorControl(eq(mockConfig), any(DoorControlRequest.class))).thenReturn(doorControlResponse);

        // 执行测试并验证异常
        BusinessRuntimeException exception = assertThrows(BusinessRuntimeException.class, () -> accessControl.openDoor(openBo));
        assertEquals("远程开门失败: 设备离线", exception.getMessage());
    }

    @Test
    void waitConfigFinish_InterruptedException() {
        ReflectionTestUtils.setField(accessControl, "MAX_WAIT_CONFIG_SECONDS", 1);
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        String taskId = "task_interrupt";
        // Mock configRateSearch 抛出 InterruptedException
        when(iSecureV2SDK.configRateSearch(eq(mockConfig), any(ConfigRateSearchRequest.class)))
                .thenAnswer(invocation -> { throw new InterruptedException("interrupt"); });
        BusinessRuntimeException exception = assertThrows(BusinessRuntimeException.class, () -> {
            ReflectionTestUtils.invokeMethod(accessControl, "waitConfigFinish", mockConfig, taskId);
        });
        assertEquals("配置下发被中断", exception.getMessage());
    }

    @Test
    void cardBinding_PersonIdException() {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setAreaId(areaId);
        AccessCardBindingBo cardBo = new AccessCardBindingBo().setLocalPersonBo(localPersonBo).setCardNumber("123");
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId))).thenReturn(mockConfig);
        when(person.getThirdPartyPersonId(localPersonBo)).thenThrow(new NotFoundException());
        assertThrows(NotFoundException.class, () -> accessControl.cardBinding(cardBo));
    }

    @Test
    void cardUnbind_PersonIdException() {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setAreaId(areaId);
        AccessCardUnBindingBo cardBo = new AccessCardUnBindingBo().setLocalPersonBo(localPersonBo).setCardNumber("123");
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId))).thenReturn(mockConfig);
        when(person.getThirdPartyPersonId(localPersonBo)).thenThrow(new NotFoundException());
        assertThrows(NotFoundException.class, () -> accessControl.cardUnbind(cardBo));
    }

    @Test
    void getCardType_Success() {
        // 测试不同卡类型ID的转换逻辑
        for (CardTypeEnum type : CardTypeEnum.values()) {
            Integer result = ReflectionTestUtils.invokeMethod(accessControl, "getCardType", type.getId());
            assertEquals(type.getCode(), result, "卡类型 " + type.getName() + " 转换错误");
        }

        // 测试null值处理
        Integer nullResult =  ReflectionTestUtils.invokeMethod(accessControl, "getCardType", (Integer) null);
        assertEquals(CardTypeEnum.IC_CARD.getCode(), nullResult, "处理null值应返回IC_CARD的code");

        // 测试不存在的类型ID
        Integer unknownResult = ReflectionTestUtils.invokeMethod(accessControl, "getCardType", 9999);
        assertEquals(CardTypeEnum.IC_CARD.getCode(), unknownResult, "处理未知类型应返回IC_CARD的code");
    }
}