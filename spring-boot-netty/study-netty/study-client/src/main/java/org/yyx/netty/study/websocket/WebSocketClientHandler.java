package org.yyx.netty.study.websocket;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * create by 叶云轩 at 2018/5/17-下午6:06
 * contact by tdg_yyx@foxmail.com
 */
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * WebSocketClientHandler 日志控制器
     * Create by 叶云轩 at 2018/5/17 下午6:10
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClientHandler.class);

    private final WebSocketClientHandshaker webSocketClientHandshaker;
    private ChannelPromise handshakeFuture;

    WebSocketClientHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    /**
     * 异常
     *
     * @param channelHandlerContext channelHandlerContext
     * @param cause                 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [exception]: {}\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", cause.getMessage());
        channelHandlerContext.close();
    }

    /**
     * 当客户端主动链接服务端的链接后，调用此方法
     *
     * @param channelHandlerContext ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [建立连接]\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓");

        Channel channel = channelHandlerContext.channel();
        // 握手
        webSocketClientHandshaker.handshake(channel);
    }

    /**
     * 与服务端断开连接时
     *
     * @param channelHandlerContext channelHandlerContext
     */
    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        WebSocketUsers.remove(channel);
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [断开连接]：client [{}]\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", channel.remoteAddress());

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

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        // 获取通道
        Channel channel = channelHandlerContext.channel();
        // 如果没有握手完成进行握手
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            webSocketClientHandshaker.finishHandshake(channel, (FullHttpResponse) msg);
            LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                    "\t├ [握手成功]\n" +
                    "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓");
            handshakeFuture.setSuccess();
            // 将当前登陆用户保存起来
            WebSocketUsers.put("client1-" + getUserNameInPath(), channel);
            return;
        }
        channelHandlerContext.flush();

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;

        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) frame;
            LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                    "\t├ [服务器响应消息]: {}\n" +
                    "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", textWebSocketFrame.text());
            WebSocketMessage webSocketMessage = new WebSocketMessage();
            webSocketMessage.setHeader(WebSocketMessage.Type.send_user);
            webSocketMessage.setAccept("yyx");
            webSocketMessage.setContent("Hello I'm YeYunXuan");
            String string = JSONObject.toJSONString(webSocketMessage);
            WebSocketUsers.sendMessageToUser("client1-YeYunXuan", string);
        }
    }

    /**
     * 获取登陆用户
     *
     * @return 用户名
     */
    private String getUserNameInPath() {
        String path = webSocketClientHandshaker.uri().getPath();
        int i = path.lastIndexOf("/");
        return path.substring(i + 1, path.length());
    }
}

