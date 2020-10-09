package com.im.sky.spring.model;

import com.im.sky.spring.annotation.RPCBean;

/**
 * @author jiangchangwei
 * @date 2020-9-15 下午 5:56
 **/
@RPCBean(name = "people")
public class People {

    public void say() {
        System.out.println("hello, world!");
    }
}
