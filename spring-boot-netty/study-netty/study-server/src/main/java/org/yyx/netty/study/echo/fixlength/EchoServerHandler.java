package org.yyx.netty.study.echo.fixlength;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * create by 叶云轩 at 2018/4/12-下午4:12
 * contact by tdg_yyx@foxmail.com
 */
public class EchoServerHandler extends ChannelHandlerAdapter {

    /**
     * MessagePackServerHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 下午4:20
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServerHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("--- [发生异常] 释放资源");
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("--- [接收到客户端的数据] {}", msg);
    }
}
