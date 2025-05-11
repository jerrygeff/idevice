package info.zhihui.idevice.core.module.concrete.foundation.service.dahua.icc;

import com.dahuatech.icc.brm.enums.BiosignatureEnum;
import com.dahuatech.icc.brm.model.v202010.person.*;
import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.common.enums.ModuleEnum;
import info.zhihui.idevice.core.module.common.service.DeviceModuleConfigService;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.enums.CommonCertificateTypeEnum;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;
import info.zhihui.idevice.core.module.relation.service.ThirdPartyRelationService;
import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonAddRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonAddResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmImageUploadResponse.ImageUploadResult;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.brm.TreeNodeTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.DEFAULT_DEVICE_TREE_QUERY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE;

@ExtendWith(MockitoExtension.class)
class IccV5FoundationImplTest {

    @Mock
    private IccV5SDK iccV5SDK;
    @Mock
    private DeviceModuleConfigService deviceModuleConfigService;
    @Mock
    private ThirdPartyRelationService thirdPartyRelationService;

    private IccV5FoundationImpl iccFoundation;

    @BeforeEach
    void setUp() {
        iccFoundation = new IccV5FoundationImpl(thirdPartyRelationService, iccV5SDK, deviceModuleConfigService);
    }

    @Test
    void getDeviceTreeMap_Success() {
        // 准备测试数据
        IccSdkConfig mockConfig = new IccSdkConfig();
        String parentId = DEFAULT_DEVICE_TREE_QUERY_ID;
        String type = "VIDEO";

        // 创建树节点
        TreeItem rootItem = new TreeItem();
        rootItem.setId("org1");
        rootItem.setName("组织1");
        rootItem.setPId(parentId);
        rootItem.setNodeType("org");

        TreeItem childItem = new TreeItem();
        childItem.setId("org2");
        childItem.setName("组织2");
        childItem.setPId("org1");
        childItem.setNodeType("org");

        TreeItem deviceItem = new TreeItem();
        deviceItem.setId("device1");
        deviceItem.setName("设备1");
        deviceItem.setPId("org2");
        deviceItem.setNodeType("dev");

        TreeItem channelItem = new TreeItem();
        channelItem.setId("channel1");
        channelItem.setName("通道1");
        channelItem.setPId("device1");
        channelItem.setNodeType(TreeNodeTypeEnum.CH.getValue());

        // 模拟树查询返回
        // 第一层查询返回
        BrmDeviceTreeListResponse rootResponse = new BrmDeviceTreeListResponse();
        BrmDeviceTreeListResponse.Data rootData = new BrmDeviceTreeListResponse.Data();
        rootData.setValue(List.of(rootItem));
        rootResponse.setData(rootData);

        // 第二层查询返回
        BrmDeviceTreeListResponse org1Response = new BrmDeviceTreeListResponse();
        BrmDeviceTreeListResponse.Data org1Data = new BrmDeviceTreeListResponse.Data();
        org1Data.setValue(List.of(childItem));
        org1Response.setData(org1Data);

        // 第三层查询返回
        BrmDeviceTreeListResponse org2Response = new BrmDeviceTreeListResponse();
        BrmDeviceTreeListResponse.Data org2Data = new BrmDeviceTreeListResponse.Data();
        org2Data.setValue(List.of(deviceItem));
        org2Response.setData(org2Data);

        // 第四层查询返回
        BrmDeviceTreeListResponse deviceResponse = new BrmDeviceTreeListResponse();
        BrmDeviceTreeListResponse.Data deviceData = new BrmDeviceTreeListResponse.Data();
        deviceData.setValue(List.of(channelItem));
        deviceResponse.setData(deviceData);

        // 设置Mock行为
        when(iccV5SDK.brmDeviceTreeList(eq(mockConfig), argThat(req ->
                parentId.equals(req.getId()) && type.equals(req.getType()))))
                .thenReturn(rootResponse);

        when(iccV5SDK.brmDeviceTreeList(eq(mockConfig), argThat(req ->
                "org1".equals(req.getId()) && type.equals(req.getType()))))
                .thenReturn(org1Response);

        when(iccV5SDK.brmDeviceTreeList(eq(mockConfig), argThat(req ->
                "org2".equals(req.getId()) && type.equals(req.getType()))))
                .thenReturn(org2Response);

        when(iccV5SDK.brmDeviceTreeList(eq(mockConfig), argThat(req ->
                "device1".equals(req.getId()) && type.equals(req.getType()))))
                .thenReturn(deviceResponse);

        // 执行测试
        Map<String, TreeItem> result = iccFoundation.getDeviceTreeMap(mockConfig, parentId, type);

        // 验证结果
        assertEquals(4, result.size());
        assertTrue(result.containsKey("org1"));
        assertTrue(result.containsKey("org2"));
        assertTrue(result.containsKey("device1"));
        assertTrue(result.containsKey("channel1"));

        // 验证映射内容
        assertEquals("组织1", result.get("org1").getName());
        assertEquals("组织2", result.get("org2").getName());
        assertEquals("设备1", result.get("device1").getName());
        assertEquals("通道1", result.get("channel1").getName());

        // 验证交互
        verify(iccV5SDK, times(4)).brmDeviceTreeList(eq(mockConfig), any(BrmDeviceTreeListRequest.class));
    }

