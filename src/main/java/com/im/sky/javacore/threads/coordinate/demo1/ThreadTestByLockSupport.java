package com.im.sky.javacore.threads.coordinate.demo1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jiangchangwei
 * @date 2020-10-12 下午 8:00
 **/
public class ThreadTestByLockSupport {
    private int flag = 1;

    private int flag2 = 2;

    private final Object MUTEX2 = new Object();

    private volatile int m = 1;

    private static final Object MUTEX = new Object();

    public static void main(String[] args) throws Exception {

        Thread mainThread = Thread.currentThread();

        AtomicInteger count = new AtomicInteger();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while(true) {
                    LockSupport.park();
                    int m = count.getAndIncrement();
                    System.out.println("t1:" + m);
                    LockSupport.unpark(mainThread);
                }
            }
        };
        t1.start();
        Thread t2 = new Thread() {
            @Override
            public void run() {
                while(true) {
                    LockSupport.park();
                    int m = count.getAndIncrement();
                    System.out.println("t2:" + m);
                    LockSupport.unpark(mainThread);
                }
            }
        };
        t2.start();
        int i = 0;
        Thread[] tArray = new Thread[] {t1, t2};
        int size = 0;
        while(true) {
            LockSupport.unpark(tArray[(i++) % 2]);
            LockSupport.park();
        }
    }

    public void testScene2() {
        flag = 2;
        m  = 2;
        flag2 = 3;
    }

    public void testScene1() throws Exception {
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    if (m == 1) {
                        if(flag != 1) {
                            break;
                        }
                    }
                }
                System.out.println("flag:" + flag);
            }).start();
        }
        Thread.sleep(1000);
//        synchronized (MUTEX) {
////            flag = 2;
////        }
        flag = 2;
    }

}