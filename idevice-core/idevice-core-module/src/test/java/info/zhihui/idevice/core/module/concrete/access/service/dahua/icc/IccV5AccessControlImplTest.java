package info.zhihui.idevice.core.module.concrete.access.service.dahua.icc;

import com.dahuatech.icc.assesscontrol.enums.PrivilegeTypeEnum;
import com.dahuatech.icc.brm.enums.CardCategory;
import com.dahuatech.icc.brm.enums.CardType;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardAddRequest;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardReturnRequest;
import com.dahuatech.icc.brm.model.v202010.device.BrmDevice;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.Person;
import info.zhihui.idevice.core.module.concrete.foundation.service.dahua.icc.IccV5FoundationImpl;
import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.expection.IccRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_ACCESS_TREE_QUERY_TYPE;
import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_DEVICE_TREE_QUERY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class IccV5AccessControlImplTest {

    @Mock
    private IccV5SDK iccV5SDK;
    @Mock
    private Person person;
    @Mock
    private DeviceModuleConfigService deviceModuleConfigService;
    @Mock
    private IccV5FoundationImpl iccFoundation;

    private IccV5AccessControlImpl iccAccess;

    @BeforeEach
    void setUp() {
        iccAccess = new IccV5AccessControlImpl(
                deviceModuleConfigService,
                iccV5SDK,
                person,
                iccFoundation
        );
    }

    @Test
    void grantAccess_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyPersonId = "100";
        String thirdPartyDeviceUniqueCode = "device_code_001";
        String personCode = "person_code_001";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonAuthBo authBo = new AccessPersonAuthBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock人员查询
        BrmPersonQueryResponse personQueryResponse = new BrmPersonQueryResponse();
        PersonData personData = new PersonData();
        personData.setCode(personCode);
        personQueryResponse.setData(personData);
        when(iccV5SDK.brmPersonQuery(eq(mockConfig), argThat(request ->
                request.getId() != null &&
                        request.getId().equals(Long.parseLong(thirdPartyPersonId))
        ))).thenReturn(personQueryResponse);

        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // 执行测试
        iccAccess.grantAccess(authBo);

        // 验证调用
        verify(iccV5SDK).accessAuthPeople(eq(mockConfig), argThat(request ->
                request.getPersonCodes().contains(personCode) &&
                        request.getPrivilegeDetails().stream().anyMatch(detail ->
                                detail.getPrivilegeType().equals(Integer.valueOf(PrivilegeTypeEnum.ChannelCodeAuth.code)) &&
                                        detail.getResourceCode().equals(thirdPartyDeviceUniqueCode)
                        )
        ));
    }

    @Test
    void grantAccess_PersonNotFound() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "device_code_001";
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        AccessPersonAuthBo authBo = new AccessPersonAuthBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn("101");

        // Mock人员查询返回空
        when(iccV5SDK.brmPersonQuery(eq(mockConfig), any(BrmPersonQueryRequest.class)))
                .thenReturn(null);

        // 执行测试并验证异常
        assertThrows(BusinessRuntimeException.class, () -> iccAccess.grantAccess(authBo));

        // 验证没有调用授权接口
        verify(iccV5SDK, never()).accessAuthPeople(any(), any());
    }

    @Test
    void grantAccess_PersonCodeNull_ThrowsException() {
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
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);
        // 返回personQueryResponse为非空但getData为null
        BrmPersonQueryResponse personQueryResponse = new BrmPersonQueryResponse();
        personQueryResponse.setData(null);
        when(iccV5SDK.brmPersonQuery(eq(mockConfig), any(BrmPersonQueryRequest.class)))
                .thenReturn(personQueryResponse);
        assertThrows(BusinessRuntimeException.class, () -> iccAccess.grantAccess(authBo));
        verify(iccV5SDK, never()).accessAuthPeople(any(), any());
    }

    @Test
    void revokeAccess_Success() {
        // 准备测试数据
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String thirdPartyDeviceUniqueCode = "device_code_001";
        String thirdPartyPersonId = "100";
        String personCode = "person_code_001";

        AccessPersonRevokeBo revokeBo = new AccessPersonRevokeBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock人员查询
        BrmPersonQueryResponse personQueryResponse = new BrmPersonQueryResponse();
        personQueryResponse.setCode(personCode);
        when(iccV5SDK.brmPersonQuery(eq(mockConfig), argThat(request ->
                request.getId() != null &&
                        request.getId().equals(Long.parseLong(thirdPartyPersonId))
        ))).thenReturn(personQueryResponse);

        // 执行测试
        iccAccess.revokeAccess(revokeBo);

        // 验证调用
        verify(iccV5SDK).accessDeletePersonSinglePrivilege(eq(mockConfig), argThat(request ->
                request.getPersonCode().equals(personCode) &&
                        request.getDeleteDetails().stream().anyMatch(detail ->
                                detail.getPrivilegeType().equals(Integer.valueOf(PrivilegeTypeEnum.ChannelCodeAuth.code)) &&
                                        detail.getResourceCode().equals(thirdPartyDeviceUniqueCode)
                        )
        ));
    }

    @Test
    void revokeAccess_PersonNotFound() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String thirdPartyDeviceUniqueCode = "device_code_001";

        AccessPersonRevokeBo revokeBo = new AccessPersonRevokeBo()
                .setLocalPersonBo(localPersonBo)
                .setThirdPartyDeviceUniqueCodes(List.of(thirdPartyDeviceUniqueCode));


        // Mock关联关系查询抛出异常
        when(person.getThirdPartyPersonId(localPersonBo))
                .thenThrow(new NotFoundException());

        // 执行测试
        iccAccess.revokeAccess(revokeBo);

        // 验证没有调用撤销授权接口
        verify(iccV5SDK, never()).accessDeletePersonSinglePrivilege(any(), any());
    }

    @Test
    void openDoor_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String thirdPartyDeviceUniqueCode = "device_code_001";

        AccessOpenBo openBo = new AccessOpenBo()
                .setAreaId(areaId)
                .setThirdPartyDeviceUniqueCode(thirdPartyDeviceUniqueCode);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // 执行测试
        iccAccess.openDoor(openBo);

        // 验证调用
        verify(iccV5SDK).accessOpenDoor(eq(mockConfig), argThat(request ->
                request.getChannelCodeList().contains(thirdPartyDeviceUniqueCode)
        ));
    }

    @Test
    void updateAccessImg_Success() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        AccessPersonFaceUpdateBo updateBo = new AccessPersonFaceUpdateBo()
                .setLocalPersonBo(localPersonBo);

        // 执行测试
        iccAccess.updateAccessImg(updateBo);

        // 验证调用
        verify(person).updatePersonFace(argThat(personFaceUpdateBo ->
                personFaceUpdateBo.getLocalPersonBo().equals(localPersonBo)
        ));
    }

    @Test
    void cardBinding_Success() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String thirdPartyPersonId = "100";
        String cardNumber = "88822221";

        AccessCardBindingBo cardBo = new AccessCardBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // 执行测试
        iccAccess.cardBinding(cardBo);

        // 验证调用
        verify(iccV5SDK).brmCardAdd(eq(mockConfig), argThat(request ->
                request.getCardNumber().equals(cardNumber) &&
                        request.getPersonId().equals(thirdPartyPersonId) &&
                        request.getCategory().equals(CardCategory.IC.getValue()) &&
                        request.getCardType().equals(CardType.NORMAL_CARD.getCode())
        ));
    }

    @Test
    void cardBinding_CardExist_Success() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String thirdPartyPersonId = "100";
        String cardNumber = "88822221";

        AccessCardBindingBo cardBo = new AccessCardBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock卡片添加抛出卡片已存在异常
        IccRuntimeException cardExistException = new IccRuntimeException("28140000", "xxx");
        when(iccV5SDK.brmCardAdd(eq(mockConfig), any(BrmCardAddRequest.class)))
                .thenThrow(cardExistException);

        // Mock卡片查询
        BrmCardQueryResponse cardQueryResponse = new BrmCardQueryResponse();
        BrmCardDetail cardData = new BrmCardDetail();
        cardData.setPersonId(0L);
        cardQueryResponse.setData(cardData);
        when(iccV5SDK.brmCardQuery(eq(mockConfig), any(BrmCardQueryRequest.class)))
                .thenReturn(cardQueryResponse);

        // 执行测试
        iccAccess.cardBinding(cardBo);

        // 验证调用
        verify(iccV5SDK).brmCardActive(eq(mockConfig), argThat(request ->
                request.getCardNumber().equals(cardNumber) &&
                        request.getPersonId().equals(Long.parseLong(thirdPartyPersonId)) &&
                        request.getCategory().equals(CardCategory.IC.getValue())
        ));
    }

    @Test
    void cardBinding_CardExist_DifferentPerson() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String thirdPartyPersonId = "100";
        String cardNumber = "88822221";

        AccessCardBindingBo cardBo = new AccessCardBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn(thirdPartyPersonId);

        // Mock卡片添加抛出卡片已存在异常
        IccRuntimeException cardExistException = new IccRuntimeException("28140000", "xxx");
        when(iccV5SDK.brmCardAdd(eq(mockConfig), any(BrmCardAddRequest.class)))
                .thenThrow(cardExistException);

        // Mock卡片查询返回不同的持有人
        BrmCardQueryResponse cardQueryResponse = new BrmCardQueryResponse();
        BrmCardDetail cardData = new BrmCardDetail();
        cardData.setPersonId(200L);
        cardQueryResponse.setData(cardData);
        when(iccV5SDK.brmCardQuery(eq(mockConfig), any(BrmCardQueryRequest.class)))
                .thenReturn(cardQueryResponse);

        // 执行测试并验证异常
        assertThrows(BusinessRuntimeException.class, () -> iccAccess.cardBinding(cardBo));

        // 验证没有调用激活接口
        verify(iccV5SDK, never()).brmCardActive(any(), any());
    }

    @Test
    void cardUnbind_Success() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String cardNumber = "88822221";
        Long cardId = 1L;

        AccessCardUnBindingBo cardBo = new AccessCardUnBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        // Mock卡片查询
        BrmCardQueryResponse cardQueryResponse = new BrmCardQueryResponse();
        BrmCardDetail cardData = new BrmCardDetail();
        cardData.setId(cardId);
        cardQueryResponse.setData(cardData);
        when(iccV5SDK.brmCardQuery(eq(mockConfig), any(BrmCardQueryRequest.class)))
                .thenReturn(cardQueryResponse);

        // 执行测试
        iccAccess.cardUnbind(cardBo);

        // 验证调用
        verify(iccV5SDK).brmCardReturn(eq(mockConfig), argThat(request ->
                request.getCardIds().contains(cardId)
        ));
        verify(iccV5SDK).brmCardDel(eq(mockConfig), argThat(request ->
                request.getCardIds().contains(cardId)
        ));
    }

    @Test
    void cardUnbind_CardNotExist() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String cardNumber = "88822221";

        AccessCardUnBindingBo cardBo = new AccessCardUnBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        // Mock卡片查询抛出卡片不存在异常
        IccRuntimeException cardNotExistException = new IccRuntimeException("28140001", "xxx");
        when(iccV5SDK.brmCardQuery(eq(mockConfig), any(BrmCardQueryRequest.class)))
                .thenThrow(cardNotExistException);

        // 执行测试
        iccAccess.cardUnbind(cardBo);

        // 验证没有调用解绑和删除接口
        verify(iccV5SDK, never()).brmCardReturn(any(), any());
        verify(iccV5SDK, never()).brmCardDel(any(), any());
    }

    @Test
    void cardUnbind_CardAlreadyUnbound() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String cardNumber = "88822221";
        Long cardId = 1L;

        AccessCardUnBindingBo cardBo = new AccessCardUnBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);

        // Mock卡片查询
        BrmCardQueryResponse cardQueryResponse = new BrmCardQueryResponse();
        BrmCardDetail cardData = new BrmCardDetail();
        cardData.setId(cardId);
        cardQueryResponse.setData(cardData);
        when(iccV5SDK.brmCardQuery(eq(mockConfig), any(BrmCardQueryRequest.class)))
                .thenReturn(cardQueryResponse);

        // Mock解绑抛出卡片已解绑异常
        IccRuntimeException cardOperateException = new IccRuntimeException("28140002", "xxx");
        when(iccV5SDK.brmCardReturn(eq(mockConfig), any(BrmCardReturnRequest.class)))
                .thenThrow(cardOperateException);

        // 执行测试
        iccAccess.cardUnbind(cardBo);

        // 验证调用删除接口
        verify(iccV5SDK).brmCardDel(eq(mockConfig), argThat(request ->
                request.getCardIds().contains(cardId)
        ));
    }

    @Test
    void findAllDeviceChannelList_Success() {
        // 准备测试数据
        Integer areaId = 1;
        String deviceCode = "device_001";
        String deviceName = "测试设备";
        String channelCode = "channel_001";
        String channelName = "测试通道";
        Integer channelSeq = 1;
        Integer isOnline = 1;
        String ownerCode = "org_001";

        AccessDeviceQueryBo queryBo = new AccessDeviceQueryBo()
                .setAreaId(areaId);

        // Mock配置服务
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // 准备通道数据
        Channel channel = new Channel();
        channel.setChannelCode(channelCode);
        channel.setChannelName(channelName);
        channel.setChannelSeq(channelSeq);
        channel.setDeviceCode(deviceCode);
        channel.setIsOnline(isOnline);
        channel.setOwnerCode(ownerCode);
        List<Channel> mockChannels = List.of(channel);

        // 准备设备数据
        BrmDevice device = new BrmDevice();
        device.setDeviceCode(deviceCode);
        device.setDeviceName(deviceName);
        List<BrmDevice> mockDevices = List.of(device);

        // Mock通道查询
        when(iccV5SDK.findAllByPage(
                eq(mockConfig),
                any(BrmChannelPageRequest.class),
                any()
        )).thenReturn(mockChannels);

        // Mock设备查询
        when(iccV5SDK.findAllByPage(
                eq(mockConfig),
                any(BrmDevicePageRequest.class),
                any()
        )).thenReturn(mockDevices);

        // Mock设备树映射
        Map<String, TreeItem> mockTreeMap = new HashMap<>();
        TreeItem treeItem = new TreeItem();
        treeItem.setId(ownerCode);
        treeItem.setName("组织1");
        mockTreeMap.put(ownerCode, treeItem);

        when(iccFoundation.getDeviceTreeMap(
                eq(mockConfig),
                eq(DEFAULT_DEVICE_TREE_QUERY_ID),
                eq(DEFAULT_ACCESS_TREE_QUERY_TYPE)
        )).thenReturn(mockTreeMap);

        // Mock设置路径信息方法
        when(iccFoundation.setTreeItemPathData(
                anyList(),
                eq(mockTreeMap),
                any(),
                any(),
                any()
        )).thenAnswer(invocation -> invocation.getArgument(0));

        // 执行测试
        List<AccessFetchInfoBo> result = iccAccess.findAllDeviceChannelList(queryBo);

        // 验证结果
        assertEquals(1, result.size());
        AccessFetchInfoBo deviceInfo = result.get(0);
        assertEquals(channelCode, deviceInfo.getThirdPartyDeviceUniqueCode());
        assertEquals(channelName, deviceInfo.getThirdPartyDeviceName());
        assertEquals(channelSeq.toString(), deviceInfo.getThirdPartyChannelNo());
        assertEquals(deviceCode, deviceInfo.getDeviceParentCode());
        assertEquals(deviceName, deviceInfo.getDeviceParentName());
        assertEquals(isOnline, deviceInfo.getOnlineStatus());
        assertEquals(areaId, deviceInfo.getThirdPartyAreaId());
        assertEquals(ownerCode, deviceInfo.getOwnerCode());

        // 验证调用
        verify(iccV5SDK, times(2)).findAllByPage(
                eq(mockConfig),
                any(),
                any()
        );

        // 验证调用设置路径方法
        verify(iccFoundation).setTreeItemPathData(
                eq(result),
                eq(mockTreeMap),
                any(),
                any(),
                any()
        );
    }

    @Test
    void syncPermissions_JustLog() {
        AccessPermissionSyncBo syncBo = new AccessPermissionSyncBo();
        iccAccess.syncPermissions(syncBo);
        // 仅验证无异常即可
    }

    @Test
    void findAllDeviceChannelList_EmptyChannels() {
        Integer areaId = 1;
        AccessDeviceQueryBo queryBo = new AccessDeviceQueryBo().setAreaId(areaId);
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);
        when(iccV5SDK.findAllByPage(eq(mockConfig), any(BrmChannelPageRequest.class), any())).thenReturn(List.of());

        // Mock getDeviceTreeMap不会被调用，因为通道为空

        List<AccessFetchInfoBo> result = iccAccess.findAllDeviceChannelList(queryBo);
        assertEquals(0, result.size());

        // 验证没有调用设备树相关方法
        verify(iccFoundation, never()).getDeviceTreeMap(any(), any(), any());
        verify(iccFoundation, never()).setTreeItemPathData(any(), any(), any(), any(), any());
    }

    @Test
    void findAllDeviceChannelList_EmptyDevices() {
        Integer areaId = 1;
        String deviceCode = "device_001";
        String channelCode = "channel_001";
        String ownerCode = "org_001";
        AccessDeviceQueryBo queryBo = new AccessDeviceQueryBo().setAreaId(areaId);
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        Channel channel = new Channel();
        channel.setChannelCode(channelCode);
        channel.setDeviceCode(deviceCode);
        channel.setChannelSeq(1);
        channel.setChannelName("通道");
        channel.setIsOnline(1);
        channel.setOwnerCode(ownerCode);

        when(iccV5SDK.findAllByPage(eq(mockConfig), any(BrmChannelPageRequest.class), any()))
                .thenReturn(List.of(channel));
        when(iccV5SDK.findAllByPage(eq(mockConfig), any(BrmDevicePageRequest.class), any()))
                .thenReturn(List.of());

        // Mock设备树映射
        Map<String, TreeItem> mockTreeMap = new HashMap<>();
        TreeItem treeItem = new TreeItem();
        treeItem.setId(ownerCode);
        treeItem.setName("组织1");
        mockTreeMap.put(ownerCode, treeItem);

        when(iccFoundation.getDeviceTreeMap(
                eq(mockConfig),
                eq(DEFAULT_DEVICE_TREE_QUERY_ID),
                eq(DEFAULT_ACCESS_TREE_QUERY_TYPE)
        )).thenReturn(mockTreeMap);

        // Mock设置路径信息方法
        when(iccFoundation.setTreeItemPathData(
                anyList(),
                eq(mockTreeMap),
                any(),
                any(),
                any()
        )).thenAnswer(invocation -> invocation.getArgument(0));

        List<AccessFetchInfoBo> result = iccAccess.findAllDeviceChannelList(queryBo);

        assertEquals(1, result.size());
        assertNull(result.get(0).getDeviceParentName());
        assertEquals(channelCode, result.get(0).getThirdPartyDeviceUniqueCode());
        assertEquals(ownerCode, result.get(0).getOwnerCode());
    }

    @Test
    void cardBinding_OtherIccRuntimeException() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String cardNumber = "88822221";
        AccessCardBindingBo cardBo = new AccessCardBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);
        when(person.getThirdPartyPersonId(localPersonBo)).thenReturn("100");
        // 抛出非卡已存在异常
        IccRuntimeException otherException = new IccRuntimeException("other_code", "other");
        when(iccV5SDK.brmCardAdd(eq(mockConfig), any(BrmCardAddRequest.class)))
                .thenThrow(otherException);
        assertThrows(IccRuntimeException.class, () -> iccAccess.cardBinding(cardBo));
        verify(iccV5SDK, never()).brmCardActive(any(), any());
    }

    @Test
    void cardUnbind_OtherIccRuntimeException() {
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(1);
        String cardNumber = "88822221";
        AccessCardUnBindingBo cardBo = new AccessCardUnBindingBo()
                .setLocalPersonBo(localPersonBo)
                .setCardNumber(cardNumber);
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(localPersonBo.getAreaId())))
                .thenReturn(mockConfig);
        // 抛出非卡不存在异常
        IccRuntimeException otherException = new IccRuntimeException("other_code", "other");
        when(iccV5SDK.brmCardQuery(eq(mockConfig), any(BrmCardQueryRequest.class)))
                .thenThrow(otherException);
        assertThrows(IccRuntimeException.class, () -> iccAccess.cardUnbind(cardBo));
        verify(iccV5SDK, never()).brmCardReturn(any(), any());
        verify(iccV5SDK, never()).brmCardDel(any(), any());
    }

}