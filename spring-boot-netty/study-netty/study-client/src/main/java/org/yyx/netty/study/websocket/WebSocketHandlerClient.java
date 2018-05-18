package org.yyx.netty.study.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.yyx.netty.study.codec.msgpack.MsgPackDecoder;
import org.yyx.netty.study.codec.msgpack.MsgPackEncoder;

/**
 * webSocketChildHandler
 * <p>
 * create by 叶云轩 at 2018/5/15-下午4:54
 * contact by tdg_yyx@foxmail.com
 */
public class WebSocketHandlerClient extends ChannelInitializer<SocketChannel> {

    private WebSocketClientHandler webSocketClientHandler;

    public WebSocketHandlerClient(WebSocketClientHandler webSocketClientHandler) {
        this.webSocketClientHandler = webSocketClientHandler;
    }

    /**
     * 初始化Channel
     *
     * @param socketChannel socketChannel
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 将请求与应答消息编码或者解码为HTTP消息
        pipeline.addLast(new HttpClientCodec());
        // 将http消息的多个部分组合成一条完整的HTTP消息
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        // 客户端Handler
        pipeline.addLast("handler", webSocketClientHandler);
    }
}
