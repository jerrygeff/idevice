package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event;

import lombok.Data;

@Data
public class AccessEventData {
    private int ExtAccessChannel;
    private int ExtEventAlarmInID;
    private int ExtEventAlarmOutID;
    private String ExtEventCardNo;
    private int ExtEventCaseID;
    private int ExtEventCode;
    private info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common.ExtEventCustomerNumInfo ExtEventCustomerNumInfo;
    private int ExtEventDoorID;
    private String ExtEventIDCardPictureURL;
    private info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common.ExtEventIdentityCardInfo ExtEventIdentityCardInfo;
    private int ExtEventInOut;
    private int ExtEventLocalControllerID;
    private int ExtEventMainDevID;
    private String ExtEventPersonNo;
    private String ExtEventPictureURL;
    private int ExtEventReaderID;
    private int ExtEventReaderKind;
    private int ExtEventReportChannel;
    private int ExtEventRoleID;
    private int ExtEventSubDevID;
    private int ExtEventSwipNum;
    private int ExtEventType;
    private int ExtEventVerifyID;
    private int ExtEventWhiteListNo;
    private String ExtReceiveTime;
    private int Seq;
    private int UserType;
    private String svrIndexCode;
}
