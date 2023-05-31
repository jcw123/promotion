package com.im.sky.javacore.spi;

import java.util.ServiceLoader;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-03 21:12
 **/
public class ServiceLoaderTest {

    public static void main(String[] args) {
        ServiceLoader<A> list = ServiceLoader.load(A.class);
        for (A a : list) {
            a.say();
        }
    }
}
