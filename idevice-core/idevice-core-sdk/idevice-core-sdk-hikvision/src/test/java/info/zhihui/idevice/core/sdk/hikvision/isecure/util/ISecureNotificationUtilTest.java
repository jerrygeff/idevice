package info.zhihui.idevice.core.sdk.hikvision.isecure.util;

import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.AccessEventData;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ISecureNotificationUtil单元测试
 *
 * @author jerryge
 */
public class ISecureNotificationUtilTest {

    /**
     * 测试正常情况下的转换功能
     */
    @Test
    public void testToEventListNormal() {
        // 准备测试数据
        Notification<String> notification = new Notification<>();
        Params<String> params = new Params<>();
        List<Event<String>> events = new ArrayList<>();

        Event<String> event1 = new Event<String>()
                .setSeq("1")
                .setEventType("ALARM")
                .setHappenTime("2023-01-01 12:00:00")
                .setSrcIndex("camera001")
                .setSrcName("前门摄像头")
                .setSrcParentIndex("building001")
                .setSrcType("camera")
                .setStatus(1)
                .setTimeout(60)
                .setEventId("event001")
                .setData("报警数据1");

        Event<String> event2 = new Event<String>()
                .setSeq("2")
                .setEventType("ALARM")
                .setHappenTime("2023-01-01 12:05:00")
                .setSrcIndex("camera002")
                .setSrcName("后门摄像头")
                .setSrcParentIndex("building001")
                .setSrcType("camera")
                .setStatus(1)
                .setTimeout(60)
                .setEventId("event002")
                .setData("报警数据2");

        events.add(event1);
        events.add(event2);

        params.setAbility("alarm")
              .setEvents(events)
              .setSendTime("2023-01-01 12:10:00");

        notification.setMethod("alarm")
                   .setParams(params);

        // 执行测试 - 直接使用notification对象作为输入
        List<Event<String>> result = ISecureNotificationUtil.toEventList(notification, String.class);

        // 验证结果
        Assertions.assertNotNull(result, "结果不应为空");
        Assertions.assertEquals(2, result.size(), "应返回2个事件");
        Assertions.assertEquals("1", result.get(0).getSeq(), "第一个事件的序列号应为1");
        Assertions.assertEquals("2", result.get(1).getSeq(), "第二个事件的序列号应为2");
        Assertions.assertEquals("报警数据1", result.get(0).getData(), "第一个事件的数据应为'报警数据1'");
        Assertions.assertEquals("报警数据2", result.get(1).getData(), "第二个事件的数据应为'报警数据2'");
    }

    /**
     * 测试空事件列表的情况
     */
    @Test
    public void testToEventListEmpty() {
        // 准备测试数据 - 空事件列表
        Notification<String> notification = new Notification<>();
        Params<String> params = new Params<>();
        params.setAbility("alarm")
              .setEvents(Collections.emptyList())
              .setSendTime("2023-01-01 12:10:00");

        notification.setMethod("alarm")
                   .setParams(params);

        // 执行测试 - 直接使用notification对象作为输入
        List<Event<String>> result = ISecureNotificationUtil.toEventList(notification, String.class);

        // 验证结果
        Assertions.assertNotNull(result, "结果不应为空");
        Assertions.assertTrue(result.isEmpty(), "事件列表应为空");
    }

    /**
     * 测试异常情况 - 传入null值
     */
    @Test
    public void testToEventListException() {
        // 执行测试并验证异常 - 传入null值应该会导致NullPointerException
        Assertions.assertThrows(NullPointerException.class, () -> {
            ISecureNotificationUtil.toEventList(null, AccessEventData.class);
        }, "传入null值应抛出NullPointerException");
    }

