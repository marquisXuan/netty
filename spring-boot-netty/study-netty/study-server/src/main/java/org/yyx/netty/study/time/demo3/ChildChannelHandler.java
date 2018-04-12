package org.yyx.netty.study.time.demo3;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * I/O事件处理类
 * <p>
 * create by 叶云轩 at 2018/4/11-下午6:34
 * contact by tdg_yyx@foxmail.com
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    /**
     * ChildChannelHandler 日志控制器
     * Create by 叶云轩 at 2018/4/12 上午11:29
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChildChannelHandler.class);


    /**
     * LineBasedFrameDecoder: 以换行符为结束标志的解码器
     *  依次遍历ByteBuf中的可读字节，
     *  如果有"\n" 或者 "\r\n" -> 就以此位置为结束位置 之后将可读索引到结束位置区间的字节组成一行
     */

    /**
     * 创建NioSocketChannel成功之后，进行初始化时，
     * 将ChannelHandler设置到ChannelPipeline中，
     * 同样，用于处理网络I/O事件
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        LOGGER.info("--- [通道初始化]");
        // region 解决粘包/拆包问题相关代码
        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 将接收到的对象转成字符串
        ch.pipeline().addLast(new StringDecoder());
        // endregion

        ch.pipeline().addLast(new TimeServerHandler());
    }
}