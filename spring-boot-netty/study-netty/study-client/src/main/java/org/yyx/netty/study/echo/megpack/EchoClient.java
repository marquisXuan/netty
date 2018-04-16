package org.yyx.netty.study.echo.megpack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.yyx.netty.study.msgpack.MsgPackDecoder;
import org.yyx.netty.study.msgpack.MsgPackEncoder;

/**
 * EchoClient
 * <p>
 * create by 叶云轩 at 2018/4/12-下午4:28
 * contact by tdg_yyx@foxmail.com
 */
public class EchoClient {

    /**
     * 客户端连接方法
     *
     * @param port 端口号
     * @param host 主机名
     * @throws Exception 异常
     */
    public void connect(int port, String host, int sendNumber) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("msg_decoder", new MsgPackDecoder());
                            ch.pipeline().addLast("msg_encoder", new MsgPackEncoder());
                            ch.pipeline().addLast(new EchoClientHandler(sendNumber));
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            group.shutdownGracefully();
        }
    }
}
