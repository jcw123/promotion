package com.im.sky.mybatis;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Reflector;

/**
 * @Author: jiangcw
 * @Date: 2019-9-17 下午 6:38
 * @Version 1.0
 */
public class ExampleReflectorFactory extends DefaultReflectorFactory {

    public ExampleReflectorFactory() {
        super();
        System.out.println("enter ExampleReflectorFactory");
    }

    @Override
    public boolean isClassCacheEnabled() {
        return super.isClassCacheEnabled();
    }

    @Override
    public void setClassCacheEnabled(boolean classCacheEnabled) {
        super.setClassCacheEnabled(classCacheEnabled);
    }

    @Override
    public Reflector findForClass(Class<?> type) {
        return super.findForClass(type);
    }
}
