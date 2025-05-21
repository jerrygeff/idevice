package info.zhihui.idevice.core.sdk.dahua.icc.dto.park.v5;

import info.zhihui.idevice.core.sdk.dahua.icc.dto.IccSelfCheckResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.expection.IccRuntimeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 添加预约车辆响应
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class ParkCarReservationAddResponse extends IccSelfCheckResponse {
    /**
     * 预约记录id
     */
    private String data;

    @Override
    public void checkResult() {
        if (!Boolean.TRUE.equals(this.isSuccess()) || !Objects.equals(Integer.parseInt(this.getCode()), 200)) {
            log.error("大华平台返回结果异常，请求id：{}，错误码：{}，错误信息：{}", this.getRequestId(), this.getCode(), this.getErrMsg());

            throw new IccRuntimeException(this.getCode(), "请求大华远程接口结果异常：" + this.getErrMsg());
        }
    }
}