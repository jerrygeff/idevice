package info.zhihui.idevice.core.module.concrete.foundation.service.dahua.icc;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.dahuatech.icc.brm.enums.BiosignatureEnum;
import com.dahuatech.icc.brm.model.v202010.person.*;
import com.dahuatech.icc.exception.ClientException;
import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.utils.FileUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.AbstractPerson;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;
import info.zhihui.idevice.core.module.relation.service.ThirdPartyRelationService;
import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonAddRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonAddResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.brm.TreeNodeTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.*;

/**
 * 大华基础模块功能
 *
 * @author jerryge
 */
@Service("IccV5FoundationImpl")
@Slf4j
public class IccV5FoundationImpl extends AbstractPerson {
    private final IccV5SDK iccV5SDK;
    private final DeviceModuleConfigService deviceModuleConfigService;

    // 主头像索引号
    private static final Integer MAIN_FACE_INDEX = 1;

    public IccV5FoundationImpl(ThirdPartyRelationService thirdPartyRelationService, IccV5SDK iccV5SDK, DeviceModuleConfigService deviceModuleConfigService) {
        super(thirdPartyRelationService);
        this.iccV5SDK = iccV5SDK;
        this.deviceModuleConfigService = deviceModuleConfigService;
    }

    /**
     * 获取设备树并构建节点映射，优化查找效率
     *
     * @param config   SDK配置
     * @param parentId 父节点ID，默认使用DEFAULT_DEVICE_TREE_QUERY_ID
     * @param type     节点类型，默认使用DEFAULT_DEVICE_TREE_QUERY_TYPE
     * @return 节点ID到节点的映射
     */
    public Map<String, TreeItem> getDeviceTreeMap(IccSdkConfig config, String parentId, String type) {
        List<TreeItem> allTreeItems = getChildTreeItems(config, parentId, type);
        if (CollectionUtils.isEmpty(allTreeItems)) {
            return Collections.emptyMap();
        }
        return allTreeItems.stream().collect(Collectors.toMap(TreeItem::getId, Function.identity()));
    }

    /**
     * 递归获取子树节点
     *
     * @param config   SDK配置
     * @param parentId 父节点ID
     * @param type     节点类型
     * @return 所有子节点
     */
    private List<TreeItem> getChildTreeItems(IccSdkConfig config, String parentId, String type) {
        BrmDeviceTreeListRequest request = new BrmDeviceTreeListRequest();
        request.setId(parentId);
        request.setType(type);
        request.setCheckStat(CHECK_STAT_CHECKED);
        BrmDeviceTreeListResponse response = iccV5SDK.brmDeviceTreeList(config, request);

        List<TreeItem> childTreeItems = response.getData().getValue();
        if (CollectionUtils.isEmpty(childTreeItems)) {
            return List.of();
        }

        List<TreeItem> allTreeItems = new ArrayList<>(childTreeItems);

        // 对非通道节点进行递归查询
        childTreeItems.stream()
                .filter(item -> !TreeNodeTypeEnum.CH.getValue().equals(item.getNodeType()))
                .forEach(item -> allTreeItems.addAll(getChildTreeItems(config, item.getId(), type)));

        return allTreeItems;
    }

    /**
     * 设置设备的组织路径信息
     *
     * @param deviceList 设备信息列表，必须包含ownerCode字段
     * @param treeItemMap 组织节点映射
     * @param getOwnerCode 获取设备所有者编码的函数
     * @param setRegionPathConsumer 设置路径的消费者函数
     * @param setRegionPathNameConsumer 设置路径名的消费者函数
     * @param <T> 设备对象类型
     * @return 更新后的设备信息列表
     */
    public <T> List<T> setTreeItemPathData(List<T> deviceList, Map<String, TreeItem> treeItemMap,
                                          Function<T, String> getOwnerCode,
                                          BiConsumer<T, List<String>> setRegionPathConsumer,
                                          BiConsumer<T, List<String>> setRegionPathNameConsumer) {
        if (CollectionUtils.isEmpty(deviceList) || treeItemMap.isEmpty()) {
            return deviceList;
        }

        for (T device : deviceList) {
            String ownerCode = getOwnerCode.apply(device);
            TreeItem item = treeItemMap.get(ownerCode);
            if (item == null) {
                continue;
            }

            // 使用双端队列优化路径构建，避免后续反转操作
            Deque<String> pathIdDeque = new ArrayDeque<>();
            Deque<String> pathNameDeque = new ArrayDeque<>();

            // 添加当前节点
            pathIdDeque.addFirst(item.getId());
            pathNameDeque.addFirst(item.getName());

            // 向上遍历父节点构建路径
            String parentId = item.getPId();
            while (parentId != null && !DEFAULT_DEVICE_TREE_QUERY_ID.equals(parentId)) {
                TreeItem parentItem = treeItemMap.get(parentId);
                if (parentItem == null) {
                    break;
                }

                pathIdDeque.addFirst(parentItem.getId());
                pathNameDeque.addFirst(parentItem.getName());
                parentId = parentItem.getPId();
            }

            // 设置路径信息
            setRegionPathConsumer.accept(device, new ArrayList<>(pathIdDeque));
            setRegionPathNameConsumer.accept(device, new ArrayList<>(pathNameDeque));
        }

        return deviceList;
    }

    @SneakyThrows
    public void updatePersonFace(PersonFaceUpdateBo personFaceUpdateBo) {
        Long personId = Long.parseLong(getThirdPartyPersonId(personFaceUpdateBo.getLocalPersonBo()));

        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, personFaceUpdateBo.getLocalPersonBo().getAreaId());
        String filePath = uploadImage(config, personFaceUpdateBo.getFaceImageResource());

