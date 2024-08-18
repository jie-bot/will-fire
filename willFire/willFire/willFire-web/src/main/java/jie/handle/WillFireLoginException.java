package jie.handle;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:13
 * @注释 登录异常
 */
public class WillFireLoginException extends RuntimeException {

    private String msg;

    public WillFireLoginException() {
        super();
    }

    public WillFireLoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WillFireLoginException(Throwable cause) {
        super(cause);
        this.msg = cause.getMessage();
    }

    public WillFireLoginException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
