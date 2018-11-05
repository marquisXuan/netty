package org.yyx.netty.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.entity.MethodInvokeMeta;

/**
 * <p>
 * create by 叶云轩 at 2018/3/3-下午2:08
 * contact by tdg_yyx@foxmail.com
 *
 * @author 叶云轩
 */
public class ClientChannelHandlerAdapter extends ChannelHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(ClientChannelHandlerAdapter.class);
    /**
     * 远程服务元信息
     */
    private MethodInvokeMeta methodInvokeMeta;
    /**
     * 用于将通信结果返回
     */
    private CustomChannelInitializerClient channelInitializerClient;

    public ClientChannelHandlerAdapter(MethodInvokeMeta methodInvokeMeta, CustomChannelInitializerClient channelInitializerClient) {
        this.methodInvokeMeta = methodInvokeMeta;
        this.channelInitializerClient = channelInitializerClient;
    }

    /**
     * 异常方法
     *
     * @param channelHandlerContext channelHandlerContext
     * @param cause                 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        logger.info("客户端出异常了,异常信息:{}", cause.getMessage());
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    /**
     * 通道连接后会自动进入此方法
     *
     * @param channelHandlerContext channelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        // 发送远程服务调用信息给服务端
        channelHandlerContext.writeAndFlush(methodInvokeMeta);
    }

    /**
     * 通道接收到消息时会进入此方法
     *
     * @param channelHandlerContext channelHandlerContext
     * @param msg                   服务端返回的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        // 将消息写给 channelInitializerClient
        channelInitializerClient.setResponse(msg);
    }
}
