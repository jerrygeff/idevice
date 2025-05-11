package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import lombok.extern.slf4j.Slf4j;

/**
 * 回放视频流响应
 *
 * @author jerryge
 */
@Slf4j
public class PlaybackLinkResponse extends CameraSelfCheckIccResponse {
    private PlaybackLinkResponseData data;

    public PlaybackLinkResponseData getData() {
        return data;
    }

    public void setData(PlaybackLinkResponseData data) {
        this.data = data;
    }
}