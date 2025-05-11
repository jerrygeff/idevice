package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import lombok.Data;

import java.util.List;

/**
 * 回放取流响应
 */
@Data
public class PlaybackLinkResponseData {
    /**
     * 录像片段列表
     */
    private List<PlaybackSegment> list;
    /**
     * 请求唯一标识
     */
    private String uuid;
    /**
     * 回放流URL
     */
    private String url;
}