    @Test
    void getDeviceTreeMap_EmptyResponse() {
        // 准备测试数据
        IccSdkConfig mockConfig = new IccSdkConfig();
        String parentId = DEFAULT_DEVICE_TREE_QUERY_ID;
        String type = "VIDEO";

        // 模拟空响应
        BrmDeviceTreeListResponse emptyResponse = new BrmDeviceTreeListResponse();
        BrmDeviceTreeListResponse.Data emptyData = new BrmDeviceTreeListResponse.Data();
        emptyData.setValue(Collections.emptyList());
        emptyResponse.setData(emptyData);

        when(iccV5SDK.brmDeviceTreeList(eq(mockConfig), any(BrmDeviceTreeListRequest.class)))
                .thenReturn(emptyResponse);

        // 执行测试
        Map<String, TreeItem> result = iccFoundation.getDeviceTreeMap(mockConfig, parentId, type);

        // 验证结果
        assertTrue(result.isEmpty());

        // 验证交互
        verify(iccV5SDK).brmDeviceTreeList(eq(mockConfig), argThat(req ->
                parentId.equals(req.getId()) && type.equals(req.getType())));
    }

    @Test
    void updatePersonFace_Success() {
        // 准备测试数据
        Integer areaId = 1;
        Integer localPersonId = 100;
        String localModuleName = "testModule";
        String thirdPartyPersonId = "200";
        String testImageBase64 = "test_image_base64";
        String uploadedFilePath = "http://test.com/image.jpg";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(localPersonId)
                .setLocalModuleName(localModuleName)
                .setAreaId(areaId);

        PersonFaceUpdateBo updateBo = new PersonFaceUpdateBo()
                .setLocalPersonBo(localPersonBo)
                .setFaceImageResource(new FileResource()
                        .setInputStream(new ByteArrayInputStream(testImageBase64.getBytes()))
                        .setContentType(IMAGE_JPEG_VALUE)
                        .setFileSize(15534L)
                );

        // Mock ThirdPartyRelationService
        ThirdPartyRelationBo relationBo = new ThirdPartyRelationBo()
                .setThirdPartyBusinessKey(thirdPartyPersonId);
        when(thirdPartyRelationService.getThirdPartyRelation(any(ThirdPartyRelationQuery.class)))
                .thenReturn(relationBo);

        // Mock DeviceModuleConfigService
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock IccV5SDK - 图片上传
        BrmImageUploadResponse uploadResponse = new BrmImageUploadResponse();
        ImageUploadResult uploadResult = new ImageUploadResult();
        uploadResult.setResult("0");
        uploadResult.setFileUrl(uploadedFilePath);
        uploadResponse.setData(uploadResult);
        ArgumentCaptor<BrmImageUploadRequest> uploadRequestCaptor = ArgumentCaptor.forClass(BrmImageUploadRequest.class);
        when(iccV5SDK.brmImageUpload(eq(mockConfig), uploadRequestCaptor.capture()))
                .thenReturn(uploadResponse);

        // 执行测试
        iccFoundation.updatePersonFace(updateBo);

        // 验证调用
        verify(thirdPartyRelationService).getThirdPartyRelation(argThat(query ->
                query.getAreaId().equals(areaId) &&
                        query.getThirdPartyModuleName().equals(ModuleEnum.PERSON.getModuleName()) &&
                        query.getLocalBusinessKey().equals(localPersonId.toString()) &&
                        query.getLocalModuleName().equals(localModuleName)
        ));

        verify(iccV5SDK).brmImageUpload(mockConfig, uploadRequestCaptor.getValue());

        ArgumentCaptor<BrmPersonBatchUpdateImgRequest> updateRequestCaptor = ArgumentCaptor.forClass(BrmPersonBatchUpdateImgRequest.class);
        verify(iccV5SDK).brmPersonUpdateFace(eq(mockConfig), updateRequestCaptor.capture());

        // 验证更新请求参数
        BrmPersonBatchUpdateImgRequest capturedRequest = updateRequestCaptor.getValue();
        PersonBioSignatures bioSignatures = capturedRequest.getPersonBiosignatures().get(0);
        assertEquals(uploadedFilePath, bioSignatures.getPath());
        assertEquals(BiosignatureEnum.FACE_IMG.getId(), bioSignatures.getType());
        assertEquals(1, bioSignatures.getIndex());
        assertEquals(Long.parseLong(thirdPartyPersonId), bioSignatures.getPersonId());
    }

