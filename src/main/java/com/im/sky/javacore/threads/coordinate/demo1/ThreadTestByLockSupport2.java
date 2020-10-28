package com.im.sky.javacore.threads.coordinate.demo1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jiangchangwei
 * @date 2020-10-12 下午 8:01
 **/
public class ThreadTestByLockSupport2 {

    public static void main(String[] args) {
        Thread t1 = null;
        Thread t2 = null;
        Thread mainThread = Thread.currentThread();
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger count  = new AtomicInteger();
        t1 = new Thread(() -> {
            while(true) {
                LockSupport.park();
                int c = count.getAndIncrement();
                System.out.println("t1:" + c);
                LockSupport.unpark(mainThread);
            }
        });
        t1.start();
        t2 = new Thread(() -> {
            while(true) {
                LockSupport.park();
                int c = count.getAndIncrement();
                System.out.println("t2:" + c);
                LockSupport.unpark(mainThread);
            }
        });
        t2.start();
        int i = 0;
        int j = 0;
        while(j++ < 10) {
            if(i == 0) {
                LockSupport.unpark(t1);
                i = 1;
            }else {
               LockSupport.unpark(t2);
               i = 0;
            }
            LockSupport.park();
        }
    }
}
