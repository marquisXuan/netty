package org.yyx.netty.server.adapter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yyx.netty.entity.MethodInvokeMeta;
import org.yyx.netty.server.dispatcher.RequestDispatcher;

import javax.annotation.Resource;

/**
 * NettyServer通道适配器
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/7/9 - 上午9:39
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

    /**
     * 服务器接收到消息时进行进行的处理
     *
     * @param channelHandlerContext
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        // 转换为MethodInvokeMeta
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        LOGGER.info("{} -> [客户端信息] \n 方法名  - > {} \n 参数列表  -> {} \n " +
                        "返回值  ->  {} ", this.getClass().getName(), invokeMeta.getMethodName(), invokeMeta.getArgs()
                , invokeMeta.getReturnType());
        // 具体的处理类
        this.dispatcher.dispatcher(channelHandlerContext, invokeMeta);
    }
}