    @Test
    void updatePersonFace_ImageFormatInvalid() {
        // 准备测试数据
        Integer areaId = 1;
        Integer localPersonId = 100;
        String localModuleName = "testModule";
        String thirdPartyPersonId = "200";
        String testImageBase64 = "test_image_base64";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(localPersonId)
                .setLocalModuleName(localModuleName)
                .setAreaId(areaId);

        PersonFaceUpdateBo updateBo = new PersonFaceUpdateBo()
                .setLocalPersonBo(localPersonBo)
                .setFaceImageResource(new FileResource()
                        .setFileSize(15534L)
                        .setContentType(IMAGE_JPEG_VALUE)
                        .setInputStream(new ByteArrayInputStream(testImageBase64.getBytes()))
                );

        // Mock ThirdPartyRelationService
        ThirdPartyRelationBo relationBo = new ThirdPartyRelationBo()
                .setThirdPartyBusinessKey(thirdPartyPersonId);
        when(thirdPartyRelationService.getThirdPartyRelation(any(ThirdPartyRelationQuery.class)))
                .thenReturn(relationBo);

        // Mock DeviceModuleConfigService
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock IccV5SDK - 图片上传失败
        BrmImageUploadResponse uploadResponse = new BrmImageUploadResponse();
        ImageUploadResult uploadResult = new ImageUploadResult();
        uploadResult.setResult("1"); // 非0表示失败
        uploadResponse.setData(uploadResult);
        ArgumentCaptor<BrmImageUploadRequest> uploadRequestCaptor = ArgumentCaptor.forClass(BrmImageUploadRequest.class);
        when(iccV5SDK.brmImageUpload(eq(mockConfig), uploadRequestCaptor.capture()))
                .thenReturn(uploadResponse);

        // 执行测试并验证异常
        assertThrows(BusinessRuntimeException.class, () -> iccFoundation.updatePersonFace(updateBo));

        // 验证调用
        verify(iccV5SDK).brmImageUpload(mockConfig, uploadRequestCaptor.getValue());
        verify(iccV5SDK, never()).brmPersonUpdateFace(any(), any());
    }

