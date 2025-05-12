package info.zhihui.idevice.core.sdk.dahua.icc.constants;

/**
 * 视频应用相关
 *
 * @author jerryge
 */
public class IccCameraConstant {
    /**
     * 实时预览
     */
    public static final String CAMERA_URL_REALTIME =
            "/evo-apigw/admin/API/video/stream/realtime";

    /**
     * 回放视频流
     */
    public static final String CAMERA_URL_PLAYBACK = "/evo-apigw/admin/API/video/stream/record";

    /**
     * 摄像头控制
     */
    public static final String CAMERA_URL_CONTROL = "/evo-apigw/admin/API/DMS/Ptz/OperateDirect";
}
