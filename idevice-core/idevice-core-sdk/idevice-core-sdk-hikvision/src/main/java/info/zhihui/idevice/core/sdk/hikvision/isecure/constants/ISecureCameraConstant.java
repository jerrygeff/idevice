package info.zhihui.idevice.core.sdk.hikvision.isecure.constants;

/**
 * @author jerryge
 */
public class ISecureCameraConstant {
    /**
     * 实时预览
     */
    public static final String CAMERA_URL_REALTIME =
            "/api/video/%s/cameras/previewURLs";

    /**
     * 回放
     */
    public static final String CAMERA_URL_PLAYBACK =
            "/api/video/%s/cameras/playbackURLs";
    /**
     * 云台控制
     */
    public static final String CAMERA_URL_CONTROL =
            "/api/video/%s/ptzs/controlling";

    /**
     * 摄像头搜索
     */
    public static final String CAMERA_SEARCH =
            "/api/resource/%s/camera/search";

    /**
     * 摄像头在线状态搜索
     */
    public static final String CAMERA_ONLINE_SEARCH =
            "/api/nms/%s/online/camera/get";

    /**
     * 编码设备搜索
     */
    public static final String CAMERA_ENCODE_DEVICE_SEARCH =
            "/api/resource/%s/encodeDevice/search";
}
