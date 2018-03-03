package org.yyx.netty.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.netty.rpc.entity.MethodInvokeMeta;

/**
 * <p>
 * create by 叶云轩 at 2018/3/3-下午2:08
 * contact by tdg_yyx@foxmail.com
 */
public class ClientChannelHandlerAdapter extends ChannelHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(ClientChannelHandlerAdapter.class);
    private MethodInvokeMeta methodInvokeMeta;
    private CustomChannelInitializerClient channelInitializerClient;

    public ClientChannelHandlerAdapter(MethodInvokeMeta methodInvokeMeta, CustomChannelInitializerClient channelInitializerClient) {
        this.methodInvokeMeta = methodInvokeMeta;
        this.channelInitializerClient = channelInitializerClient;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("客户端出异常了,异常信息:{}", cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (methodInvokeMeta.getMethodName().endsWith("toString") && !"class java.lang.String".equals(methodInvokeMeta.getReturnType().toString()))
            logger.info("客户端发送信息参数:{},信息返回值类型：{}", methodInvokeMeta.getArgs(), methodInvokeMeta.getReturnType());
        ctx.writeAndFlush(methodInvokeMeta);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelInitializerClient.setResponse(msg);
    }
}
