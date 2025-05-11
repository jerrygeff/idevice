package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureCameraConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * 回放取流请求参数
 */
@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class PlaybackLinkRequest extends ISecureJsonRequest<PlaybackLinkResponse> {
    private static final String VERSION = "v2";
    private static final String API_URL = String.format(ISecureCameraConstant.CAMERA_URL_PLAYBACK, VERSION);
    private static final String CAMERA_INDEX_CODE_ERROR_MSG = "cameraIndexCode不能为空";
    private static final String BEGIN_TIME_ERROR_MSG = "beginTime不能为空";
    private static final String END_TIME_ERROR_MSG = "endTime不能为空";
    private static final String END_BEFORE_BEGIN_ERROR_MSG = "endTime不能早于beginTime";
    private static final String TIME_INTERVAL_TOO_LONG_ERROR_MSG = "开始时间和结束时间间隔不能超过三天";
    private static final String TIME_FORMAT_ERROR_MSG = "beginTime或endTime格式不正确，需为yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /** 摄像头唯一标识 */
    private final String cameraIndexCode;
    /** 存储类型（0-中心，1-设备） */
    private final String recordLocation;
    /** 协议（如rtsp） */
    private final String protocol;
    /** 传输模式（0-UDP，1-TCP） */
    private final Integer transmode;
    /** 起始时间，格式：yyyy-MM-dd'T'HH:mm:ss.SSSXXX */
    private final String beginTime;
    /** 结束时间，格式：yyyy-MM-dd'T'HH:mm:ss.SSSXXX */
    private final String endTime;
    /** 请求唯一标识 */
    private final String uuid;
    /** 扩展参数 */
    private final String expand;
    /** 码流格式（如ps） */
    private final String streamform;
    /** 锁定类型 */
    private final Integer lockType;

    private PlaybackLinkRequest(String cameraIndexCode, String recordLocation, String protocol, Integer transmode, String beginTime, String endTime, String uuid, String expand, String streamform, Integer lockType) {
        super(API_URL);
        this.cameraIndexCode = cameraIndexCode;
        this.recordLocation = recordLocation;
        this.protocol = protocol;
        this.transmode = transmode;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.uuid = uuid;
        this.expand = expand;
        this.streamform = streamform;
        this.lockType = lockType;
    }

    @Override
    public void validate() {
        super.validate();
        if (StringUtils.isBlank(cameraIndexCode)) {
            throw new ParamException(CAMERA_INDEX_CODE_ERROR_MSG);
        }
        if (StringUtils.isBlank(beginTime)) {
            throw new ParamException(BEGIN_TIME_ERROR_MSG);
        }
        if (StringUtils.isBlank(endTime)) {
            throw new ParamException(END_TIME_ERROR_MSG);
        }
        try {
            ZonedDateTime begin = ZonedDateTime.parse(beginTime);
            ZonedDateTime end = ZonedDateTime.parse(endTime);
            if (end.isBefore(begin)) {
                throw new ParamException(END_BEFORE_BEGIN_ERROR_MSG);
            }
           Duration duration = Duration.between(begin, end);
            if (duration.toDays() > 3) {
                throw new ParamException(TIME_INTERVAL_TOO_LONG_ERROR_MSG);
            }
        } catch (DateTimeParseException e) {
            throw new ParamException(TIME_FORMAT_ERROR_MSG);
        }
    }

    @Override
    public Class<PlaybackLinkResponse> getResponseClass() {
        return PlaybackLinkResponse.class;
    }
}