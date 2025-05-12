package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import lombok.Builder;
import lombok.Data;

/**
 * 实时视频流请求数据
 *
 * @author jerryge
 */
@Data
@Builder
public class RealTimeLinkRequestData {
    private String channelId;
    private String streamType;
    private String type;
}