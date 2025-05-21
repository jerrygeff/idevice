package info.zhihui.idevice.core.sdk.dahua.icc.sdk;

import info.zhihui.idevice.core.sdk.dahua.icc.IccV5SDK;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.park.v5.ParkCarReservationAddRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.park.v5.ParkCarReservationAddResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccParkConstant.DEFAULT_RESERVATION_TYPE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jerryge
 */
@Slf4j
@TestMethodOrder(MethodOrderer.DisplayName.class)
@ExtendWith(MockitoExtension.class)
public class IccV5SDKTest {
    private final IccV5SDK iccV5SDK = new IccV5SDK();

    private final IccSdkConfig iccSdkConfig = new IccSdkConfig()
            .setIccSdkHost("124.160.33.135")
            .setIccSdkClientId("CompanyName")
            .setIccSdkClientSecret("42bec152-8f04-476a-9aec-e7d616ff3cb3")
            .setIccSdkUserName("TEST")
            .setIccSdkPassword("OGR28u6_cc")
            .setIsEnableHttpTest(false)
            .setPort("4077");

    private static final String CARD_NUM = "88822222";

    @Test
    public void testGetCapabilities() {
        // Case 1: 测试空字符串
        List<String> emptyResult = iccV5SDK.getCapabilities("");
        assertTrue(emptyResult.isEmpty(), "空字符串应返回空列表");

        // Case 2: 测试null输入
        List<String> nullResult = iccV5SDK.getCapabilities(null);
        assertTrue(nullResult.isEmpty(), "null输入应返回空列表");

        // Case 3: 测试没有任何能力（全0）
        String noCapabilities = "00000000000000000000000000000000";
        List<String> noCapabilitiesResult = iccV5SDK.getCapabilities(noCapabilities);
        assertTrue(noCapabilitiesResult.isEmpty(), "没有能力的输入应返回空列表");

        // Case 4: 测试单个能力 - 智能报警 (位置0, 二进制字符串的第1位)
        String singleCapability1 = "10000000000000000000000000000000";
        List<String> singleCapability1Result = iccV5SDK.getCapabilities(singleCapability1);
        assertEquals(1, singleCapability1Result.size(), "应返回一个能力");
        assertEquals("智能报警", singleCapability1Result.get(0), "应返回'智能报警'能力");

        // Case 5: 测试单个能力 - 云台控制 (位置16, 二进制字符串的第17位)
        String singleCapability2 = "00000000000000001000000000000000";
        List<String> singleCapability2Result = iccV5SDK.getCapabilities(singleCapability2);
        assertEquals(1, singleCapability2Result.size(), "应返回一个能力");
        assertEquals("云台控制", singleCapability2Result.get(0), "应返回'云台控制'能力");

        // Case 6: 测试单个能力 - AR广角360° (位置30, 二进制字符串的第31位)
        String singleCapability3 = "00000000000000000000000000000010";
        List<String> singleCapability3Result = iccV5SDK.getCapabilities(singleCapability3);
        assertEquals(1, singleCapability3Result.size(), "应返回一个能力");
        assertEquals("AR广角360°", singleCapability3Result.get(0), "应返回'AR广角360°'能力");

        // Case 7: 测试多个能力 - 人脸检测(位置7, 二进制字符串的第8位)、人员识别(位置8, 二进制字符串的第9位)
        // 注意：从右往左数第8位和第9位
        String multipleCapabilities1 = "00000001100000000000000000000000";
        List<String> multipleCapabilities1Result = iccV5SDK.getCapabilities(multipleCapabilities1);
        assertEquals(2, multipleCapabilities1Result.size(), "应返回两个能力");
        assertTrue(multipleCapabilities1Result.contains("人脸检测"), "结果应包含'人脸检测'");
        assertTrue(multipleCapabilities1Result.contains("人员识别"), "结果应包含'人员识别'");

        // Case 8: 测试多个能力 - 智能报警(位置0, 二进制字符串的第1位)、鱼眼(位置1, 二进制字符串的第2位)、电动聚焦(位置2, 二进制字符串的第3位)
        String multipleCapabilities2 = "11100000000000000000000000000000";
        List<String> multipleCapabilities2Result = iccV5SDK.getCapabilities(multipleCapabilities2);
        assertEquals(3, multipleCapabilities2Result.size(), "应返回三个能力");
        assertTrue(multipleCapabilities2Result.contains("智能报警"), "结果应包含'智能报警'");
        assertTrue(multipleCapabilities2Result.contains("鱼眼"), "结果应包含'鱼眼'");
        assertTrue(multipleCapabilities2Result.contains("电动聚焦"), "结果应包含'电动聚焦'");

        // Case 9: 测试包含占位符"-"的能力位 (位置17, 二进制字符串的第18位)
        String placeholderCapability = "00000000000000000100000000000000";
        List<String> placeholderCapabilityResult = iccV5SDK.getCapabilities(placeholderCapability);
        assertEquals(0, placeholderCapabilityResult.size(), "包含占位符的能力位应被过滤");
        assertFalse(placeholderCapabilityResult.contains("-"), "结果不应包含'-'占位符");

        // Case 10: 测试所有能力（全1）
        String allCapabilities = "11111111111111111111111111111111";
        List<String> allCapabilitiesResult = iccV5SDK.getCapabilities(allCapabilities);
        // 3位能力集，扣除2个"-"占位符
        assertEquals(30, allCapabilitiesResult.size(), "应返回31个能力(32减去2个占位符)");
        assertTrue(allCapabilitiesResult.contains("智能报警"), "结果应包含'智能报警'");
        assertTrue(allCapabilitiesResult.contains("鱼眼"), "结果应包含'鱼眼'");
        assertTrue(allCapabilitiesResult.contains("云台控制"), "结果应包含'云台控制'");
        assertTrue(allCapabilitiesResult.contains("AR广角360°"), "结果应包含'AR广角360°'");
        assertFalse(allCapabilitiesResult.contains("-"), "结果不应包含'-'占位符");

        // Case 11: 测试超长二进制字符串，应只处理前32位
        String tooLongCapability = "1111111111111111111111111111111111111";
        List<String> tooLongCapabilityResult = iccV5SDK.getCapabilities(tooLongCapability);
        assertEquals(30, tooLongCapabilityResult.size(), "应只处理前32位，返回30个能力(32减去2个占位符)");
    }

