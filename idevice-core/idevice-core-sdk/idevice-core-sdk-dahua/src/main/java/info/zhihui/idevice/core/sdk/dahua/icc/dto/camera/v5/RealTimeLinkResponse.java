package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import lombok.extern.slf4j.Slf4j;

/**
 * 实时视频流响应
 *
 * @author jerryge
 */
@Slf4j
public class RealTimeLinkResponse extends CameraSelfCheckIccResponse {
    private RealTimeLinkResponseData data;

    public RealTimeLinkResponseData getData() {
        return data;
    }

    public void setData(RealTimeLinkResponseData data) {
        this.data = data;
    }
}