package org.yyx.netty.exception;

/**
 * 没有可用的通道异常
 * <p>
 *
 * @author 叶云轩 at marquis_xuan@163.com
 * @date 2018/11/2-16:00
 */
public class NoUseableChannel extends RuntimeException{
    private static final long serialVersionUID = 7762465537123947683L;

    public NoUseableChannel() {
        super();
    }

    public NoUseableChannel(String message) {
        super(message);
    }
}