    @Test
    void addLocalPersonToThirdParty_Success() {
        // 准备测试数据
        Integer areaId = 1;
        Integer localPersonId = 100;
        String localModuleName = "testModule";
        Long generatedId = 500L;
        String testImageBase64 = "test_image_base64";
        String uploadedFilePath = "http://test.com/image.jpg";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(localPersonId)
                .setLocalModuleName(localModuleName)
                .setAreaId(areaId);

        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("Test Person")
                .setPhone("13800138000")
                .setCertificateType(CommonCertificateTypeEnum.ID_CARD)
                .setCertificateNo("123456789012345678")
                .setGender(1)
                .setOrganizationCode("ORG001")
                .setFaceImageResource(List.of(new FileResource()
                        .setInputStream(new ByteArrayInputStream(testImageBase64.getBytes()))
                        .setContentType(IMAGE_JPEG_VALUE)
                        .setFileSize(15534L)
                ));

        // Mock DeviceModuleConfigService
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock IccV5SDK - 生成ID
        BrmPersonGenIdResponse genIdResponse = new BrmPersonGenIdResponse();
        BrmPersonGenIdResponse.GenIdData personGenIdResult = new BrmPersonGenIdResponse.GenIdData();
        personGenIdResult.setId(generatedId);
        genIdResponse.setData(personGenIdResult);
        when(iccV5SDK.brmPersonGenId(eq(mockConfig), any(BrmPersonGenIdRequest.class))).thenReturn(genIdResponse);

        // Mock IccV5SDK - 图片上传
        BrmImageUploadResponse uploadResponse = new BrmImageUploadResponse();
        ImageUploadResult uploadResult = new ImageUploadResult();
        uploadResult.setResult("0");
        uploadResult.setFileUrl(uploadedFilePath);
        uploadResponse.setData(uploadResult);
        when(iccV5SDK.brmImageUpload(eq(mockConfig), any(BrmImageUploadRequest.class))).thenReturn(uploadResponse);

        // Mock IccV5SDK - 添加人员
        BrmPersonAddResponse personAddResponse = new BrmPersonAddResponse();
        BrmPersonAddResponse.PersonIdData personAddResult = new BrmPersonAddResponse.PersonIdData();
        personAddResult.setId(generatedId);
        personAddResponse.setData(personAddResult);
        ArgumentCaptor<BrmPersonAddRequest> personAddRequestCaptor = ArgumentCaptor.forClass(BrmPersonAddRequest.class);
        when(iccV5SDK.brmPersonAdd(eq(mockConfig), personAddRequestCaptor.capture())).thenReturn(personAddResponse);

        // 执行测试
        String result = iccFoundation.addLocalPersonToThirdParty(personAddBo);

        // 验证结果
        assertEquals(generatedId.toString(), result);

        // 验证调用
        verify(iccV5SDK).brmPersonGenId(eq(mockConfig), any(BrmPersonGenIdRequest.class));
        verify(iccV5SDK).brmImageUpload(eq(mockConfig), any(BrmImageUploadRequest.class));
        verify(iccV5SDK).brmPersonAdd(eq(mockConfig), any(BrmPersonAddRequest.class));

        // 验证ThirdPartyRelation添加
        ArgumentCaptor<ThirdPartyRelationBo> relationCaptor = ArgumentCaptor.forClass(ThirdPartyRelationBo.class);
        verify(thirdPartyRelationService).add(relationCaptor.capture());
        ThirdPartyRelationBo capturedRelation = relationCaptor.getValue();
        assertEquals(areaId, capturedRelation.getAreaId());
        assertEquals(localModuleName, capturedRelation.getLocalModuleName());
        assertEquals(localPersonId.toString(), capturedRelation.getLocalBusinessKey());
        assertEquals(generatedId.toString(), capturedRelation.getThirdPartyBusinessKey());
    }

    @Test
    void addLocalPersonToThirdParty_WithEmptyFaceParam() {
        // 准备测试数据
        Integer areaId = 1;
        Integer localPersonId = 100;
        String localModuleName = "testModule";
        Long generatedId = 500L;

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(localPersonId)
                .setLocalModuleName(localModuleName)
                .setAreaId(areaId);

        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("Test Person")
                .setPhone("13800138000")
                .setCertificateType(CommonCertificateTypeEnum.ID_CARD)
                .setCertificateNo("123456789012345678")
                .setGender(1)
                .setOrganizationCode("ORG001")
                .setFaceImageResource(Collections.emptyList()); // 空的人脸参数

        // Mock DeviceModuleConfigService
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock IccV5SDK - 生成ID
        BrmPersonGenIdResponse genIdResponse = new BrmPersonGenIdResponse();
        BrmPersonGenIdResponse.GenIdData genIdResult = new BrmPersonGenIdResponse.GenIdData();
        genIdResult.setId(generatedId);
        genIdResponse.setData(genIdResult);
        when(iccV5SDK.brmPersonGenId(eq(mockConfig), any(BrmPersonGenIdRequest.class))).thenReturn(genIdResponse);

        // Mock IccV5SDK - 添加人员
        BrmPersonAddResponse personAddResponse = new BrmPersonAddResponse();
        BrmPersonAddResponse.PersonIdData personAddResult = new BrmPersonAddResponse.PersonIdData();
        personAddResult.setId(generatedId);
        personAddResponse.setData(personAddResult);
        when(iccV5SDK.brmPersonAdd(eq(mockConfig), any(BrmPersonAddRequest.class))).thenReturn(personAddResponse);

        // 执行测试
        String result = iccFoundation.addLocalPersonToThirdParty(personAddBo);

        // 验证结果
        assertEquals(generatedId.toString(), result);

        // 验证调用
        verify(iccV5SDK).brmPersonGenId(eq(mockConfig), any(BrmPersonGenIdRequest.class));
        verify(iccV5SDK, never()).brmImageUpload(any(), any()); // 不应该调用图片上传
        verify(iccV5SDK).brmPersonAdd(eq(mockConfig), any(BrmPersonAddRequest.class));
    }

