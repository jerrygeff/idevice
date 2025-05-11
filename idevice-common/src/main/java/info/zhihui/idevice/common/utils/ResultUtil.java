package info.zhihui.idevice.common.utils;

import info.zhihui.idevice.common.constant.ResultCode;
import info.zhihui.idevice.common.vo.RestResult;

public class ResultUtil {

    private static <T> RestResult<T> genResult(Boolean success, T data, Integer code, String msg) {
        RestResult<T> result = new RestResult<>();

        result.setSuccess(success);
        result.setData(data);
        result.setCode(code);
        result.setMessage(msg);

        return result;
    }

    public static <T> RestResult<T> success(T data, Integer code, String msg) {
        return genResult(true, data, code, msg);
    }

    public static <T> RestResult<T> success(T data) {
        return genResult(true, data, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public static <T> RestResult<T> success() {
        return genResult(true, null, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public static <T> RestResult<T> error(T data, Integer code, String msg) {
        return genResult(false, data, code, msg);
    }

    public static <T> RestResult<T> error(Integer code, String msg) {
        return genResult(false, null, code, msg);
    }

    public static <T> RestResult<T> error(String msg) {
        return genResult(false, null, ResultCode.BUSINESS_ERROR.getCode(), msg);
    }

}
