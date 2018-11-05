package org.yyx.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.entity.MethodInvokeMeta;

/**
 * NettyClient
 * <p>
 * create by 叶云轩 at 2018/3/3-下午2:07
 * contact by tdg_yyx@foxmail.com
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:30
 */
public class NettyClient {
    /**
     * NettyClient 日志控制器
     * Create by 叶云轩 at 2018/3/3 下午2:08
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);
    /**
     * 初始化Bootstrap实例， 此实例是netty客户端应用开发的入口
     */
    private Bootstrap bootstrap;
    /**
     * 工人线程组
     */
    private EventLoopGroup worker;
    /**
     * 远程端口
     */
    private int port;
    /**
     * 远程服务器url
     */
    private String url;
    /**
     * 默认重连机制为10交
     */
    private int MAX_RETRY_TIMES = 10;

    /**
     * 有参构造
     *
     * @param url  远程服务器url
     * @param port 远程端口
     */
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

    /**
     * 真正远程调用的方法
     *
     * @param methodInvokeMeta 封装的远程服务信息
     * @param retry            重连次数
     * @return 调用结果
     * @throws Exception 会出现异常
     */
    public Object remoteCall(final MethodInvokeMeta methodInvokeMeta, int retry) throws Exception {
        CustomChannelInitializerClient customChannelInitializer = new CustomChannelInitializerClient(methodInvokeMeta);
        bootstrap.handler(customChannelInitializer);
        LOGGER.info("{} -> [准备进行netty通信] ", this.getClass().getName());
        try {
            ChannelFuture sync = bootstrap.connect(url, port).sync();
            sync.channel().closeFuture().sync();
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
                return remoteCall(methodInvokeMeta, retry);
            }
        }
        Object response;
        try {
            response = customChannelInitializer.getResponse();
        } catch (Exception e) {
            throw e;
        }
        LOGGER.info("{} -> [response] {}", this.getClass().getName(), response);
        return response;
    }
}
