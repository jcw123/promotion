package com.im.sky.javacore.thread;

import org.junit.Test;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-20 20:56
 **/
public class VolatileTest {

    private volatile boolean start = false;

    @Test
    public void test() throws Exception {
        for(int i = 0; i < 100; i++) {
            new Thread(() -> {
                if(!start) {
                    try {
                        Thread.sleep(100);
                    }catch (Exception e) {

                    }
                    start = true;
                    System.out.println("i am start");
                }
            }).start();
        }
        Thread.sleep(5 * 1000);
    }
}
