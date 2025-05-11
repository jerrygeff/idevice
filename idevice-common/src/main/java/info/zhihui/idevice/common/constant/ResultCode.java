package info.zhihui.idevice.common.constant;

import lombok.Getter;

@Getter
public enum ResultCode {
    // 通用
    SUCCESS(100001, "成功"),
    FAILED(-100001, "接口异常"),

    BUSINESS_ERROR(-101001, "错误"),
    PARAMETER_ERROR(-102001, "接口参数异常"),

    ;

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
