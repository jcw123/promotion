package com.im.sky.lock.juc.aqs.test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 4:08
 **/
public class LockSupportTest {

    private static ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue();

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                say();
            });
            t.start();
        }
        Thread t2 = new Thread(() -> {
            while (true) {
                Thread t = queue.poll();
                if(t != null) {
                    LockSupport.unpark(t);
                }
            }
        });
        t2.start();
    }

    private static void say() {
        System.out.println("再见");
        queue.add(Thread.currentThread());
        LockSupport.park();
        System.out.println("再也不见");
    }
}
