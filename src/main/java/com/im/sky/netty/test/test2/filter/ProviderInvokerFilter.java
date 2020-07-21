package com.im.sky.netty.test.test2.filter;

import com.im.sky.netty.test.test2.config.ProviderConfig;
import com.im.sky.netty.test.test2.msg.Invocation;
import com.im.sky.netty.test.test2.msg.MessageBuilder;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 4:56
 **/
@Slf4j
public class ProviderInvokerFilter<T> implements  Filter {

    private final ProviderConfig<T> providerConfig;

    private final T ref;

    public ProviderInvokerFilter(ProviderConfig<T> providerConfig) {
        this.providerConfig = providerConfig;
        this.ref = providerConfig.getRef();
    }

    @Override
    public ResponseMessage invoke(RequestMessage request) {
        Invocation invocation = request.getInvocationBody();
        ResponseMessage responseMessage = MessageBuilder.buildResponse(request);
        try {
            Object result = reflectInvoke(ref, invocation);
            responseMessage.setResponse(result);
        }catch (Exception e) {
            responseMessage.setException(e);
        }
        return null;
    }

    private Object reflectInvoke(Object impl, Invocation invocation) throws NoSuchMethodException,
            ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Method method = ReflectionUtils.getMethod(invocation.getClazzName(), invocation.getMethodName(),
                invocation.getArgsType());
        return method.invoke(impl, invocation.getArgs());
    }
}
