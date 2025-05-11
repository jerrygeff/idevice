package info.zhihui.idevice.core.module.concrete.access.service.dahua.icc;

import com.dahuatech.icc.assesscontrol.enums.PrivilegeTypeEnum;
import com.dahuatech.icc.assesscontrol.model.v202103.channelControl.ChannelControlOpenDoorRequest;
import com.dahuatech.icc.brm.enums.CardCategory;
import com.dahuatech.icc.brm.enums.CardType;
import com.dahuatech.icc.brm.enums.DeviceCategoryEnum;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardActiveRequest;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardAddRequest;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardDelRequest;
import com.dahuatech.icc.brm.model.v202010.card.BrmCardReturnRequest;
import com.dahuatech.icc.brm.model.v202010.device.BrmDevice;
import com.dahuatech.icc.exception.ClientException;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.Person;
import info.zhihui.idevice.core.module.concrete.foundation.service.dahua.icc.IccV5FoundationImpl;
import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5.AuthPersonBatchAddRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5.AuthPersonDeleteSingleRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5.DeleteDetail;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5.PrivilegeDetail;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.brm.UnitTypeEnum;
import info.zhihui.idevice.core.sdk.dahua.icc.expection.IccRuntimeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_ACCESS_TREE_QUERY_TYPE;
import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_DEVICE_TREE_QUERY_ID;


@Service
@Slf4j
public class IccV5AccessControlImpl implements AccessControl {

    private final IccV5SDK iccV5SDK;
    private final Person person;
    private final DeviceModuleConfigService deviceModuleConfigService;
    private final IccV5FoundationImpl iccV5Foundation;

    @Autowired
    public IccV5AccessControlImpl(
            DeviceModuleConfigService deviceModuleConfigService,
            IccV5SDK iccV5SDK,
            @Qualifier("IccV5FoundationImpl") Person person,
            IccV5FoundationImpl iccV5Foundation
    ) {
        this.person = person;
        this.deviceModuleConfigService = deviceModuleConfigService;
        this.iccV5SDK = iccV5SDK;
        this.iccV5Foundation = iccV5Foundation;
    }

