package info.zhihui.idevice.core.sdk.hikvision.isecure.constants;

/**
 * @author jerryge
 */
public class ISecureAccessConstant {
    /**
     * 门禁反控
     */
    public static final String ASSESS_DOOR_CONTROL = "/api/acs/%s/door/doControl";

    /**
     * 门禁卡绑定
     */
    public static final String CARD_BINDING = "/api/cis/%s/card/bindings";

    /**
     * 门禁卡退卡
     */
    public static final String CARD_RETURN = "/api/cis/%s/card/deletion";

    /**
     * 门禁设备查询
     */
    public static final String ACCESS_DEVICE_SEARCH = "/api/resource/%s/acsDevice/search";

    /**
     * 查询门禁点列表
     */
    public static final String DOOR_SEARCH = "/api/resource/%s/door/search";

    /**
     * 获取门禁设备在线状态
     */
    public static final String DOOR_ONLINE_STATUS = "/api/nms/%s/online/acs_device/get";
}
