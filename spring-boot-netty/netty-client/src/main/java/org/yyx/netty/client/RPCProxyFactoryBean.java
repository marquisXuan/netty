package org.yyx.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.yyx.netty.entity.MethodInvokeMeta;
import org.yyx.netty.rpc.util.WrapMethodUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理类
 * <p>
 *
 * @author 叶云轩 contact by marquis_xuan@163.com
 * @date 2018/11/1 - 15:49
 */
public class RPCProxyFactoryBean extends AbstractFactoryBean<Object> implements InvocationHandler {
    /**
     * RPCProxyFactoryBean 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RPCProxyFactoryBean.class);
    /**
     * 远程服务接口
     */
    private Class interfaceClass;
    /**
     * netty客户端
     */
    private NettyClient nettyClient;

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    /**
     * 创建实例的方法
     *
     * @return 由工厂创建的实例
     */
    @Override
    protected Object createInstance() {
        LOGGER.info("[代理工厂] 初始化代理Bean : {}", interfaceClass);
        // 返回代理类
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    /**
     * 动态调用方法的方法
     * 该方法不会显示调用
     *
     * @param proxy  被代理的实例
     * @param method 调用的方法
     * @param args   参数列表
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        LOGGER.info("{} -> [准备进行远程服务调用] ", this.getClass().getName());
        LOGGER.info("{} -> [封装调用信息] ", this.getClass().getName());
        final MethodInvokeMeta methodInvokeMeta = WrapMethodUtils.readMethod(interfaceClass, method, args);
        LOGGER.info("{} -> [远程服务调用封装完毕] 调用接口 -> {}\n调用方法 -> {}\n参数列表 -> {} \n 参数类型 -> {}" +
                        "\n 返回值类型 -> {}", this.getClass().getName(), methodInvokeMeta.getInterfaceClass(), methodInvokeMeta.getMethodName()
                , methodInvokeMeta.getArgs(), methodInvokeMeta.getParameterTypes(), methodInvokeMeta.getReturnType());
        try {
            // 真正开始使用netty进行通信的方法
            // todo 修改的地方将netty连接时机修改到循环外，不要每次都进行连接
            return nettyClient.remoteCall(methodInvokeMeta, 0);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }
}