package com.im.sky.javacore.classloader;

import java.lang.reflect.Method;

/**
 * @author jiangchangwei
 * @date 2020-7-2 下午 6:46
 **/
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        DefaultClassloader classloader = new DefaultClassloader("d:/tmp/cls/");
        Class clazz = classloader.loadClass("People");
        Method method = clazz.getMethod("say", new Class[0]);
        DefaultClassloader classloader2 = new DefaultClassloader("d:/tmp/cls/");
        ObjectFactory factory = new ObjectFactory(classloader2);
        Object o = factory.buildInstance("People");
        method.invoke(o, new Object[0]);

    }

}
