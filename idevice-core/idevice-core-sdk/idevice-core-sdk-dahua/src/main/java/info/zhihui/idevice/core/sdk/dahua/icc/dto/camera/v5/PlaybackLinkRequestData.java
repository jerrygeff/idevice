package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import lombok.Builder;
import lombok.Data;

/**
 * 回放视频流请求数据
 *
 * @author jerryge
 */
@Data
@Builder
public class PlaybackLinkRequestData {
    private String channelId;
    private String streamType;
    private String type;
    private String recordType;
    private String beginTime;
    private String endTime;
    private String recordSource;
}