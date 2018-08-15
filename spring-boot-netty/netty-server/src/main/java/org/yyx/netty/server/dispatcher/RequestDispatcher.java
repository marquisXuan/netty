package org.yyx.netty.server.dispatcher;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yyx.netty.entity.MethodInvokeMeta;
import org.yyx.netty.entity.NullWritable;

import java.lang.reflect.Method;

/**
 * 请求分排器
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/7/9 - 上午9:37
 */
@Component
public class RequestDispatcher implements ApplicationContextAware {
    /**
     * Spring上下文
     */
    private ApplicationContext app;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.app = applicationContext;
    }

    /**
     * 发送
     *
     * @param channelHandlerContext channelHandlerContext
     * @param invokeMeta            invokeMeta
     */
    public void dispatcher(final ChannelHandlerContext channelHandlerContext, final MethodInvokeMeta invokeMeta) {
        ChannelFuture f = null;
        try {
            Class<?> interfaceClass = invokeMeta.getInterfaceClass();
            String name = invokeMeta.getMethodName();
            Object[] args = invokeMeta.getArgs();
            Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
            Object targetObject = app.getBean(interfaceClass);
            Method method = targetObject.getClass().getMethod(name, parameterTypes);
            Object obj = method.invoke(targetObject, args);
            if (obj == null) {
                f = channelHandlerContext.writeAndFlush(NullWritable.nullWritable());
            } else {
                f = channelHandlerContext.writeAndFlush(obj);
            }
            f.addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            f = channelHandlerContext.writeAndFlush(e.getMessage());
        } finally {
            if (f != null) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}
