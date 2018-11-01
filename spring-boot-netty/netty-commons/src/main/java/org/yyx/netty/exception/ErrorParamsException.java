package org.yyx.netty.exception;

/**
 * 参数错误异常
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/11/1-14:41
 */
public class ErrorParamsException extends RuntimeException {
    private static final long serialVersionUID = -623198335011996153L;

    public ErrorParamsException() {
        super();
    }

    public ErrorParamsException(String message) {
        super(message);
    }
}
