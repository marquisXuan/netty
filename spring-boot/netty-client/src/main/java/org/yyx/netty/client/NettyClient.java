package org.yyx.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.rpc.entity.MethodInvokeMeta;

/**
 * NettyClient
 * <p>
 * create by 叶云轩 at 2018/3/3-下午2:07
 * contact by tdg_yyx@foxmail.com
 */
public class NettyClient {
    /**
     * NettyClient 日志控制器
     * Create by 叶云轩 at 2018/3/3 下午2:08
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);
    private Bootstrap bootstrap;
    private EventLoopGroup worker;
    private int port;
    private String url;
    private int MAX_RETRY_TIMES = 10;

    public NettyClient(String url, int port) {
        this.url = url;
        this.port = port;
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
    }

    public void close() {
        LOGGER.info("关闭资源");
        worker.shutdownGracefully();
    }

    public Object remoteCall(final MethodInvokeMeta cmd, int retry) {
        try {
            CustomChannelInitializerClient customChannelInitializer = new CustomChannelInitializerClient(cmd);
            bootstrap.handler(customChannelInitializer);
            ChannelFuture sync = bootstrap.connect(url, port).sync();
            sync.channel().closeFuture().sync();
            Object response = customChannelInitializer.getResponse();
            return response;
        } catch (InterruptedException e) {
            retry++;
            if (retry > MAX_RETRY_TIMES) {
                throw new RuntimeException("调用Wrong");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                LOGGER.info("第{}次尝试....失败", retry);
                return remoteCall(cmd, retry);
            }
        }
    }
}
