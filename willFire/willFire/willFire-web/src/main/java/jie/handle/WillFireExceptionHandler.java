package jie.handle;

import com.alibaba.fastjson.JSON;
import jie.config.WillFireResult;
import jie.enums.CodeMsg;
import jie.utils.WillFireUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:11
 * @注释 异常处理类
 */
@ControllerAdvice
@Slf4j
public class WillFireExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public WillFireResult<Object> handleException(Exception ex) {
        if (ex instanceof WillFireRuntimeException) {
            WillFireRuntimeException e = (WillFireRuntimeException) ex;
            return WillFireResult.fail(e.getCode() == null ? 200 : e.getCode(), e.getMsg());
        }

        if (ex instanceof WillFireLoginException) {
            WillFireLoginException e = (WillFireLoginException) ex;
            return WillFireResult.fail(300, e.getMessage());
        }

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            Map<String, String> collect = e.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return WillFireResult.fail(JSON.toJSONString(collect));
        }

        if (ex instanceof MissingServletRequestParameterException) {
            return WillFireResult.fail(CodeMsg.PARAMETER_ERROR);
        }

        log.error("URL------" + WillFireUtil.getRequest().getRequestURL() + " 出错了");
        log.error(ex.getMessage());
        return WillFireResult.fail(CodeMsg.FAIL);
    }
}
