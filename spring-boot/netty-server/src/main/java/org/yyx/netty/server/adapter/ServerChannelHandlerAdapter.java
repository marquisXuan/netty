package org.yyx.netty.server.adapter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yyx.netty.rpc.entity.MethodInvokeMeta;
import org.yyx.netty.server.dispatcher.RequestDispatcher;

import javax.annotation.Resource;

/**
 * NettyServer通道适配器
 * <p>
 * create by 叶云轩 at 2018/3/3-下午12:27
 * contact by tdg_yyx@foxmail.com
 */
@Component
@Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {
    /**
     * ServerChannelHandlerAdapter 日志控制器
     * Create by 叶云轩 at 2018/3/3 下午1:25
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerChannelHandlerAdapter.class);
    /**
     * 注入请求分排器
     */
    @Resource
    private RequestDispatcher dispatcher;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        // 屏蔽toString()方法
        if (invokeMeta.getMethodName().endsWith("toString()")
                && !"class java.lang.String".equals(invokeMeta.getReturnType().toString()))
            LOGGER.info("客户端传入参数 :{},返回值：{}",
                    invokeMeta.getArgs(), invokeMeta.getReturnType());
        dispatcher.dispatcher(ctx, invokeMeta);
    }
}