    @Test
    void addLocalPersonToThirdParty_WithImageUploadError() {
        // 准备测试数据
        Integer areaId = 1;
        Integer localPersonId = 100;
        String localModuleName = "testModule";
        Long generatedId = 500L;
        String testImageBase64 = "test_image_base64";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(localPersonId)
                .setLocalModuleName(localModuleName)
                .setAreaId(areaId);

        List<FileResource> faceParams = new ArrayList<>();
        FileResource validResource = new FileResource()
                .setInputStream(new ByteArrayInputStream(testImageBase64.getBytes()))
                .setContentType(IMAGE_JPEG_VALUE)
                .setFileSize(15534L);

        FileResource errorResource = mock(FileResource.class);
        when(errorResource.getInputStream()).thenThrow(new BusinessRuntimeException("模拟图片处理异常"));

        faceParams.add(validResource);
        faceParams.add(errorResource);

        PersonAddBo personAddBo = new PersonAddBo()
                .setLocalPersonBo(localPersonBo)
                .setName("Test Person")
                .setPhone("13800138000")
                .setCertificateType(CommonCertificateTypeEnum.ID_CARD)
                .setCertificateNo("123456789012345678")
                .setGender(1)
                .setOrganizationCode("ORG001")
                .setFaceImageResource(faceParams);

        // Mock DeviceModuleConfigService
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // Mock IccV5SDK - 生成ID
        BrmPersonGenIdResponse genIdResponse = new BrmPersonGenIdResponse();
        BrmPersonGenIdResponse.GenIdData genIdResult = new BrmPersonGenIdResponse.GenIdData();
        genIdResult.setId(generatedId);
        genIdResponse.setData(genIdResult);
        when(iccV5SDK.brmPersonGenId(eq(mockConfig), any(BrmPersonGenIdRequest.class))).thenReturn(genIdResponse);

        // Mock IccV5SDK - 图片上传(只为有效图片模拟成功)
        BrmImageUploadResponse uploadResponse = new BrmImageUploadResponse();
        ImageUploadResult uploadResult = new ImageUploadResult();
        uploadResult.setResult("0");
        uploadResult.setFileUrl("http://test.com/image.jpg");
        uploadResponse.setData(uploadResult);
        when(iccV5SDK.brmImageUpload(eq(mockConfig), any(BrmImageUploadRequest.class))).thenReturn(uploadResponse);

        // Mock IccV5SDK - 添加人员
        BrmPersonAddResponse personAddResponse = new BrmPersonAddResponse();
        BrmPersonAddResponse.PersonIdData personAddResult = new BrmPersonAddResponse.PersonIdData();
        personAddResult.setId(generatedId);
        personAddResponse.setData(personAddResult);
        when(iccV5SDK.brmPersonAdd(eq(mockConfig), any(BrmPersonAddRequest.class))).thenReturn(personAddResponse);

        // 执行测试
        String result = iccFoundation.addLocalPersonToThirdParty(personAddBo);

        // 验证结果
        assertEquals(generatedId.toString(), result);

        // 验证调用
        verify(iccV5SDK).brmPersonGenId(eq(mockConfig), any(BrmPersonGenIdRequest.class));
        verify(iccV5SDK, times(1)).brmImageUpload(eq(mockConfig), any(BrmImageUploadRequest.class)); // 只有一张图片上传成功
        verify(iccV5SDK).brmPersonAdd(eq(mockConfig), any(BrmPersonAddRequest.class));
    }

