package info.zhihui.idevice.core.sdk.dahua.icc.expection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IccRuntimeException extends RuntimeException {
    private String message;
    private String code;

    public IccRuntimeException(String message) {
        this.message = message;
    }

    public IccRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
