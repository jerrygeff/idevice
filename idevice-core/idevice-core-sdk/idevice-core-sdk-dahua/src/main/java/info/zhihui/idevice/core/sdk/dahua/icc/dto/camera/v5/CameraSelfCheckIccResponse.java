package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import info.zhihui.idevice.core.sdk.dahua.icc.dto.IccSelfCheckResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.expection.IccRuntimeException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jerryge
 */
@Slf4j
public class CameraSelfCheckIccResponse extends IccSelfCheckResponse {
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void checkResult() {
        if (!"1000".equals(this.getCode())) {
            log.error("大华平台返回结果异常，请求id：{}，错误码：{}，错误信息：{}", this.getRequestId(), this.getCode(), this.getDesc());
            String errMsg = this.getDesc();
            throw new IccRuntimeException(this.getCode(), "请求大华远程接口结果异常：" + errMsg);
        }
    }
}
