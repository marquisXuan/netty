package org.yyx.netty.study.time.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * create by 叶云轩 at 2018/4/11-下午6:33
 * contact by tdg_yyx@foxmail.com
 */
public class TimeServer {
    /*
     NioEventLoopGroup本质是一个线程组，包含一组NIO线程专门用于网络事件的处理
     一个用于服务端接受客户端的连接
     一个用于进行SocketChannel的网络读写
     */

    /**
     * TimeServer 日志控制器
     * Create by 叶云轩 at 2018/4/12 上午11:26
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeServer.class);

    /**
     * 绑定端口
     *
     * @param port 端口号
     * @throws Exception 异常
     */
    public void bind(int port) throws Exception {
        LOGGER.info("--- [绑定端口] {}", port);
        // 声明Boss线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 声明Worker线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            LOGGER.info("--- [启动NIO] ");
            // Netty用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 将两个NIO线程组传递到ServerBootStrap中
            bootstrap.group(bossGroup, workerGroup)
                    // NioServerSocketChannel 相当于NIO中的ServerSocketChannel类
                    .channel(NioServerSocketChannel.class)
                    // 配置NioServerSocketChannel的TCP参数 backlog设置为1024
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 绑定I/O事件处理类
                    .childHandler(new ChildChannelHandler());
            // 绑定端口，同步等待成功
            // channelFuture 相当于JDK的java.util.concurrent.Future用于异步操作通知回调
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
