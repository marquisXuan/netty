package org.yyx.netty.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义的NettyClientHandler
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/11/1-17:20
 */
public class NettyClientHandlerAdapter extends ChannelHandlerAdapter {

    /**
     * NettyClientHandlerAdapter 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClientHandlerAdapter.class);

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        LOGGER.info("{} -> [连接建立成功] {}", this.getClass().getName(), channelHandlerContext.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("{} -> [客户端收到的消息] {}", this.getClass().getName(), msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("{} -> [客户端消息接收完毕] {}", this.getClass().getName(), ctx.channel().id());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LOGGER.info("{} -> [客户端心跳监测发送] 通道编号：{}", this.getClass().getName(), ctx.channel().id());
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush("ping-pong-ping-pong");
        }
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        LOGGER.info("{} -> [关闭通道] {}", this.getClass().getName(), ctx.channel().id());
        super.close(ctx, promise);
    }
}