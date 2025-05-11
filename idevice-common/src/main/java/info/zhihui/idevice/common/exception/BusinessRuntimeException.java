package info.zhihui.idevice.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessRuntimeException extends RuntimeException {
    private String message;
    private Integer code;

    public BusinessRuntimeException(String message) {
        this.message = message;
    }

    public BusinessRuntimeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
