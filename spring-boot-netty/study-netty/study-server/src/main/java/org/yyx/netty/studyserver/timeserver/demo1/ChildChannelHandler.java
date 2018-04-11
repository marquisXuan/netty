package org.yyx.netty.studyserver.timeserver.demo1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * I/O事件处理类
 * <p>
 * create by 叶云轩 at 2018/4/11-下午6:34
 * contact by tdg_yyx@foxmail.com
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new TimeServerHandler());
    }
}