    // ------ 调用icc测试环境 --------

//    @Test
//    public void testAccessAuthPerson() {
//        PrivilegeDetail privilegeDetail = new PrivilegeDetail()
//                .setPrivilegeType(1)
//                .setResourceCode("1002183$1$0$1");
//
//        AuthPersonBatchAddRequest authPersonBatchAddRequest = AuthPersonBatchAddRequest.builder()
//                .personCodes(List.of("18800000002"))
//                .timeQuantumId(1L)
//                .privilegeDetails(List.of(
//                        privilegeDetail
//                )).build();
//        iccV5SDK.accessAuthPeople(iccSdkConfig, authPersonBatchAddRequest);
//    }
//
//    @Test
//    public void testRemoveAuthPerson() {
//        AuthPersonBatchDeleteRequest request = AuthPersonBatchDeleteRequest.builder()
//                .personCodes(List.of("18800000002"))
//                .build();
//        iccV5SDK.accessDeletePeopleAllPrivilege(iccSdkConfig, request);
//    }
//
//    @Test
//    public void testRemoveAuthPersonSinglePrivilege() {
//        AuthPersonDeleteSingleRequest request = AuthPersonDeleteSingleRequest.builder().personCode("18800000002")
//                .deleteDetails(List.of(
//                        new DeleteDetail()
//                                .setPrivilegeType(1)
//                                .setResourceCode("1002183$1$0$1")
//                )).build();
//        iccV5SDK.accessDeletePersonSinglePrivilege(iccSdkConfig, request);
//    }
//
//    @Test
//    public void testBrmPersonQuery() {
//        BrmPersonQueryRequest request = new BrmPersonQueryRequest(2L);
//        iccV5SDK.brmPersonQuery(iccSdkConfig, request);
//    }
//
//    @Test
//    public void testBrmDevicePage() {
//        BrmDevicePageRequest request = new BrmDevicePageRequest();
//        request.setPageNum(4);
//        request.setShowChildNodeData(1);
//        iccV5SDK.brmDevicePage(iccSdkConfig, request);
//    }
//
//    @Test
//    public void testBrmCardGenId() {
//        BrmCardGenIdRequest request = new BrmCardGenIdRequest();
//        iccV5SDK.brmCardGenId(iccSdkConfig, request);
//    }
//
//    @Test
//    @DisplayName("cardAdd")
//    public void testBrmCardAdd() throws ClientException {
//        BrmCardAddRequest brmCardAddRequest = new BrmCardAddRequest
//                .Builder()
//                .cardNumber(CARD_NUM)
//                .category(CardCategory.IC.getValue())
//                .cardType(CardType.NORMAL_CARD.getCode())
//                .startDate("2025-02-01 00:00:00")
//                .endDate("2028-02-01 00:00:00")
//                .departmentId(1L)
//                .build();
//
//        try {
//            BrmCardAddResponse res = iccV5SDK.brmCardAdd(iccSdkConfig, brmCardAddRequest);
//            log.info("res: {}", res);
//        } catch (IccRuntimeException e) {
//            log.info("捕获异常：{}", e.getCode());
//        }
//
//    }
//
//    @Test
//    @DisplayName("cardAddAfter1-active")
//    public void testBrmCardActive() throws ClientException {
//        BrmCardActiveRequest request = BrmCardActiveRequest.builder()
//                .cardNumber(CARD_NUM)
//                .personId(2L)
//                .departmentId(1L)
//                .startDate("2025-02-01 00:00:00")
//                .endDate("2028-02-01 00:00:00")
//                .category(CardCategory.IC.getValue())
//                .build();
//        iccV5SDK.brmCardActive(iccSdkConfig, request);
//    }
//
//    @Test
//    @DisplayName("cardAddAfter2-return")
//    public void testBrmCardReturn() {
//        BrmCardQueryRequest request = new BrmCardQueryRequest(CARD_NUM);
//        var res = iccV5SDK.brmCardQuery(iccSdkConfig, request);
//
//        BrmCardReturnRequest request1 = new BrmCardReturnRequest();
//        request1.setCardIds(List.of(res.getData().getId()));
//        iccV5SDK.brmCardReturn(iccSdkConfig, request1);
//    }
//
//    @Test
//    @DisplayName("cardAddAfter3-delete")
//    public void testBrmCardDel() {
//        BrmCardQueryRequest request = new BrmCardQueryRequest(CARD_NUM);
//        var res = iccV5SDK.brmCardQuery(iccSdkConfig, request);
//
//        BrmCardDelRequest request1 = new BrmCardDelRequest();
//        request1.setCardIds(List.of(res.getData().getId()));
//        iccV5SDK.brmCardDel(iccSdkConfig, request1);
//    }
//
//    @Test
//    public void testBrmPersonUpdateImage() throws IOException, ClientException {
//        InputStream inputStream = ResourceUtil.getStreamSafe("testdata/face.jpg");
//        byte[] allImageBytes = inputStream.readAllBytes();
//        String base64String = Base64.getEncoder().encodeToString(allImageBytes);
//
//        BrmImageUploadRequest imageUploadRequest = new BrmImageUploadRequest.Builder()
//                .fileSize(15534L)
//                .contentType(IMAGE_JPEG_VALUE)
//                .originBase64(base64String)
//                .build();
//        BrmImageUploadResponse brmImageUploadResponse = iccV5SDK.brmImageUpload(iccSdkConfig, imageUploadRequest);
//
//        PersonBioSignatures bioSignatures = new PersonBioSignatures();
//        bioSignatures.setPath(brmImageUploadResponse.getData().getFileUrl());
//        bioSignatures.setType(BiosignatureEnum.FACE_IMG.getId());
//        bioSignatures.setIndex(1);
//        bioSignatures.setPersonId(2L);
//
//        BrmPersonBatchUpdateImgRequest brmPersonBatchUpdateImgRequest = new BrmPersonBatchUpdateImgRequest.Builder()
//                .personBiosignatures(List.of(bioSignatures))
//                .build();
//        iccV5SDK.brmPersonUpdateFace(iccSdkConfig, brmPersonBatchUpdateImgRequest);
//    }
//
//    @Test
//    public void testBrmPersonAddAndDelete() throws ClientException {
//        BrmPersonGenIdResponse idResponse = iccV5SDK.brmPersonGenId(iccSdkConfig, new BrmPersonGenIdRequest());
//
//        // 创建人员新增请求
//        BrmPersonAddRequest request = new BrmPersonAddRequest.Builder()
//                .service("evo-thirdParty")
//                .id(idResponse.getData().getId())
//                .name("jerryge测试人员")
//                .departmentId(1L)
//                .code("18800000099")
//                .paperType(-1)  // 身份证
//                .paperNumber("330101199001011234")
//                .paperTypeName("身份证")
//                .paperAddress("***")
//                .sex(1)  // 男性
//                .phone("18800000099")
//                .email("test@example.com")
//                .validStartTime("2025-01-01 00:00:00")
//                .validEndTime("2028-01-01 00:00:00")
//                .build();
//
//        BrmPersonAddResponse response = iccV5SDK.brmPersonAdd(iccSdkConfig, request);
//        log.info("添加人员结果: {}", response);
//        assertNotNull(response, "响应不应为空");
//
//        // 创建人员删除请求
//        BrmPersonDelRequest delRequest = new BrmPersonDelRequest.Builder()
//                .ids(List.of(response.getData().getId()))
//                .build();
//
//        BrmPersonDelResponse delResponse = iccV5SDK.brmPersonDel(iccSdkConfig, delRequest);
//        log.info("删除人员结果: {}", delResponse);
//        assertNotNull(delResponse, "响应不应为空");
//    }
//
//
//    @Test
//    public void testBrmDeviceTreeList() {
////        BrmDeviceTreeListRequest request = new BrmDeviceTreeListRequest();
////        request.setId("001");
////        request.setType("001;;1");
////        request.setCheckStat(1);
////
////        BrmDeviceTreeListResponse response = iccV5SDK.brmDeviceTreeList(iccSdkConfig, request);
////
////        response.getData().getValue().forEach(item -> {
////            log.info("第一级：{}", item);
////        });
//
//        BrmDeviceTreeListRequest request1 = new BrmDeviceTreeListRequest();
//        request1.setId("1003454");
//        request1.setType("001;;1");
//        request1.setCheckStat(1);
//        BrmDeviceTreeListResponse response1 = iccV5SDK.brmDeviceTreeList(iccSdkConfig, request1);
//        response1.getData().getValue().forEach(item -> {
//            log.info("第二级：{}", item);
//        });
//
//    }
//
//    @Test
//    public void testCameraRealTimeLink() {
//        String channelId = "1003454$1$0$0";
//
//        RealTimeLinkRequest realTimeLinkRequest = RealTimeLinkRequest.builder()
//                .channelId(channelId)
//                .streamType("1")
//                .type("rtsp")
//                .build();
//        RealTimeLinkResponse response = iccV5SDK.cameraRealTimeLink(iccSdkConfig, realTimeLinkRequest);
//        log.info("response: {}", response);
//        Assertions.assertNotNull(response.getData().getUrl());
//    }

    @Test
    public void testParkCarReservationAdd() {
        ParkCarReservationAddRequest request = new ParkCarReservationAddRequest.Builder()
                .carNum("苏EJP888")
                .consumerName("jerryge")
                .telNumber("13913404821")
                .reservationType(DEFAULT_RESERVATION_TYPE)
                .startTimeStr("2025-07-01 00:00:00")
                .endTimeStr("2025-07-02 00:00:00")
                .build();
       ParkCarReservationAddResponse response = iccV5SDK.parkCarReservationAdd(iccSdkConfig, request);
       log.info("response: {}", response);
    }

}
