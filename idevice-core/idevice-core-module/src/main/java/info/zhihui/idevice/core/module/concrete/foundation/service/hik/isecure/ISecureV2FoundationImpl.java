package info.zhihui.idevice.core.module.concrete.foundation.service.hik.isecure;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.utils.FileUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.enums.CommonCertificateTypeEnum;
import info.zhihui.idevice.core.module.concrete.foundation.service.AbstractPerson;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;
import info.zhihui.idevice.core.module.relation.service.ThirdPartyRelationService;
import info.zhihui.idevice.core.sdk.hikvision.isecure.ISecureV2SDK;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CertificateTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant.DEFAULT_ORG_INDEX_CODE;

/**
 * @author jerryge
 */
@Service
@Slf4j
public class ISecureV2FoundationImpl extends AbstractPerson {
    /**
     * 默认人员类型 文档缺失这部分的描述
     */
    private final static String DEFAULT_PERSON_TYPE = "1";

    private final ISecureV2SDK iSecureV2SDK;
    private final DeviceModuleConfigService deviceModuleConfigService;

    public ISecureV2FoundationImpl(ThirdPartyRelationService thirdPartyRelationService, ISecureV2SDK iSecureV2SDK, DeviceModuleConfigService deviceModuleConfigService) {
        super(thirdPartyRelationService);
        this.iSecureV2SDK = iSecureV2SDK;
        this.deviceModuleConfigService = deviceModuleConfigService;
    }

    @Override
    public String addLocalPersonToThirdParty(PersonAddBo personAddBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, personAddBo.getLocalPersonBo().getAreaId());

        List<FaceData> faces = buildFaceDataList(personAddBo.getFaceImageResource());
        PersonAddRequest personAddRequest = PersonAddRequest.builder()
                .personName(personAddBo.getName())
                .gender(personAddBo.getGender().toString())
                .orgIndexCode(StringUtils.isBlank(personAddBo.getOrganizationCode()) ? DEFAULT_ORG_INDEX_CODE : personAddBo.getOrganizationCode())
                .phoneNo(personAddBo.getPhone())
                .certificateType(getCertificateType(personAddBo.getCertificateType()))
                .certificateNo(personAddBo.getCertificateNo())
                .faces(faces)
                .jobNo(personAddBo.getJobNo())
                .personType(DEFAULT_PERSON_TYPE)
                .build();

        PersonAddResponse personAddResponse = iSecureV2SDK.personAdd(config, personAddRequest);
        String personId = personAddResponse.getData().getPersonId();

        thirdPartyRelationService.add(new ThirdPartyRelationBo()
                .setAreaId(personAddBo.getLocalPersonBo().getAreaId())
                .setLocalModuleName(personAddBo.getLocalPersonBo().getLocalModuleName())
                .setLocalBusinessKey(personAddBo.getLocalPersonBo().getLocalPersonId().toString())
                .setThirdPartyModuleName(getModuleName())
                .setThirdPartyBusinessKey(personId)
        );