    @Override
    public void grantAccess(AccessPersonAuthBo accessPersonAuth) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, accessPersonAuth.getLocalPersonBo().getAreaId());

        AuthPersonBatchAddRequest authPersonBatchAddRequest = buildAuthPersonBatchAddRequest(accessPersonAuth, config);
        iccV5SDK.accessAuthPeople(config, authPersonBatchAddRequest);
        log.debug("进行门禁授权，已下发");
    }

    private AuthPersonBatchAddRequest buildAuthPersonBatchAddRequest(AccessPersonAuthBo accessPersonAuth, IccSdkConfig config) {
        String personId = person.getThirdPartyPersonId(accessPersonAuth.getLocalPersonBo());
        BrmPersonQueryResponse personQueryResponse = iccV5SDK.brmPersonQuery(config, new BrmPersonQueryRequest(Long.parseLong(personId)));
        String personCode = Optional.ofNullable(personQueryResponse).map(BrmPersonQueryResponse::getData).map(PersonData::getCode)
                .orElseThrow(() -> new BusinessRuntimeException("数据异常，没有找到对应的personCode"));

        List<PrivilegeDetail> privilegeDetails = accessPersonAuth.getThirdPartyDeviceUniqueCodes().stream()
                .map(thirdPartyDeviceUniqueCode -> new PrivilegeDetail()
                        .setPrivilegeType(Integer.valueOf(PrivilegeTypeEnum.ChannelCodeAuth.code))
                        .setResourceCode(thirdPartyDeviceUniqueCode))
                .collect(Collectors.toList());
        AuthPersonBatchAddRequest authPersonBatchAddRequest = AuthPersonBatchAddRequest.builder()
                .personCodes(List.of(personCode))
                .privilegeDetails(privilegeDetails)
                .timeQuantumId(IccBrmConstant.DEFAULT_TIME_QUANTUM_ID)
                .build();
        // 自定义授权有效期
        if (accessPersonAuth.getStartTime() != null && accessPersonAuth.getEndTime() != null) {
            authPersonBatchAddRequest.setStartDate(SysDateUtil.toDateTimeString(accessPersonAuth.getStartTime()));
            authPersonBatchAddRequest.setEndDate(SysDateUtil.toDateTimeString(accessPersonAuth.getEndTime()));
        }

        return authPersonBatchAddRequest;
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

        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, accessPersonRevokeBo.getLocalPersonBo().getAreaId());
        BrmPersonQueryResponse personQueryResponse = iccV5SDK.brmPersonQuery(config, new BrmPersonQueryRequest(Long.parseLong(personId)));

        // 门禁按通道处理
        List<DeleteDetail> deleteDetails = accessPersonRevokeBo.getThirdPartyDeviceUniqueCodes().stream()
                .map(resourceCode -> new DeleteDetail()
                        .setPrivilegeType(Integer.valueOf(PrivilegeTypeEnum.ChannelCodeAuth.code))
                        .setResourceCode(resourceCode))
                .collect(Collectors.toList());
        AuthPersonDeleteSingleRequest request = AuthPersonDeleteSingleRequest.builder()
                .personCode(personQueryResponse.getCode())
                .deleteDetails(deleteDetails)
                .build();
        iccV5SDK.accessDeletePersonSinglePrivilege(config, request);
        log.debug("进行门禁取消授权，下发完成");
    }

    @Override
    public void syncPermissions(AccessPermissionSyncBo accessPermissionSyncBo) {
        log.info("大华平台自动同步权限");
    }

    @Override
    public void updateAccessImg(AccessPersonFaceUpdateBo accessPersonFaceUpdateBo) {
        PersonFaceUpdateBo personFaceUpdateBo = new PersonFaceUpdateBo()
                .setLocalPersonBo(accessPersonFaceUpdateBo.getLocalPersonBo())
                .setFaceImageResource(accessPersonFaceUpdateBo.getFaceImageResource());

        person.updatePersonFace(personFaceUpdateBo);
    }

    @Override
    @SneakyThrows
    public void openDoor(AccessOpenBo accessOpenDoorBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, accessOpenDoorBo.getAreaId());
        ChannelControlOpenDoorRequest request = ChannelControlOpenDoorRequest.builder()
                .channelCodeList(List.of(accessOpenDoorBo.getThirdPartyDeviceUniqueCode()))
                .build();

        iccV5SDK.accessOpenDoor(config, request);
    }

    @Override
    public List<AccessFetchInfoBo> findAllDeviceChannelList(AccessDeviceQueryBo bo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, bo.getAreaId());

        // 获取所有门禁通道
        BrmChannelPageRequest channelRequest = new BrmChannelPageRequest();
        channelRequest.setUnitTypeList(List.of(UnitTypeEnum.ACCESS_CONTROL.getCode()));
        List<Channel> channels = iccV5SDK.findAllByPage(config, channelRequest, iccV5SDK::brmChannelPage);
        if (CollectionUtils.isEmpty(channels)) {
            return List.of();
        }

        // 获取所有设备，创建设备编码到设备名称的映射
        BrmDevicePageRequest deviceRequest = new BrmDevicePageRequest();
        deviceRequest.setCategorys(List.of(DeviceCategoryEnum.ACCESS_CONTROL.getId()));
        List<BrmDevice> devices = iccV5SDK.findAllByPage(config, deviceRequest, iccV5SDK::brmDevicePage);
        Map<String, String> deviceNameMap = devices.stream()
                .collect(Collectors.toMap(BrmDevice::getDeviceCode, BrmDevice::getDeviceName));

        // 将通道信息转换为AccessFetchInfoBo列表
        List<AccessFetchInfoBo> accessDeviceList = convertToAccessFetchInfoBoList(channels, deviceNameMap, bo.getAreaId());

        // 获取完整设备树并构建映射
        Map<String, TreeItem> treeItemMap = iccV5Foundation.getDeviceTreeMap(config, DEFAULT_DEVICE_TREE_QUERY_ID, DEFAULT_ACCESS_TREE_QUERY_TYPE);

        // 设置组织路径信息
        return iccV5Foundation.setTreeItemPathData(
                accessDeviceList,
                treeItemMap,
                AccessFetchInfoBo::getOwnerCode,
                AccessFetchInfoBo::setRegionPath,
                AccessFetchInfoBo::setRegionPathName
        );
    }

    /**
     * 将通道列表和设备名称映射转换为AccessFetchInfoBo列表
     *
     * @param channels      门禁通道列表
     * @param deviceNameMap 设备编码到设备名称的映射
     * @param areaId        区域ID
     * @return 门禁设备信息列表
     */
    private List<AccessFetchInfoBo> convertToAccessFetchInfoBoList(List<Channel> channels, Map<String, String> deviceNameMap, Integer areaId) {
        return channels.stream()
                .map(channel -> {
                    AccessFetchInfoBo device = new AccessFetchInfoBo();
                    device.setThirdPartyDeviceName(channel.getChannelName());
                    device.setThirdPartyDeviceUniqueCode(channel.getChannelCode());
                    device.setThirdPartyChannelNo(channel.getChannelSeq().toString());
                    device.setOnlineStatus(channel.getIsOnline());
                    device.setDeviceParentCode(channel.getDeviceCode());
                    device.setDeviceParentName(deviceNameMap.getOrDefault(channel.getDeviceCode(), null));
                    device.setOwnerCode(channel.getOwnerCode());
                    device.setThirdPartyAreaId(areaId);
                    return device;
                })
                .collect(Collectors.toList());
    }

    /**
     * 大华平台的门禁：
     * 从 V1.1.4 版本开始，门禁默认按人员授权，人员所带的卡、指纹、人脸会自动下发；
     * 新增的卡、指纹、人脸也会自动下发，无需额外授权
     */
    @Override
    @SneakyThrows
    public void cardBinding(AccessCardBindingBo cardAuthBo) {
        BrmCardAddRequest request = buildBrmCardAddRequest(cardAuthBo);
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, cardAuthBo.getLocalPersonBo().getAreaId());

        try {
            iccV5SDK.brmCardAdd(config, request);
        } catch (IccRuntimeException e) {
            if (IccBrmConstant.ERROR_CODE_CARD_EXIST.equals(e.getCode())) {
                // 如果卡存在，尝试激活卡
                cardActiveWhenCardExist(config, request);
                return;
            }

            // 其他情况的异常，原样返回
            throw e;
        }

    }

    private BrmCardAddRequest buildBrmCardAddRequest(AccessCardBindingBo cardAuthBo) throws ClientException {
        String startStr, endStr;
        if (cardAuthBo.getStartTime() == null || cardAuthBo.getEndTime() == null) {
            LocalDateTime now = LocalDateTime.now();
            startStr = SysDateUtil.toDateTimeString(now);
            endStr = SysDateUtil.toDateTimeString(now.plusYears(IccBrmConstant.DEFAULT_CARD_VALID_YEAR));
        } else {
            startStr = SysDateUtil.toDateTimeString(cardAuthBo.getStartTime());
            endStr = SysDateUtil.toDateTimeString(cardAuthBo.getEndTime());
        }

        BrmCardAddRequest request = new BrmCardAddRequest.Builder()
                .departmentId(IccBrmConstant.DEFAULT_DEPARTMENT_ID)
                .cardType(getCardType(cardAuthBo.getCardType()))
                .category(CardCategory.IC.getValue())
                .cardNumber(cardAuthBo.getCardNumber())
                .startDate(startStr)
                .endDate(endStr)
                .build();

        String personId = person.getThirdPartyPersonId(cardAuthBo.getLocalPersonBo());
        request.setPersonId(personId);

        return request;
    }

    private String getCardType(Integer carId) {
        if (carId == null) {
            return CardType.NORMAL_CARD.getCode();
        }

        for (CardType cardType: CardType.values()) {
            if (cardType.getCode().equals(carId.toString())) {
                return cardType.getCode();
            }
        }

        return CardType.NORMAL_CARD.getCode();
    }

    private void cardActiveWhenCardExist(IccSdkConfig config, BrmCardAddRequest request) throws ClientException {
        BrmCardQueryResponse res = iccV5SDK.brmCardQuery(config, new BrmCardQueryRequest(request.getCardNumber()));
        Long existPersonId = res.getData().getPersonId();
        log.debug("卡片已存在，持有人：{}", existPersonId);

        // 校验是否存在持有人，不存在激活
        if (existPersonId == null || existPersonId == 0) {
            BrmCardActiveRequest activeRequest = new BrmCardActiveRequest.Builder()
                    .cardNumber(request.getCardNumber())
                    .personId(Long.parseLong(request.getPersonId()))
                    .departmentId(IccBrmConstant.DEFAULT_DEPARTMENT_ID)
                    .category(CardCategory.IC.getValue())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .build();
            iccV5SDK.brmCardActive(config, activeRequest);
        } else {
            // 不是要设置的人，报错
            if (!existPersonId.toString().equals(request.getPersonId())) {
                log.error("卡片已存在，卡片目前持有人{}，需要设置的持有人{}", existPersonId, request.getPersonId());
                throw new BusinessRuntimeException("卡片已存在,持有人不相同");
            }
        }
    }

    @Override
    public void cardUnbind(AccessCardUnBindingBo cardBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, cardBo.getLocalPersonBo().getAreaId());

        Long cardId;
        try {
            BrmCardQueryResponse res = iccV5SDK.brmCardQuery(config, new BrmCardQueryRequest(cardBo.getCardNumber()));
            cardId = res.getData().getId();
        } catch (IccRuntimeException e) {
            // 卡已经不存在，忽略后面的操作，直接退出
            if (IccBrmConstant.ERROR_CODE_CARD_NOT_EXIST.equals(e.getCode())) {
                log.info("卡片不存在，忽略解绑操作: {}", cardBo.getCardNumber());
                return;
            }
            // 存在其他异常，中断流程
            throw e;
        }

        unbindAndDeleteCard(config, cardId);
    }

    private void unbindAndDeleteCard(IccSdkConfig config, Long cardId) {
        try {
            BrmCardReturnRequest returnRequest = new BrmCardReturnRequest();
            returnRequest.setCardIds(List.of(cardId));
            iccV5SDK.brmCardReturn(config, returnRequest);
        } catch (IccRuntimeException e) {
            if (IccBrmConstant.ERROR_CODE_CARD_OPERATE.equals(e.getCode())) {
                log.info("卡片已解绑，忽略解绑操作: {}", cardId);
            }
        }

        BrmCardDelRequest delRequest = new BrmCardDelRequest();
        delRequest.setCardIds(List.of(cardId));
        iccV5SDK.brmCardDel(config, delRequest);
    }


}
