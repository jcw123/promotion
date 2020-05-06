package com.im.sky.javacore.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 3:26
 **/
public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before invoke");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("after invoke");
        return result;
    }
}
