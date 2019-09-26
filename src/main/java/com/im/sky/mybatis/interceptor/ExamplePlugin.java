package com.im.sky.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * @Author: jiangcw
 * @Date: 2019-9-16 下午 10:41
 * @Version 1.0
 */
@Intercepts({
        @Signature(type= Executor.class, method = "update", args={MappedStatement.class, Object.class})
})
public class ExamplePlugin implements Interceptor {

    Properties properties = new Properties();

    public ExamplePlugin() {
        System.out.println("enter ExamplePlugin");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("pre");
        Object o = invocation.proceed();
        System.out.println("post");
        return o;
    }

    @Override
    public Object plugin(Object target) {
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