        updatePersonFaceImage(config, filePath, personId);
        log.info("人脸信息已更新");
    }

    private String uploadImage(IccSdkConfig config, FileResource faceImageResource) throws IOException {
        String base64String = FileUtil.getBase64FromInputStream(faceImageResource.getInputStream());

        BrmImageUploadRequest imageUploadRequest = new BrmImageUploadRequest.Builder()
                .fileSize(faceImageResource.getFileSize())
                .contentType(faceImageResource.getContentType())
                .originBase64(base64String)
                .build();
        BrmImageUploadResponse brmImageUploadResponse = iccV5SDK.brmImageUpload(config, imageUploadRequest);
        // 非0为异常状态
        if (!"0".equals(brmImageUploadResponse.getData().getResult())) {
            throw new BusinessRuntimeException("上传的图片不符合规格");
        }

        return brmImageUploadResponse.getData().getFileUrl();
    }

    private void updatePersonFaceImage(IccSdkConfig config, String filePath, Long personId) throws ClientException {
        PersonBioSignatures bioSignatures = new PersonBioSignatures();
        bioSignatures.setPath(filePath);
        bioSignatures.setType(BiosignatureEnum.FACE_IMG.getId());
        bioSignatures.setIndex(MAIN_FACE_INDEX);
        bioSignatures.setPersonId(personId);

        BrmPersonBatchUpdateImgRequest brmPersonBatchUpdateImgRequest = new BrmPersonBatchUpdateImgRequest.Builder()
                .personBiosignatures(List.of(bioSignatures))
                .build();
        iccV5SDK.brmPersonUpdateFace(config, brmPersonBatchUpdateImgRequest);
    }

    @Override
    @SneakyThrows
    public String addLocalPersonToThirdParty(PersonAddBo personAddBo) {
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, personAddBo.getLocalPersonBo().getAreaId());

        Long id = generatePersonId(config);
        List<PersonBioSignatures> personBioSignatures = buildPersonBioSignatures(personAddBo.getFaceImageResource(), config);

        BrmPersonAddRequest personAddRequest = BrmPersonAddRequest.builder()
                .service(DEFAULT_SERVICE)
                .id(id)
                .name(personAddBo.getName())
                .departmentId(DEFAULT_DEPARTMENT_ID)
                .code(genPersonCode(personAddBo.getLocalPersonBo()))
                .paperType(personAddBo.getCertificateType().getCode())
                .paperNumber(personAddBo.getCertificateNo())
                .paperAddress(DEFAULT_PAPER_ADDRESS)
                .phone(personAddBo.getPhone())
                .sex(personAddBo.getGender())
                .ownerCode(personAddBo.getOrganizationCode())
                .personBiosignatures(personBioSignatures)
                .build();
        BrmPersonAddResponse personAddResponse = iccV5SDK.brmPersonAdd(config, personAddRequest);
        String personId = personAddResponse.getData().getId().toString();

        thirdPartyRelationService.add(new ThirdPartyRelationBo()
                .setAreaId(personAddBo.getLocalPersonBo().getAreaId())
                .setLocalModuleName(personAddBo.getLocalPersonBo().getLocalModuleName())
                .setLocalBusinessKey(personAddBo.getLocalPersonBo().getLocalPersonId().toString())
                .setThirdPartyModuleName(getModuleName())
                .setThirdPartyBusinessKey(personId)
        );

        return personId;
    }

    private Long generatePersonId(IccSdkConfig config) throws ClientException {
        BrmPersonGenIdRequest personGenIdRequest = new BrmPersonGenIdRequest();
        BrmPersonGenIdResponse personGenIdResponse = iccV5SDK.brmPersonGenId(config, personGenIdRequest);
        return personGenIdResponse.getData().getId();
    }

    private List<PersonBioSignatures> buildPersonBioSignatures(List<FileResource> fileResourceList, IccSdkConfig config) {
        if (CollectionUtils.isEmpty(fileResourceList)) {
            return new ArrayList<>();
        }
        List<PersonBioSignatures> result = new ArrayList<>();
        for (FileResource fileResource : fileResourceList) {
            try {
                String filePath = uploadImage(config, fileResource);
                PersonBioSignatures bioSignature = new PersonBioSignatures();
                bioSignature.setPath(filePath);
                bioSignature.setType(BiosignatureEnum.FACE_IMG.getId());
                bioSignature.setIndex(MAIN_FACE_INDEX);
                result.add(bioSignature);
            } catch (Exception e) {
                log.warn("buildPersonBioSignatures: 图片上传失败，已跳过，原因: {}", e.getMessage(), e);
            }
        }
        return result;
    }


    private String genPersonCode(LocalPersonBo localPersonBo) {
        return localPersonBo.getLocalModuleName() + localPersonBo.getLocalPersonId().toString();
    }

    @Override
    @SneakyThrows
    public void deleteThirdPartyPerson(LocalPersonBo localPersonBo) {
        Long personId = Long.parseLong(getThirdPartyPersonId(localPersonBo));
        IccSdkConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, IccSdkConfig.class, localPersonBo.getAreaId());

        BrmPersonDelRequest request = new BrmPersonDelRequest.Builder().ids(List.of(personId)).build();
        iccV5SDK.brmPersonDel(config, request);

        thirdPartyRelationService.delete(new ThirdPartyRelationQuery()
                .setAreaId(localPersonBo.getAreaId())
                .setLocalModuleName(localPersonBo.getLocalModuleName())
                .setLocalBusinessKey(localPersonBo.getLocalPersonId().toString())
                .setThirdPartyModuleName(getModuleName())
        );
    }
}
