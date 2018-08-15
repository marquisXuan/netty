package org.yyx.netty.rpc.util;


import org.yyx.netty.entity.MethodInvokeMeta;

import java.lang.reflect.Method;

/**
 * <p>
 * create by 叶云轩 at 2018/3/3-下午2:10
 * contact by tdg_yyx@foxmail.com
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:27
 */
public class WrapMethodUtils {
    /**
     * 获取 method的元数据信息
     *
     * @param interfaceClass
     * @param method
     * @param args
     * @return
     */
    public static MethodInvokeMeta readMethod(Class interfaceClass, Method method, Object[] args) {
        MethodInvokeMeta mim = new MethodInvokeMeta();
        mim.setInterfaceClass(interfaceClass);
        mim.setArgs(args);
        mim.setMethodName(method.getName());
        mim.setReturnType(method.getReturnType());
        Class<?>[] parameterTypes = method.getParameterTypes();
        mim.setParameterTypes(parameterTypes);
        return mim;
    }
}
