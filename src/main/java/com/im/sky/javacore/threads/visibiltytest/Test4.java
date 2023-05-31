package com.im.sky.javacore.threads.visibiltytest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-23 15:39
 **/
public class Test4 {

    private volatile boolean start;

    private int n;

    public static void main(String[] args) throws Exception {
        AtomicBoolean ss = new AtomicBoolean();

        Test4 test4 = new Test4();
        Thread t1 = new Thread(() -> {
            while(!test4.start) {
                System.out.println(test4.n);
            }
            System.out.println(test4.n);
        });
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(() -> {
            test4.n = 10;
            test4.start = true;
        });
        t2.start();
    }
}
