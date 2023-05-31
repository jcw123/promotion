package com.im.sky.javacore.threads.visibiltytest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-23 17:49
 **/
public class Test5 {

    private static volatile boolean start = false;

    public void start() {
        if(!start) {
            start = true;
            System.out.println("i am starting");
        }
    }
}
