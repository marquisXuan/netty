package org.yyx.netty.study.time.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 针对网络事件进行读写操作的类
 * <p>
 * create by 叶云轩 at 2018/4/11-下午6:35
 * contact by tdg_yyx@foxmail.com
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    /**
     * TimeServerHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 上午11:30
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeServerHandler.class);
    /**
     * 模拟粘包/拆包问题计数器
     */
    private int counter;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    /**
     * 读事件
     *
     * @param ctx ChannelHandlerContext
     * @param msg 消息
     *
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // region 解决模拟粘包/拆包问题相关代码
        String body = (String) msg;
        LOGGER.info("--- [接收到的数据] {} | [counter] {}", body, ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        // endregion

        // 异步发送应答消息给Client
        ctx.writeAndFlush(resp); // --> 将消息放到发送缓冲数组中
    }
}
