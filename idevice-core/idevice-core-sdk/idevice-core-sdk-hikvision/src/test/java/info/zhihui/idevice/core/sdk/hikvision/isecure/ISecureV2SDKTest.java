package info.zhihui.idevice.core.sdk.hikvision.isecure;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecurePageData;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2.*;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.config.ISecureSDKConfig;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2.CameraControlResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jerryge
 */
@Slf4j
public class ISecureV2SDKTest {
    private final ISecureV2SDK iSecureV2SDK = new ISecureV2SDK();

    private final ISecureSDKConfig iSecureSDKConfig = new ISecureSDKConfig()
            .setHost("127.0.0.1")
            .setAppKey("aaaaa")
            .setAppSecret("bbbb");

    /**
     * 测试findAllByPage方法 - 单页数据场景
     */
    @Test
    public void testFindAllByPageSinglePage() {
        // 创建测试请求
        DoorSearchRequest request = DoorSearchRequest.builder()
                .name("测试门禁")
                .build();

        // 模拟单页数据响应
        List<DoorInfo> expectedDoors = createTestDoorList(5);

        // 使用自定义的BiFunction来模拟finder函数
        List<DoorInfo> result = iSecureV2SDK.findAllByPage(iSecureSDKConfig, request, (config, req) -> {
            // 验证分页参数是否正确设置
            Assertions.assertEquals(1, req.getPageNo());
            Assertions.assertEquals(500, req.getPageSize());

            // 创建模拟响应
            DoorSearchResponse response = new DoorSearchResponse();
            response.setCode("0");
            response.setMsg("成功");
            response.setData(new ISecurePageData<>());
            response.getData().setTotal(expectedDoors.size());
            response.getData().setPageNo(req.getPageNo());
            response.getData().setPageSize(req.getPageSize());
            response.getData().setList(expectedDoors);

            return response;
        });

        // 验证结果
        Assertions.assertEquals(expectedDoors.size(), result.size());
        Assertions.assertEquals(expectedDoors, result);
    }

    /**
     * 测试findAllByPage方法 - 多页数据场景
     */
    @Test
    public void testFindAllByPageMultiplePages() {
        // 创建测试请求
        DoorSearchRequest request = DoorSearchRequest.builder()
                .name("测试门禁")
                .build();

        // 模拟多页数据
        List<DoorInfo> page1 = createTestDoorList(500);
        List<DoorInfo> page2 = createTestDoorList(300);
        List<DoorInfo> allExpectedDoors = new ArrayList<>(page1);
        allExpectedDoors.addAll(page2);

        // 使用自定义的BiFunction来模拟finder函数
        List<DoorInfo> result = iSecureV2SDK.findAllByPage(iSecureSDKConfig, request, (config, req) -> {
            // 创建模拟响应
            DoorSearchResponse response = new DoorSearchResponse();
            response.setCode("0");
            response.setMsg("成功");
            response.setData(new ISecurePageData<>());
            response.getData().setTotal(800); // 总共800条数据
            response.getData().setPageNo(req.getPageNo());
            response.getData().setPageSize(req.getPageSize());

            // 根据页码返回不同的数据
            if (req.getPageNo() == 1) {
                response.getData().setList(page1);
            } else if (req.getPageNo() == 2) {
                response.getData().setList(page2);
            } else {
                response.getData().setList(Collections.emptyList());
            }

            return response;
        });

        // 验证结果
        Assertions.assertEquals(allExpectedDoors.size(), result.size());
        Assertions.assertEquals(800, result.size());
    }

    /**
     * 测试findAllByPage方法 - 空结果场景
     */
    @Test
    public void testFindAllByPageEmptyResult() {
        // 创建测试请求
        DoorSearchRequest request = DoorSearchRequest.builder()
                .name("不存在的门禁")
                .build();

        // 使用自定义的BiFunction来模拟finder函数
        List<DoorInfo> result = iSecureV2SDK.findAllByPage(iSecureSDKConfig, request, (config, req) -> {
            // 创建模拟响应
            DoorSearchResponse response = new DoorSearchResponse();
            response.setCode("0");
            response.setMsg("成功");
            response.setData(new ISecurePageData<>());
            response.getData().setTotal(0);
            response.getData().setPageNo(req.getPageNo());
            response.getData().setPageSize(req.getPageSize());
            response.getData().setList(Collections.emptyList());

            return response;
        });

        // 验证结果
        Assertions.assertTrue(result.isEmpty());
    }

    /**
     * 测试findAllByPage方法 - 异常处理场景
     */
    @Test
    public void testFindAllByPageWithException() {
        // 创建测试请求
        DoorSearchRequest request = DoorSearchRequest.builder()
                .name("测试门禁")
                .build();

        // 模拟多页数据，但第二页请求出现异常
        List<DoorInfo> page1 = createTestDoorList(500);

        // 使用自定义的BiFunction来模拟finder函数
        Assertions.assertThrows(BusinessRuntimeException.class, () -> {
            iSecureV2SDK.findAllByPage(iSecureSDKConfig, request, (config, req) -> {
                // 创建模拟响应
                DoorSearchResponse response = new DoorSearchResponse();
                response.setCode("0");
                response.setMsg("成功");
                response.setData(new ISecurePageData<>());
                response.getData().setPageNo(req.getPageNo());
                response.getData().setPageSize(req.getPageSize());

                // 第一页正常返回，第二页抛出异常
                if (req.getPageNo() == 1) {
                    response.getData().setTotal(1000); // 总共1000条数据，需要2页
                    response.getData().setList(page1);
                } else {
                    throw new RuntimeException("模拟第二页请求异常");
                }

                return response;
            });
        }, "获取全部数据异常");
    }

    /**
     * 创建测试用的门禁点列表
     */
    private List<DoorInfo> createTestDoorList(int count) {
        List<DoorInfo> doors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DoorInfo door = new DoorInfo();
            door.setIndexCode("door" + i);
            door.setName("测试门禁" + i);
            door.setDoorNo(String.valueOf(i));
            door.setChannelNo("channel" + i);
            door.setResourceType("door");
            door.setParentIndexCode("parent" + i);
            door.setRegionIndexCode("region" + i);
            door.setRegionName("区域" + i);
            doors.add(door);
        }
        return doors;
    }


    @Test
    void responseNotThrow() {
        String str = "{\"code\":\"0\",\"msg\":\"success\",\"data\":{}}";
        var response = JacksonUtil.fromJson(str, CameraControlResponse.class);
        log.info("response: {}", response);
    }

    @Test
    void cardBingingRequestTest() {
        CardBindingRequest request = CardBindingRequest.builder()
                .cardList(List.of(new CardBindingInfo().setPersonId("123").setCardNo("Aaaa")))
                .startDate("2023-08-01")
                .endDate("2023-08-31")
                .build();

        request.validate();

        CardBindingRequest requestErr = CardBindingRequest.builder()
                .cardList(List.of(new CardBindingInfo().setPersonId("123").setCardNo("Aaaa")))
                .startDate("2023-08-01")
                .endDate("2038-08-31")
                .build();
        Assertions.assertThrows(ParamException.class, requestErr::validate);
    }

}
