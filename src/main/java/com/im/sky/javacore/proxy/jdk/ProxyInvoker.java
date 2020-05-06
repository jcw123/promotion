package com.im.sky.javacore.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 2:44
 **/
public class ProxyInvoker {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        testJdkProxy();
    }

    public static void testJdkProxy() {
        Dog dog = new Dog();
        JdkProxy jdkProxy = new JdkProxy(dog);
        Animal dog2 = (Animal)Proxy.newProxyInstance(Dog.class.getClassLoader(), dog.getClass().getInterfaces(), jdkProxy);
        dog2.life();

    }
}