        return personId;
    }

    private String getCertificateType(CommonCertificateTypeEnum certificateType) {
        if (certificateType == null) {
            return CertificateTypeEnum.OTHER.getCode();
        }
        return switch (certificateType) {
            case ID_CARD -> CertificateTypeEnum.ID_CARD.getCode();
            case PASSPORT -> CertificateTypeEnum.PASSPORT.getCode();
            case HOUSEHOLD_REGISTER -> CertificateTypeEnum.HOUSEHOLD_REGISTER.getCode();
            case DRIVER_LICENSE -> CertificateTypeEnum.DRIVING_LICENSE.getCode();
            case WORK_PERMIT -> CertificateTypeEnum.WORK_CERTIFICATE.getCode();
            case STUDENT_ID -> CertificateTypeEnum.STUDENT_CERTIFICATE.getCode();
            default -> CertificateTypeEnum.OTHER.getCode();
        };
    }

    @Override
    public void deleteThirdPartyPerson(LocalPersonBo localPersonBo) {
        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, localPersonBo.getAreaId());
        String personId = getThirdPartyPersonId(localPersonBo);

        PersonDeleteRequest personDeleteRequest = PersonDeleteRequest.builder()
                .personIds(List.of(personId))
                .build();
        PersonDeleteResponse personDeleteResponse = iSecureV2SDK.personDelete(config, personDeleteRequest);

        if (personDeleteResponse.getData() != null && !personDeleteResponse.getData().isEmpty()) {
            throw new BusinessRuntimeException("删除人员失败:" + personDeleteResponse.getData().get(0).getMsg());
        }

        thirdPartyRelationService.delete(new ThirdPartyRelationQuery()
                .setAreaId(localPersonBo.getAreaId())
                .setLocalModuleName(localPersonBo.getLocalModuleName())
                .setLocalBusinessKey(localPersonBo.getLocalPersonId().toString())
                .setThirdPartyModuleName(getModuleName())
        );
    }

    private List<FaceData> buildFaceDataList(List<FileResource> faceParam) {
        List<FaceData> res = new ArrayList<>();
        if (!CollectionUtils.isEmpty(faceParam)) {
            for (FileResource fileResource : faceParam) {
                try {
                    res.add(new FaceData()
                            .setFaceData(FileUtil.getBase64FromInputStream(fileResource.getInputStream()))
                    );
                } catch (Exception ignored) {
                }
            }
        }

        return res;
    }

    @Override
    @SneakyThrows
    public void updatePersonFace(PersonFaceUpdateBo personFaceUpdateBo) {
        String personId = getThirdPartyPersonId(personFaceUpdateBo.getLocalPersonBo());

        ISecureSDKConfig config = deviceModuleConfigService.getDeviceConfigValue(AccessControl.class, ISecureSDKConfig.class, personFaceUpdateBo.getLocalPersonBo().getAreaId());
        PersonUniqueKeySearchRequest request = PersonUniqueKeySearchRequest.builder()
                .paramName("personId")
                .paramValue(List.of(personId))
                .build();
        PersonUniqueKeySearchResponse personUniqueKeySearchResponse = iSecureV2SDK.personUniqueKeySearch(config, request);
        if (personUniqueKeySearchResponse.getData().getList().isEmpty()) {
            throw new BusinessRuntimeException("平台上未找到该人员信息");
        }

        String faceData = FileUtil.getBase64FromInputStream(personFaceUpdateBo.getFaceImageResource().getInputStream());
        // 有人有人脸图片就修改
        if (!CollectionUtils.isEmpty(personUniqueKeySearchResponse.getData().getList().get(0).getPersonPhoto()) &&
                StringUtils.isNotBlank(personUniqueKeySearchResponse.getData().getList().get(0).getPersonPhoto().get(0).getPersonPhotoIndexCode())) {

            String faceId = personUniqueKeySearchResponse.getData().getList().get(0).getPersonPhoto().get(0).getPersonPhotoIndexCode();
            PersonFaceUpdateRequest personFaceUpdateRequest = PersonFaceUpdateRequest.builder()
                    .faceId(faceId)
                    .faceData(faceData)
                    .build();
            PersonFaceUpdateResponse personFaceUpdateResponse = iSecureV2SDK.personFaceUpdate(config, personFaceUpdateRequest);
            log.info("修改人脸成功：{}", personFaceUpdateResponse);
        } else {
            // 存在人员但没有人脸就添加
            PersonFaceAddRequest personFaceAddRequest = PersonFaceAddRequest.builder()
                    .personId(personId)
                    .faceData(faceData)
                    .build();
            PersonFaceAddResponse personFaceAddResponse = iSecureV2SDK.personFaceAdd(config, personFaceAddRequest);
            log.info("添加人脸成功：{}", personFaceAddResponse);
        }

    }

}
