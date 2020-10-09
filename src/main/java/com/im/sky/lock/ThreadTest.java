package com.im.sky.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-8-16 下午 3:25
 **/
public class ThreadTest {
    public static void main(String[] args) throws Exception {
        AtomicInteger count = new AtomicInteger();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    System.out.println("hello, world");
                    count.incrementAndGet();
                    try {
//                    Thread.yield();
                        Thread.sleep(1);
//                    Thread.dumpStack();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setPriority(10);
        t.start();
        t.join(1000 * 60 * 2);
        t.stop();
        System.out.println(count.get());
    }
}
