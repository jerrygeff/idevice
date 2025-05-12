package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.PersonPropertyEnum;
import lombok.Data;

@Data
public class PersonExtendData {

    /**
     * @see PersonPropertyEnum
     */
    private Integer personProperty;

    private String userVerifyMode;

    /**
     * 最大认证次数，当且仅当personProperty是来宾时有效 0为无次数限制（默认为0）
     */
    private Integer maxOpenDoorTime;

    /**
     * 是否是首人，需要在设备上配置首卡开门持续时长
     */
    private Boolean leaderPersonEnabled;

    /**
     * 是否关闭延迟开门，需在设备上配置延迟开门时长
     */
    private Boolean closeDelayEnabled;
}