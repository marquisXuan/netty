package org.yyx.netty.rpc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.yyx.netty.entity.MethodInvokeMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:28
 */
public class RemoteMethodInvokeUtil implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Object processMethod(MethodInvokeMeta methodInvokeMeta) throws InvocationTargetException, IllegalAccessException {
        Class interfaceClass = methodInvokeMeta.getInterfaceClass();
        Object bean = applicationContext.getBean(interfaceClass);
        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        Method method = null;
        for (Method declaredMethod : declaredMethods) {
            if (methodInvokeMeta.getMethodName().equals(declaredMethod.getName())) {
                method = declaredMethod;
            }
        }
        Object invoke = method.invoke(bean, methodInvokeMeta.getArgs());
        return invoke;
    }

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        applicationContext = app;
    }
}
