package info.zhihui.idevice.core.sdk.hikvision.isecure.expection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ISecureRuntimeException extends RuntimeException {
    private String message;
    private String code;

    public ISecureRuntimeException(String message) {
        this.message = message;
    }

    public ISecureRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
