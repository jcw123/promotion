package com.im.sky.javacore.proxy.jdk;

import org.apache.ibatis.reflection.Jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 2:15
 **/
public class JdkProxy implements InvocationHandler {

    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object object = method.invoke(target, args);
        System.out.println("after invoke");
        return object;
    }
}
