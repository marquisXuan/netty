package org.yyx.netty.study.time.demo1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
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
     * 创建NioSocketChannel成功之后，进行初始化时，
     * 将ChannelHandler设置到ChannelPipeline中，
     * 同样，用于处理网络I/O事件
     *
     * @param ch
     *
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        LOGGER.info("--- [通道初始化]");
        ch.pipeline().addLast(new TimeServerHandler());
    }
}