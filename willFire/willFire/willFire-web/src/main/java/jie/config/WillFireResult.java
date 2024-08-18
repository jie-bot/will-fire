package jie.config;

import jie.enums.CodeMsg;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午8:59
 * @注释
 */

@Data


public class WillFireResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;
    private long currentTimeMillis = System.currentTimeMillis();

    public WillFireResult() {
        this.code = 200;
    }

    public WillFireResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public WillFireResult(T data) {
        this.data = data;
        this.code = 200;
    }

    public WillFireResult(String message) {
        this.message = message;
        this.code = 200;
    }

    public static <T> WillFireResult<T> fail(String message) {
        return new WillFireResult(500, message);
    }

    public static <T> WillFireResult<T> fail(CodeMsg codeMsg) {
        return new WillFireResult(codeMsg.getCode(), codeMsg.getMsg());
    }

    public static <T> WillFireResult<T> fail(Integer code, String message) {
        return new WillFireResult(code, message);
    }

    public static <T> WillFireResult<T> success(T data) {
        return new WillFireResult(data);
    }

    public static <T> WillFireResult<T> success() {
        return new WillFireResult();
    }
}
