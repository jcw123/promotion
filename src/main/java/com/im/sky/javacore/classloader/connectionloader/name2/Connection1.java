package com.im.sky.javacore.classloader.connectionloader.name2;

import com.im.sky.javacore.classloader.connectionloader.Connection;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 4:37
 **/
public class Connection1 implements Connection {
    @Override
    public void mark() {
        System.out.println("name2.c1");
    }
}
