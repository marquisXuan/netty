package org.yyx.netty.server.adapter;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
    private int lossConnectCount = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("{} -> [连接异常] {}通道异常，异常原因：{}", this.getClass().getName(),
                ctx.channel().id(), cause.getMessage());
        ctx.close();
    }

    /**
     * 服务器接收到消息时进行进行的处理
     *
     * @param channelHandlerContext channelHandlerContext
     * @param msg                   msg
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        if (msg instanceof String) {
            if ("ping-pong-ping-pong".equals(msg)) {
                LOGGER.info("{} -> [心跳监测] {}：通道活跃", this.getClass().getName(), channelHandlerContext.channel().id());
                // 心跳消息
                lossConnectCount = 0;
                return;
            }
        }
        // 转换为MethodInvokeMeta
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        LOGGER.info("{} -> [客户端信息] \n 方法名  - > {} \n 参数列表  -> {} \n " +
                        "返回值  ->  {} ", this.getClass().getName(), invokeMeta.getMethodName(), invokeMeta.getArgs()
                , invokeMeta.getReturnType());
        // 具体的处理类
        this.dispatcher.dispatcher(channelHandlerContext, invokeMeta);
    }

    /**
     * 触发器
     *
     * @param channelHandlerContext channelHandlerContext
     * @param evt
     * @throws Exception exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object evt) throws Exception {
        LOGGER.info("{} -> [已经有5秒中没有接收到客户端的消息了]", this.getClass().getName());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 2) {
                    LOGGER.info("{} -> [释放不活跃通道] {}", this.getClass().getName(), channelHandlerContext.channel().id());
                    channelHandlerContext.channel().close();
                }
            }
        } else {
            super.userEventTriggered(channelHandlerContext, evt);
        }
    }
}
