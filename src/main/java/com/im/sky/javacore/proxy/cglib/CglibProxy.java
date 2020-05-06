package com.im.sky.javacore.proxy.cglib;

import com.im.sky.javacore.proxy.jdk.Dog;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 3:31
 **/
public class CglibProxy {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\tmp");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dog.class);
        enhancer.setCallback(new MyMethodInterceptor());
        Dog dog = (Dog)enhancer.create();
        dog.life();
    }


}
