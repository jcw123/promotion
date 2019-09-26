package com.im.sky.mybatis;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.List;
import java.util.Properties;

/**
 * @Author: jiangcw
 * @Date: 2019-9-16 下午 10:47
 * @Version 1.0
 */
public class ExampleObjectFactory extends DefaultObjectFactory {

    public ExampleObjectFactory() {
        System.out.println("enter ExampleObjectFactory");
    }

    @Override
    public <T> T create(Class<T> type) {
        return super.create(type);
    }

    @Override
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return super.isCollection(type);
    }
}