    @Test
    void deleteThirdPartyPerson_Success() {
        // 准备测试数据
        Integer areaId = 1;
        Integer localPersonId = 100;
        String localModuleName = "testModule";
        String thirdPartyPersonId = "200";

        LocalPersonBo localPersonBo = new LocalPersonBo()
                .setLocalPersonId(localPersonId)
                .setLocalModuleName(localModuleName)
                .setAreaId(areaId);

        // Mock ThirdPartyRelationService
        ThirdPartyRelationBo relationBo = new ThirdPartyRelationBo()
                .setThirdPartyBusinessKey(thirdPartyPersonId);
        when(thirdPartyRelationService.getThirdPartyRelation(any(ThirdPartyRelationQuery.class)))
                .thenReturn(relationBo);

        // Mock DeviceModuleConfigService
        IccSdkConfig mockConfig = new IccSdkConfig();
        when(deviceModuleConfigService.getDeviceConfigValue(eq(AccessControl.class), eq(IccSdkConfig.class), eq(areaId)))
                .thenReturn(mockConfig);

        // 执行测试
        iccFoundation.deleteThirdPartyPerson(localPersonBo);

        // 验证调用
        ArgumentCaptor<BrmPersonDelRequest> delRequestCaptor = ArgumentCaptor.forClass(BrmPersonDelRequest.class);
        verify(iccV5SDK).brmPersonDel(eq(mockConfig), delRequestCaptor.capture());

        // 验证删除请求参数
        BrmPersonDelRequest capturedRequest = delRequestCaptor.getValue();
        assertEquals(1, capturedRequest.getIds().size());
        assertEquals(Long.parseLong(thirdPartyPersonId), capturedRequest.getIds().get(0));

        // 验证关系删除
        ArgumentCaptor<ThirdPartyRelationQuery> queryCaptor = ArgumentCaptor.forClass(ThirdPartyRelationQuery.class);
        verify(thirdPartyRelationService).delete(queryCaptor.capture());
        ThirdPartyRelationQuery capturedQuery = queryCaptor.getValue();
        assertEquals(areaId, capturedQuery.getAreaId());
        assertEquals(localModuleName, capturedQuery.getLocalModuleName());
        assertEquals(localPersonId.toString(), capturedQuery.getLocalBusinessKey());
    }

    @Test
    void setTreeItemPathData_Success() {
        // 准备测试数据 - 创建设备列表
        TestDevice device1 = new TestDevice();
        device1.setOwnerCode("org1");

        TestDevice device2 = new TestDevice();
        device2.setOwnerCode("org2");

        TestDevice device3 = new TestDevice();
        device3.setOwnerCode("org3");

        List<TestDevice> deviceList = List.of(device1, device2, device3);

        Map<String, TreeItem> treeItemMap = new HashMap<>();

        // 创建三层树结构: org1 -> org2 -> org3
        TreeItem org1Item = new TreeItem();
        org1Item.setId("org1");
        org1Item.setName("组织1");
        org1Item.setPId(DEFAULT_DEVICE_TREE_QUERY_ID);
        treeItemMap.put("org1", org1Item);

        TreeItem org2Item = new TreeItem();
        org2Item.setId("org2");
        org2Item.setName("组织2");
        org2Item.setPId("org1");
        treeItemMap.put("org2", org2Item);

        TreeItem org3Item = new TreeItem();
        org3Item.setId("org3");
        org3Item.setName("组织3");
        org3Item.setPId("org2");
        treeItemMap.put("org3", org3Item);

        // 定义获取和设置函数
        Function<TestDevice, String> getOwnerCode = TestDevice::getOwnerCode;
        BiConsumer<TestDevice, List<String>> setRegionPath = TestDevice::setRegionPath;
        BiConsumer<TestDevice, List<String>> setRegionPathName = TestDevice::setRegionPathName;

        // 执行测试
        List<TestDevice> result = iccFoundation.setTreeItemPathData(
                deviceList,
                treeItemMap,
                getOwnerCode,
                setRegionPath,
                setRegionPathName
        );

        // 验证结果
        assertEquals(3, result.size());

        // 验证device1(org1)的路径设置
        TestDevice resultDevice1 = result.stream()
                .filter(d -> "org1".equals(d.getOwnerCode()))
                .findFirst()
                .orElseThrow();
        assertEquals(1, resultDevice1.getRegionPath().size());
        assertEquals("org1", resultDevice1.getRegionPath().get(0));
        assertEquals(1, resultDevice1.getRegionPathName().size());
        assertEquals("组织1", resultDevice1.getRegionPathName().get(0));

        // 验证device2(org2)的路径设置
        TestDevice resultDevice2 = result.stream()
                .filter(d -> "org2".equals(d.getOwnerCode()))
                .findFirst()
                .orElseThrow();
        assertEquals(2, resultDevice2.getRegionPath().size());
        assertEquals("org1", resultDevice2.getRegionPath().get(0));
        assertEquals("org2", resultDevice2.getRegionPath().get(1));
        assertEquals(2, resultDevice2.getRegionPathName().size());
        assertEquals("组织1", resultDevice2.getRegionPathName().get(0));
        assertEquals("组织2", resultDevice2.getRegionPathName().get(1));

        // 验证device3(org3)的路径设置 - 最深层级
        TestDevice resultDevice3 = result.stream()
                .filter(d -> "org3".equals(d.getOwnerCode()))
                .findFirst()
                .orElseThrow();
        assertEquals(3, resultDevice3.getRegionPath().size());
        assertEquals("org1", resultDevice3.getRegionPath().get(0));
        assertEquals("org2", resultDevice3.getRegionPath().get(1));
        assertEquals("org3", resultDevice3.getRegionPath().get(2));
        assertEquals(3, resultDevice3.getRegionPathName().size());
        assertEquals("组织1", resultDevice3.getRegionPathName().get(0));
        assertEquals("组织2", resultDevice3.getRegionPathName().get(1));
        assertEquals("组织3", resultDevice3.getRegionPathName().get(2));
    }

