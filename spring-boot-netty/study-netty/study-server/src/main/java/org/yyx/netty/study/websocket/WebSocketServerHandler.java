package org.yyx.netty.study.websocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>
 * create by 叶云轩 at 2018/5/11-上午11:49
 * contact by tdg_yyx@foxmail.com
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * WebSocketServerHandler 日志控制器
     * Create by 叶云轩 at 2018/5/11 上午11:50
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private WebSocketServerHandshaker socketServerHandShaker;

    /**
     * 异常
     *
     * @param channelHandlerContext channelHandlerContext
     * @param cause                 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        channelHandlerContext.close();
    }

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        // 使用一个结构存储通道
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                        "\t├ [建立连接]: client [{}]\n" +
                        "\t├ [当前在线人数]: {}\n" +
                        "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", channelHandlerContext.channel().remoteAddress()
                , WebSocketUsers.getUSERS().size() + 1);
    }

    /**
     * 与客户端断开连接时
     *
     * @param channelHandlerContext channelHandlerContext
     */
    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [断开连接]：client [{}]\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", channel.remoteAddress());
        WebSocketUsers.remove(channel);
        ConcurrentMap<String, Channel> users = WebSocketUsers.getUSERS();
        LOGGER.info("\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓");
        for (String s : users.keySet()) {
            LOGGER.info(
                    "\t├ [当前在线]: {}", s);
        }
        LOGGER.info("\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓");
    }

    /**
     * 读完之后调用的方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    /**
     * 接收客户端发送的消息
     *
     * @param ctx ChannelHandlerContext
     * @param msg 消息
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [收到客户端消息类型]: {} - {}\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", msg.getClass(), msg.toString());
        // 传统http接入 第一次需要使用http建立握手
        if (msg instanceof FullHttpRequest) {
            handlerHttpRequest(ctx, (FullHttpRequest) msg);
            ctx.channel().write(new TextWebSocketFrame("连接成功"));
        }
        // WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }

    }

    /**
     * 第一次握手
     *
     * @param channelHandlerContext channelHandlerContext
     * @param req                   请求
     */
    private void handlerHttpRequest(ChannelHandlerContext channelHandlerContext, FullHttpRequest req) {

        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory
                = new WebSocketServerHandshakerFactory("ws://localhost:9999/websocket/{uri}",
                null, false);
        String uri = req.uri();
        String[] split = uri.split("/");
        String userName = split[2];
        // 加入在线用户
        WebSocketUsers.put(userName, channelHandlerContext.channel());
        socketServerHandShaker = wsFactory.newHandshaker(req);
        if (socketServerHandShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
        } else {
            socketServerHandShaker.handshake(channelHandlerContext.channel(), req);
        }
    }

    /**
     * webSocket处理逻辑
     *
     * @param channelHandlerContext channelHandlerContext
     * @param frame                 webSocketFrame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext channelHandlerContext, WebSocketFrame frame) throws IOException {
        Channel channel = channelHandlerContext.channel();
        // region 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                    "\t├ [关闭与客户端的链接]\n" +
                    "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓");
            socketServerHandShaker.close(channel, (CloseWebSocketFrame) frame.retain());
            return;
        }
        // endregion
        // region 判断是否是ping消息
        if (frame instanceof PingWebSocketFrame) {
            LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                    "\t├ [Ping消息]\n" +
                    "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓");
            channel.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // endregion
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
            WebSocketMessage webSocketMessage = JSONObject.parseObject(text, WebSocketMessage.class);
            String accept = webSocketMessage.getAccept();
            WebSocketUsers.sendMessageToUser(accept, webSocketMessage.getContent());
        }
        if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf content = binaryWebSocketFrame.content();
            LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                    "\t├ [二进制数据]:{}\n" +
                    "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", content);
            final int length = content.readableBytes();
            final byte[] array = new byte[length];
            content.getBytes(content.readerIndex(), array, 0, length);
            MessagePack messagePack = new MessagePack();
            List<Object> list = new ArrayList<>();
            list.add(messagePack.read(array));
            for (Object o : list) {
                LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                        "\t├ [解码数据]: {}\n" +
                        "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", o);
            }

        }
        // 非文本消息处理方式
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format(
                    "%s 暂不支持非文本消息", frame.getClass().getName()
            ));
        }
    }
}
