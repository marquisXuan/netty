package org.yyx.netty.study.websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

/**
 * webSocketClient
 * <p>
 * create by 叶云轩 at 2018/5/17-下午6:04
 * contact by tdg_yyx@foxmail.com
 */
public class WebSocketClient {

    public void connect(int port, String host, String userName) throws Exception {
        // 配置客户端的NIO线程组
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            // Client辅助启动类
            Bootstrap bootstrap = new Bootstrap();
            // 配置bootstrap
            WebSocketClientHandler webSocketClientHandler = new WebSocketClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(new URI("ws://172.0.0.1:9999/websocket/" + userName)
                            , WebSocketVersion.V13, null, false, new DefaultHttpHeaders())
            );
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new WebSocketHandlerClient(webSocketClientHandler));
            // 发起异步连接操作  同步方法待成功
            Channel channel = bootstrap.connect(host, port).sync().channel();
            // 等待客户端链路关闭
            channel.closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            clientGroup.shutdownGracefully();
        }


    }
}
