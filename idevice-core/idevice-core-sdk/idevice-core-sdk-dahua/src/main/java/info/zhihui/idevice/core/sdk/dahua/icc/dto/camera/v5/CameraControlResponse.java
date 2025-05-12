package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import lombok.extern.slf4j.Slf4j;

/**
 * 摄像头控制响应
 *
 * @author jerryge
 */
@Slf4j
public class CameraControlResponse extends CameraSelfCheckIccResponse {
    private CameraControlResponseData data;

    public CameraControlResponseData getData() {
        return data;
    }

    public void setData(CameraControlResponseData data) {
        this.data = data;
    }
}