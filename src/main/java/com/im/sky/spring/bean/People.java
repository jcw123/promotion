package com.im.sky.spring.bean;

/**
 * @Author: jiangcw
 * @Date: 2019-9-14 上午 10:40
 * @Version 1.0
 */
public class People implements IPeople {

    @Override
    public void say() {
        System.out.println("说人话");
    }

    @Override
    public void cry() {
        System.out.println("痛苦才会哭");
    }
}
