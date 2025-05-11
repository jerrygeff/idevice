package info.zhihui.idevice.core.module.concrete.foundation.service.hik.isecure;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.enums.CommonCertificateTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.ISecureV2SDK;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CertificateTypeEnum;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.service.ThirdPartyRelationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ISecureV2FoundationImplTest {

    @Mock
    private ISecureV2SDK iSecureV2SDK;
    @Mock
    private DeviceModuleConfigService deviceModuleConfigService;
    @Mock
    private ThirdPartyRelationService thirdPartyRelationService;

    @InjectMocks
    private ISecureV2FoundationImpl foundation;

    @BeforeEach
    void setUp() {
        foundation = new ISecureV2FoundationImpl(thirdPartyRelationService, iSecureV2SDK, deviceModuleConfigService);
    }

    @Test
    void addLocalPersonToThirdParty_Success() {
        Integer areaId = 1;
        // mock FileResource 并模拟 getInputStream 返回有效图片流
        FileResource mockFileResource = mock(FileResource.class);
        try {
            when(mockFileResource.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(new byte[]{1,2,3,4}));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setLocalModuleName("user").setAreaId(areaId);
        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("张三")
                .setGender(1)
                .setPhone("12345678901")
                .setCertificateType(CommonCertificateTypeEnum.OFFICER_CERTIFICATE)
                .setCertificateNo("123456789012345678")
                .setFaceImageResource(List.of(mockFileResource));

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        PersonAddResponseData data = new PersonAddResponseData();
        data.setPersonId("person_001");
        PersonAddResponse personAddResponse = new PersonAddResponse();
        personAddResponse.setData(data);
        when(iSecureV2SDK.personAdd(eq(mockConfig), any(PersonAddRequest.class))).thenReturn(personAddResponse);

        doNothing().when(thirdPartyRelationService).add(any(ThirdPartyRelationBo.class));

        // mock人脸图片base64
        mockStaticImageUtil();

        String personId = foundation.addLocalPersonToThirdParty(personAddBo);
        assertEquals("person_001", personId);
        verify(iSecureV2SDK).personAdd(eq(mockConfig), any(PersonAddRequest.class));
        verify(thirdPartyRelationService).add(any(ThirdPartyRelationBo.class));
    }

    @Test
    void addLocalPersonToThirdParty_NoFace_ThrowsException() {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setLocalModuleName("user").setAreaId(areaId);
        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("张三")
                .setGender(1)
                .setPhone("12345678901")
                .setCertificateType(CommonCertificateTypeEnum.OFFICER_CERTIFICATE)
                .setCertificateNo("123456789012345678")
                .setFaceImageResource(List.of());
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        PersonAddResponseData data = new PersonAddResponseData();
        data.setPersonId("person_001");
        PersonAddResponse personAddResponse = new PersonAddResponse();
        personAddResponse.setData(data);
        when(iSecureV2SDK.personAdd(eq(mockConfig), any(PersonAddRequest.class))).thenReturn(personAddResponse);

        doNothing().when(thirdPartyRelationService).add(any(ThirdPartyRelationBo.class));

        // 执行测试 - 不应该抛出异常
        String personId = foundation.addLocalPersonToThirdParty(personAddBo);
        assertEquals("person_001", personId);

        // 验证SDK调用和关系添加
        verify(iSecureV2SDK).personAdd(eq(mockConfig), any(PersonAddRequest.class));
        verify(thirdPartyRelationService).add(any(ThirdPartyRelationBo.class));
    }

    @Test
    void updatePersonFace_UpdateSuccess() throws Exception {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setLocalModuleName("user").setAreaId(areaId);
        FileResource mockFileResource = mock(FileResource.class);
        when(mockFileResource.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(new byte[]{1,2,3,4}));
        PersonFaceUpdateBo updateBo = new PersonFaceUpdateBo().setLocalPersonBo(localPersonBo).setFaceImageResource(mockFileResource);
        // mock getThirdPartyPersonId
        ISecureV2FoundationImpl spyFoundation = spy(foundation);
        doReturn("person_001").when(spyFoundation).getThirdPartyPersonId(localPersonBo);
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);
        // 修正为PersonDetail
        PersonDetail data = new PersonDetail();
        PersonPhoto photo = new PersonPhoto();
        photo.setPersonPhotoIndexCode("face_001");
        data.setPersonPhoto(List.of(photo));
        PersonUniqueKeySearchResponse personUniqueKeySearchResponse = new PersonUniqueKeySearchResponse();
        PersonUniqueKeySearchResponseData responseData = new PersonUniqueKeySearchResponseData();
        responseData.setList(List.of(data));
        personUniqueKeySearchResponse.setData(responseData);
        when(iSecureV2SDK.personUniqueKeySearch(eq(mockConfig), any(PersonUniqueKeySearchRequest.class))).thenReturn(personUniqueKeySearchResponse);
        // mock人脸图片base64
        mockStaticImageUtil();
        PersonFaceUpdateResponse updateResponse = new PersonFaceUpdateResponse();
        when(iSecureV2SDK.personFaceUpdate(eq(mockConfig), any(PersonFaceUpdateRequest.class))).thenReturn(updateResponse);
        spyFoundation.updatePersonFace(updateBo);
        verify(iSecureV2SDK).personFaceUpdate(eq(mockConfig), any(PersonFaceUpdateRequest.class));
    }

    @Test
    void updatePersonFace_AddFaceIfNotExist() throws Exception {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setLocalModuleName("user").setAreaId(areaId);
        FileResource mockFileResource = mock(FileResource.class);
        when(mockFileResource.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(new byte[]{1,2,3,4}));
        PersonFaceUpdateBo updateBo = new PersonFaceUpdateBo().setLocalPersonBo(localPersonBo).setFaceImageResource(mockFileResource);
        ISecureV2FoundationImpl spyFoundation = spy(foundation);
        doReturn("person_001").when(spyFoundation).getThirdPartyPersonId(localPersonBo);
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);
        // 修正为PersonDetail
        PersonDetail data = new PersonDetail();
        data.setPersonPhoto(null);
        PersonUniqueKeySearchResponse personUniqueKeySearchResponse = new PersonUniqueKeySearchResponse();
        personUniqueKeySearchResponse.setData(new PersonUniqueKeySearchResponseData());
        personUniqueKeySearchResponse.getData().setList(List.of(data));
        when(iSecureV2SDK.personUniqueKeySearch(eq(mockConfig), any(PersonUniqueKeySearchRequest.class))).thenReturn(personUniqueKeySearchResponse);
        // mock人脸图片base64
        mockStaticImageUtil();
        PersonFaceAddResponse addResponse = new PersonFaceAddResponse();
        when(iSecureV2SDK.personFaceAdd(eq(mockConfig), any(PersonFaceAddRequest.class))).thenReturn(addResponse);
        spyFoundation.updatePersonFace(updateBo);
        verify(iSecureV2SDK).personFaceAdd(eq(mockConfig), any(PersonFaceAddRequest.class));
    }

    @Test
    void updatePersonFace_NotFound_ThrowsException() throws Exception {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setLocalModuleName("user").setAreaId(areaId);
        PersonFaceUpdateBo updateBo = new PersonFaceUpdateBo().setLocalPersonBo(localPersonBo).setFaceImageResource(mock(FileResource.class));
        ISecureV2FoundationImpl spyFoundation = spy(foundation);
        doReturn("person_001").when(spyFoundation).getThirdPartyPersonId(localPersonBo);
        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);
        PersonUniqueKeySearchResponse personUniqueKeySearchResponse = new PersonUniqueKeySearchResponse();
        personUniqueKeySearchResponse.setData(new PersonUniqueKeySearchResponseData());
        personUniqueKeySearchResponse.getData().setList(List.of());
        when(iSecureV2SDK.personUniqueKeySearch(eq(mockConfig), any(PersonUniqueKeySearchRequest.class))).thenReturn(personUniqueKeySearchResponse);
        mockStaticImageUtil();
        BusinessRuntimeException exception = assertThrows(BusinessRuntimeException.class, () -> spyFoundation.updatePersonFace(updateBo));
        assertTrue(exception.getMessage().contains("平台上未找到该人员信息"));
    }

    @Test
    void deleteThirdPartyPerson_Success() {
        // Setup
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock getThirdPartyPersonId
        ISecureV2FoundationImpl spyFoundation = spy(foundation);
        doReturn("person_001").when(spyFoundation).getThirdPartyPersonId(eq(localPersonBo));

        // Mock personDelete response
        PersonDeleteResponse deleteResponse = new PersonDeleteResponse();
        deleteResponse.setData(List.of());
        when(iSecureV2SDK.personDelete(eq(mockConfig), any(PersonDeleteRequest.class))).thenReturn(deleteResponse);

        // Execute
        spyFoundation.deleteThirdPartyPerson(localPersonBo);

        // Verify
        verify(iSecureV2SDK).personDelete(eq(mockConfig),
                argThat(request -> request.getPersonIds().contains("person_001")));
        verify(thirdPartyRelationService).delete(argThat(query ->
                query.getAreaId().equals(areaId) &&
                query.getLocalModuleName().equals("user") &&
                query.getLocalBusinessKey().equals("100")));
    }

    @Test
    void deleteThirdPartyPerson_DeleteFailed_ThrowsException() {
        // Setup
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock getThirdPartyPersonId
        ISecureV2FoundationImpl spyFoundation = spy(foundation);
        doReturn("person_001").when(spyFoundation).getThirdPartyPersonId(eq(localPersonBo));

        // Mock personDelete response with error
        PersonDeleteResult errorData = new PersonDeleteResult();
        errorData.setMsg("Delete failed");
        PersonDeleteResponse deleteResponse = new PersonDeleteResponse();
        deleteResponse.setData(List.of(errorData));
        when(iSecureV2SDK.personDelete(eq(mockConfig), any(PersonDeleteRequest.class))).thenReturn(deleteResponse);

        // Execute and verify exception
        BusinessRuntimeException exception = assertThrows(BusinessRuntimeException.class,
                () -> spyFoundation.deleteThirdPartyPerson(localPersonBo));
        assertTrue(exception.getMessage().contains("删除人员失败"));

        // Verify SDK was called but relation was not deleted
        verify(iSecureV2SDK).personDelete(eq(mockConfig), any(PersonDeleteRequest.class));
        verify(thirdPartyRelationService, never()).delete(any());
    }

    @Test
    void addLocalPersonToThirdParty_WithDifferentCertificateTypes() {
        // 测试不同证件类型
        testAddPersonWithCertificateType(CommonCertificateTypeEnum.ID_CARD);
        testAddPersonWithCertificateType(CommonCertificateTypeEnum.PASSPORT);
        testAddPersonWithCertificateType(CommonCertificateTypeEnum.HOUSEHOLD_REGISTER);
        testAddPersonWithCertificateType(CommonCertificateTypeEnum.DRIVER_LICENSE);
        testAddPersonWithCertificateType(CommonCertificateTypeEnum.WORK_PERMIT);
        testAddPersonWithCertificateType(CommonCertificateTypeEnum.STUDENT_ID);
        testAddPersonWithCertificateType(null); // 测试空证件类型
    }

    private void testAddPersonWithCertificateType(CommonCertificateTypeEnum certificateType) {
        // 重置模拟对象
        reset(iSecureV2SDK, deviceModuleConfigService, thirdPartyRelationService);

        Integer areaId = 1;
        // mock FileResource
        FileResource mockFileResource = mock(FileResource.class);
        try {
            when(mockFileResource.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1,2,3,4}));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("张三")
                .setGender(1)
                .setPhone("12345678901")
                .setCertificateType(certificateType)
                .setCertificateNo("123456789012345678")
                .setFaceImageResource(List.of(mockFileResource));

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        PersonAddResponseData data = new PersonAddResponseData();
        data.setPersonId("person_001");
        PersonAddResponse personAddResponse = new PersonAddResponse();
        personAddResponse.setData(data);

        ArgumentCaptor<PersonAddRequest> requestCaptor = ArgumentCaptor.forClass(PersonAddRequest.class);
        when(iSecureV2SDK.personAdd(eq(mockConfig), requestCaptor.capture())).thenReturn(personAddResponse);

        // mock人脸图片base64
        mockStaticImageUtil();

        // 执行测试
        foundation.addLocalPersonToThirdParty(personAddBo);

        // 验证请求中使用了正确的证件类型
        PersonAddRequest capturedRequest = requestCaptor.getValue();

        if (certificateType == null) {
            assertEquals(CertificateTypeEnum.OTHER.getCode(), capturedRequest.getCertificateType());
        } else {
            switch (certificateType) {
                case ID_CARD:
                    assertEquals(CertificateTypeEnum.ID_CARD.getCode(), capturedRequest.getCertificateType());
                    break;
                case PASSPORT:
                    assertEquals(CertificateTypeEnum.PASSPORT.getCode(), capturedRequest.getCertificateType());
                    break;
                case HOUSEHOLD_REGISTER:
                    assertEquals(CertificateTypeEnum.HOUSEHOLD_REGISTER.getCode(), capturedRequest.getCertificateType());
                    break;
                case DRIVER_LICENSE:
                    assertEquals(CertificateTypeEnum.DRIVING_LICENSE.getCode(), capturedRequest.getCertificateType());
                    break;
                case WORK_PERMIT:
                    assertEquals(CertificateTypeEnum.WORK_CERTIFICATE.getCode(), capturedRequest.getCertificateType());
                    break;
                case STUDENT_ID:
                    assertEquals(CertificateTypeEnum.STUDENT_CERTIFICATE.getCode(), capturedRequest.getCertificateType());
                    break;
                default:
                    assertEquals(CertificateTypeEnum.OTHER.getCode(), capturedRequest.getCertificateType());
                    break;
            }
        }
    }

    @Test
    void addLocalPersonToThirdParty_WithNullOrganizationCode() {
        Integer areaId = 1;
        // mock FileResource
        FileResource mockFileResource = mock(FileResource.class);
        try {
            when(mockFileResource.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1,2,3,4}));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("张三")
                .setGender(1)
                .setPhone("12345678901")
                .setCertificateType(CommonCertificateTypeEnum.ID_CARD)
                .setCertificateNo("123456789012345678")
                .setOrganizationCode(null) // 测试organizationCode为null的情况
                .setFaceImageResource(List.of(mockFileResource));

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        PersonAddResponseData data = new PersonAddResponseData();
        data.setPersonId("person_001");
        PersonAddResponse personAddResponse = new PersonAddResponse();
        personAddResponse.setData(data);

        ArgumentCaptor<PersonAddRequest> requestCaptor = ArgumentCaptor.forClass(PersonAddRequest.class);
        when(iSecureV2SDK.personAdd(eq(mockConfig), requestCaptor.capture())).thenReturn(personAddResponse);

        // mock人脸图片base64
        mockStaticImageUtil();

        // 执行测试
        foundation.addLocalPersonToThirdParty(personAddBo);

        // 验证请求中使用了默认组织码
        PersonAddRequest capturedRequest = requestCaptor.getValue();
        assertEquals("root000000", capturedRequest.getOrgIndexCode());
    }

    @Test
    void buildFaceDataList_AllExceptionsHandled() {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(100)
                .setLocalModuleName("user")
                .setAreaId(areaId);

        // 创建多个异常FileResource
        FileResource errorResource1 = mock(FileResource.class);
        FileResource errorResource2 = mock(FileResource.class);
        FileResource validResource = mock(FileResource.class);

        try {
            when(errorResource1.getInputStream()).thenThrow(new RuntimeException("错误1"));
            when(errorResource2.getInputStream()).thenThrow(new RuntimeException("错误2"));
            when(validResource.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1,2,3,4}));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 构建PersonAddBo，包含异常和正常的FileResource
        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("张三")
                .setGender(1)
                .setPhone("12345678901")
                .setCertificateType(CommonCertificateTypeEnum.ID_CARD)
                .setCertificateNo("123456789012345678")
                .setFaceImageResource(List.of(errorResource1, errorResource2, validResource));

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        PersonAddResponseData data = new PersonAddResponseData();
        data.setPersonId("person_001");
        PersonAddResponse personAddResponse = new PersonAddResponse();
        personAddResponse.setData(data);

        when(iSecureV2SDK.personAdd(eq(mockConfig), any(PersonAddRequest.class))).thenReturn(personAddResponse);

        // mock人脸图片base64
        mockStaticImageUtil();

        // 执行测试
        String personId = foundation.addLocalPersonToThirdParty(personAddBo);
        assertEquals("person_001", personId);

        // 验证异常被正确处理，而唯一有效的图片被使用
        verify(iSecureV2SDK).personAdd(eq(mockConfig), any(PersonAddRequest.class));
        verify(errorResource1).getInputStream();
        verify(errorResource2).getInputStream();
        verify(validResource).getInputStream();
    }

    @Test
    void updatePersonFace_WithEmptyPhotoIndexCode() throws Exception {
        Integer areaId = 1;
        LocalPersonBo localPersonBo = new LocalPersonBo().setLocalPersonId(100).setLocalModuleName("user").setAreaId(areaId);
        FileResource mockFileResource = mock(FileResource.class);
        when(mockFileResource.getInputStream()).thenReturn(new java.io.ByteArrayInputStream(new byte[]{1,2,3,4}));
        PersonFaceUpdateBo updateBo = new PersonFaceUpdateBo().setLocalPersonBo(localPersonBo).setFaceImageResource(mockFileResource);

        ISecureV2FoundationImpl spyFoundation = spy(foundation);
        doReturn("person_001").when(spyFoundation).getThirdPartyPersonId(localPersonBo);

        ISecureSDKConfig mockConfig = new ISecureSDKConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(ISecureSDKConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // 创建PersonPhoto但设置空indexCode
        PersonDetail data = new PersonDetail();
        PersonPhoto photo = new PersonPhoto();
        photo.setPersonPhotoIndexCode(""); // 空indexCode
        data.setPersonPhoto(List.of(photo));

        PersonUniqueKeySearchResponse personUniqueKeySearchResponse = new PersonUniqueKeySearchResponse();
        personUniqueKeySearchResponse.setData(new PersonUniqueKeySearchResponseData());
        personUniqueKeySearchResponse.getData().setList(List.of(data));
        when(iSecureV2SDK.personUniqueKeySearch(eq(mockConfig), any(PersonUniqueKeySearchRequest.class))).thenReturn(personUniqueKeySearchResponse);

        // mock人脸图片base64
        mockStaticImageUtil();

        // 空indexCode会走添加人脸的流程
        PersonFaceAddResponse addResponse = new PersonFaceAddResponse();
        when(iSecureV2SDK.personFaceAdd(eq(mockConfig), any(PersonFaceAddRequest.class))).thenReturn(addResponse);

        // 执行测试
        spyFoundation.updatePersonFace(updateBo);

        // 验证走了添加而不是更新的流程
        verify(iSecureV2SDK, never()).personFaceUpdate(any(), any());
        verify(iSecureV2SDK).personFaceAdd(eq(mockConfig), any(PersonFaceAddRequest.class));
    }

    // mock静态方法ImageUtil.getBase64Image
    private void mockStaticImageUtil() {
        // 这里建议用PowerMockito等框架mock静态方法，实际项目中请根据测试框架补充
    }
}