    /**
     * 测试AccessEventData类型的事件列表转换
     */
    @Test
    public void testToEventListWithAccessEventData() {
        // 准备测试数据
        Notification<AccessEventData> notification = new Notification<>();
        Params<AccessEventData> params = new Params<>();
        List<Event<AccessEventData>> events = new ArrayList<>();

        // 创建ExtEventCustomerNumInfo对象
        ExtEventCustomerNumInfo customerNumInfo = new ExtEventCustomerNumInfo()
                .setAccessChannel(1)
                .setEntryTimes(10)
                .setExitTimes(5)
                .setTotalTimes(15);

        // 创建ExtEventIdentityCardInfo对象
        ExtEventIdentityCardInfo identityCardInfo = new ExtEventIdentityCardInfo()
                .setAddress("北京市海淀区")
                .setBirth("1990-01-01")
                .setEndDate("2030-12-31")
                .setIdNum("110101199001010011")
                .setIssuingAuthority("北京市公安局")
                .setName("张三")
                .setNation(1)
                .setSex(1)
                .setStartDate("2020-01-01")
                .setTermOfValidity(10);

        // 创建AccessEventData对象
        AccessEventData accessEventData1 = new AccessEventData();
        accessEventData1.setExtAccessChannel(1);
        accessEventData1.setExtEventAlarmInID(101);
        accessEventData1.setExtEventAlarmOutID(201);
        accessEventData1.setExtEventCardNo("A12345");
        accessEventData1.setExtEventCaseID(301);
        accessEventData1.setExtEventCode(401);
        accessEventData1.setExtEventCustomerNumInfo(customerNumInfo);
        accessEventData1.setExtEventDoorID(501);
        accessEventData1.setExtEventIDCardPictureURL("http://example.com/idcard1.jpg");
        accessEventData1.setExtEventIdentityCardInfo(identityCardInfo);
        accessEventData1.setExtEventInOut(1);
        accessEventData1.setExtEventLocalControllerID(601);
        accessEventData1.setExtEventMainDevID(701);
        accessEventData1.setExtEventPersonNo("P12345");
        accessEventData1.setExtEventPictureURL("http://example.com/picture1.jpg");
        accessEventData1.setExtEventReaderID(801);
        accessEventData1.setExtEventReaderKind(1);
        accessEventData1.setExtEventReportChannel(1);
        accessEventData1.setExtEventRoleID(901);
        accessEventData1.setExtEventSubDevID(1001);
        accessEventData1.setExtEventSwipNum(1);
        accessEventData1.setExtEventType(1);
        accessEventData1.setExtEventVerifyID(1101);
        accessEventData1.setExtEventWhiteListNo(1201);
        accessEventData1.setExtReceiveTime("2023-01-01 12:00:00");
        accessEventData1.setSeq(1);
        accessEventData1.setUserType(1);
        accessEventData1.setSvrIndexCode("SIC12345");

        // 创建第二个AccessEventData对象，使用不同的值
        AccessEventData accessEventData2 = new AccessEventData();
        accessEventData2.setExtAccessChannel(2);
        accessEventData2.setExtEventAlarmInID(102);
        accessEventData2.setExtEventAlarmOutID(202);
        accessEventData2.setExtEventCardNo("B67890");
        accessEventData2.setExtEventCaseID(302);
        accessEventData2.setExtEventCode(402);
        // 使用相同的customerNumInfo和identityCardInfo对象
        accessEventData2.setExtEventCustomerNumInfo(customerNumInfo);
        accessEventData2.setExtEventDoorID(502);
        accessEventData2.setExtEventIDCardPictureURL("http://example.com/idcard2.jpg");
        accessEventData2.setExtEventIdentityCardInfo(identityCardInfo);
        accessEventData2.setExtEventInOut(0);
        accessEventData2.setExtEventLocalControllerID(602);
        accessEventData2.setExtEventMainDevID(702);
        accessEventData2.setExtEventPersonNo("P67890");
        accessEventData2.setExtEventPictureURL("http://example.com/picture2.jpg");
        accessEventData2.setExtEventReaderID(802);
        accessEventData2.setExtEventReaderKind(2);
        accessEventData2.setExtEventReportChannel(2);
        accessEventData2.setExtEventRoleID(902);
        accessEventData2.setExtEventSubDevID(1002);
        accessEventData2.setExtEventSwipNum(2);
        accessEventData2.setExtEventType(2);
        accessEventData2.setExtEventVerifyID(1102);
        accessEventData2.setExtEventWhiteListNo(1202);
        accessEventData2.setExtReceiveTime("2023-01-01 12:05:00");
        accessEventData2.setSeq(2);
        accessEventData2.setUserType(2);
        accessEventData2.setSvrIndexCode("SIC67890");

        // 创建Event对象并设置AccessEventData
        Event<AccessEventData> event1 = new Event<AccessEventData>()
                .setSeq("1")
                .setEventType("ACCESS")
                .setHappenTime("2023-01-01 12:00:00")
                .setSrcIndex("door001")
                .setSrcName("前门门禁")
                .setSrcParentIndex("building001")
                .setSrcType("door")
                .setStatus(1)
                .setTimeout(60)
                .setEventId("event001")
                .setData(accessEventData1);

        Event<AccessEventData> event2 = new Event<AccessEventData>()
                .setSeq("2")
                .setEventType("ACCESS")
                .setHappenTime("2023-01-01 12:05:00")
                .setSrcIndex("door002")
                .setSrcName("后门门禁")
                .setSrcParentIndex("building001")
                .setSrcType("door")
                .setStatus(1)
                .setTimeout(60)
                .setEventId("event002")
                .setData(accessEventData2);

        events.add(event1);
        events.add(event2);

        params.setAbility("access")
              .setEvents(events)
              .setSendTime("2023-01-01 12:10:00");

        notification.setMethod("access")
                   .setParams(params);

        // 执行测试 - 使用专门处理AccessEventData的方法
        List<Event<AccessEventData>> result = ISecureNotificationUtil.toEventList(notification, AccessEventData.class);

        // 验证结果
        Assertions.assertNotNull(result, "结果不应为空");
        Assertions.assertEquals(2, result.size(), "应返回2个事件");

        // 验证第一个事件
        Event<AccessEventData> resultEvent1 = result.get(0);
        Assertions.assertEquals("1", resultEvent1.getSeq(), "第一个事件的序列号应为1");
        Assertions.assertEquals("ACCESS", resultEvent1.getEventType(), "第一个事件的类型应为ACCESS");

        // 验证第一个事件的AccessEventData
        AccessEventData resultData1 = resultEvent1.getData();
        Assertions.assertNotNull(resultData1, "第一个事件的数据不应为空");
        Assertions.assertEquals(1, resultData1.getExtAccessChannel(), "ExtAccessChannel应为1");
        Assertions.assertEquals("A12345", resultData1.getExtEventCardNo(), "ExtEventCardNo应为A12345");
        Assertions.assertEquals("http://example.com/idcard1.jpg", resultData1.getExtEventIDCardPictureURL(), "ExtEventIDCardPictureURL应正确");

        // 验证第一个事件的ExtEventCustomerNumInfo
        ExtEventCustomerNumInfo resultCustomerNumInfo1 = resultData1.getExtEventCustomerNumInfo();
        Assertions.assertNotNull(resultCustomerNumInfo1, "ExtEventCustomerNumInfo不应为空");
        Assertions.assertEquals(1, resultCustomerNumInfo1.getAccessChannel(), "AccessChannel应为1");
        Assertions.assertEquals(10, resultCustomerNumInfo1.getEntryTimes(), "EntryTimes应为10");

        // 验证第一个事件的ExtEventIdentityCardInfo
        ExtEventIdentityCardInfo resultIdentityCardInfo1 = resultData1.getExtEventIdentityCardInfo();
        Assertions.assertNotNull(resultIdentityCardInfo1, "ExtEventIdentityCardInfo不应为空");
        Assertions.assertEquals("张三", resultIdentityCardInfo1.getName(), "Name应为张三");
        Assertions.assertEquals("110101199001010011", resultIdentityCardInfo1.getIdNum(), "IdNum应正确");

        // 验证第二个事件
        Event<AccessEventData> resultEvent2 = result.get(1);
        Assertions.assertEquals("2", resultEvent2.getSeq(), "第二个事件的序列号应为2");

        // 验证第二个事件的AccessEventData
        AccessEventData resultData2 = resultEvent2.getData();
        Assertions.assertNotNull(resultData2, "第二个事件的数据不应为空");
        Assertions.assertEquals(2, resultData2.getExtAccessChannel(), "ExtAccessChannel应为2");
        Assertions.assertEquals("B67890", resultData2.getExtEventCardNo(), "ExtEventCardNo应为B67890");
    }
}