    @Test
    void setTreeItemPathData_EmptyDeviceList() {
        // 准备测试数据
        List<TestDevice> emptyDeviceList = Collections.emptyList();
        Map<String, TreeItem> treeItemMap = new HashMap<>();

        // 添加一些树节点
        TreeItem item = new TreeItem();
        item.setId("org1");
        item.setName("组织1");
        treeItemMap.put("org1", item);

        // 定义获取和设置函数
        Function<TestDevice, String> getOwnerCode = TestDevice::getOwnerCode;
        BiConsumer<TestDevice, List<String>> setRegionPath = TestDevice::setRegionPath;
        BiConsumer<TestDevice, List<String>> setRegionPathName = TestDevice::setRegionPathName;

        // 执行测试
        List<TestDevice> result = iccFoundation.setTreeItemPathData(
                emptyDeviceList,
                treeItemMap,
                getOwnerCode,
                setRegionPath,
                setRegionPathName
        );

        // 验证结果
        assertTrue(result.isEmpty());
    }

    @Test
    void setTreeItemPathData_EmptyTreeMap() {
        // 准备测试数据
        TestDevice device = new TestDevice();
        device.setOwnerCode("org1");
        List<TestDevice> deviceList = List.of(device);

        Map<String, TreeItem> emptyTreeMap = Collections.emptyMap();

        // 定义获取和设置函数
        Function<TestDevice, String> getOwnerCode = TestDevice::getOwnerCode;
        BiConsumer<TestDevice, List<String>> setRegionPath = TestDevice::setRegionPath;
        BiConsumer<TestDevice, List<String>> setRegionPathName = TestDevice::setRegionPathName;

        // 执行测试
        List<TestDevice> result = iccFoundation.setTreeItemPathData(
                deviceList,
                emptyTreeMap,
                getOwnerCode,
                setRegionPath,
                setRegionPathName
        );

        // 验证结果
        assertEquals(1, result.size());
        assertNull(result.get(0).getRegionPath());
        assertNull(result.get(0).getRegionPathName());
    }

    @Test
    void setTreeItemPathData_OwnerCodeNotInTreeMap() {
        // 准备测试数据
        TestDevice device = new TestDevice();
        device.setOwnerCode("non_existent_org");
        List<TestDevice> deviceList = List.of(device);

        Map<String, TreeItem> treeItemMap = new HashMap<>();
        TreeItem item = new TreeItem();
        item.setId("org1");
        item.setName("组织1");
        treeItemMap.put("org1", item);

        // 定义获取和设置函数
        Function<TestDevice, String> getOwnerCode = TestDevice::getOwnerCode;
        BiConsumer<TestDevice, List<String>> setRegionPath = TestDevice::setRegionPath;
        BiConsumer<TestDevice, List<String>> setRegionPathName = TestDevice::setRegionPathName;

        // 执行测试
        List<TestDevice> result = iccFoundation.setTreeItemPathData(
                deviceList,
                treeItemMap,
                getOwnerCode,
                setRegionPath,
                setRegionPathName
        );

        // 验证结果
        assertEquals(1, result.size());
        assertNull(result.get(0).getRegionPath());
        assertNull(result.get(0).getRegionPathName());
    }

    // 测试用设备类
    @Setter
    @Getter
    static class TestDevice {
        private String ownerCode;
        private List<String> regionPath;
        private List<String> regionPathName;

    }
}