package info.zhihui.idevice.handler;

import cn.hutool.core.lang.Opt;
import info.zhihui.idevice.common.constant.ResultCode;
import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.common.exception.DataException;
import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.common.utils.ResultUtil;
import info.zhihui.idevice.common.vo.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public RestResult<Void> handleRuntimeException(NotFoundException e) {
        log.error("handle NotFoundException: {}", e.getMessage());
        return ResultUtil.error(ResultCode.BUSINESS_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(DataException.class)
    public RestResult<Void> handleDataException(DataException e) {
        log.error("handle DataException: {}", e.getMessage());
        return ResultUtil.error(ResultCode.BUSINESS_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(ParamException.class)
    public RestResult<Void> handleRuntimeException(ParamException e) {
        log.error("handle ParamException: ", e);
        return ResultUtil.error(ResultCode.PARAMETER_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(BusinessRuntimeException.class)
    public RestResult<Void> handleRuntimeException(BusinessRuntimeException e) {
        log.error("handle BusinessRuntimeException: {}", e.getMessage());
        return ResultUtil.error(Opt.ofNullable(e.getCode()).orElse(ResultCode.BUSINESS_ERROR.getCode()), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult<Void> handleArgumentException(MethodArgumentNotValidException e) {
        log.error("handle MethodArgumentNotValidException: {}", e.getMessage());

        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorStr = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = String.format("%s%s;", fieldError.getField(), fieldError.getDefaultMessage());
            errorStr.append(msg);
        }
        return ResultUtil.error(ResultCode.PARAMETER_ERROR.getCode(), errorStr.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<Void> handleRuntimeException(RuntimeException e) {
        log.error("handle runtime exception: ", e);
        String message = "系统异常，请稍后再试";
        return ResultUtil.error(ResultCode.FAILED.getCode(), message);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public RestResult<Void> handleDuplicateKeyEx(DuplicateKeyException e) {
        log.warn("数据重复: {}", e.getMessage(), e);
        return ResultUtil.error("数据已存在，请确认");
    }

}
