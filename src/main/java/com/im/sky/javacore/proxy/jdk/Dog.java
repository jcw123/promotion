package com.im.sky.javacore.proxy.jdk;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 2:45
 **/
public class Dog implements Animal {

    public void eat() {
        System.out.println("dog eat meat");
    }

    public void run() {
        System.out.println("dog run");
    }

    @Override
    public void life() {
        System.out.println("dog life");
    }
}
