package jie.handle;

import jie.enums.CodeMsg;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:09
 * @注释 运行时异常
 */
public class WillFireRuntimeException extends RuntimeException {
    private String msg;

    private Integer code;

    public WillFireRuntimeException() {
        super();
    }

    public WillFireRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 200;
    }


    public WillFireRuntimeException(Throwable cause) {
        super(cause);
        this.msg = cause.getMessage();
        this.code = 200;
    }

    public WillFireRuntimeException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.msg = codeMsg.getMsg();
        this.code = codeMsg.getCode();
    }

    public WillFireRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
        this.code = 200;
    }

    public WillFireRuntimeException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
    public Integer getCode() { return code; }
}
