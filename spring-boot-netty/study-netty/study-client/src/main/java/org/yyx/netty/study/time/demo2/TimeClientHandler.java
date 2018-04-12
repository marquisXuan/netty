package org.yyx.netty.study.time.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * create by 叶云轩 at 2018/4/12-上午10:16
 * contact by tdg_yyx@foxmail.com
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    /**
     * TimeClientHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 上午10:16
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeClientHandler.class);
    /**
     * 模拟粘包/拆包问题计数器
     */
    private int counter;

    /**
     *
     */
    private byte[] req;

    public TimeClientHandler() {
        // region 模拟粘包/拆包问题相关代码
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
        // endregion
    }

    /**
     * 捕捉异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warn("--- [异常，释放资源] {}", cause.getMessage());
        ctx.close();
    }

    /**
     * 当客户端和服务端TCP链路建立成功之后调用此方法
     * 发送指令给服务端,调用ChannelHandlerContext.writeAndFlush方法将请求消息发送给服务端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // region 模拟粘包/拆包问题相关代码
        ByteBuf message;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
        // endregion
    }

    /**
     * 服务端返回应答消息时，调用此方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] request = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(request);
        String body = new String(request, "utf-8");
        LOGGER.info("--- [Now is] {} | [counter] {}", body, ++counter);
    }
}
