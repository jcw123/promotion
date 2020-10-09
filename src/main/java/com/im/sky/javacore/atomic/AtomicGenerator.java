package com.im.sky.javacore.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-8-13 下午 2:30
 **/
public class AtomicGenerator {

    private static final AtomicInteger counter = new AtomicInteger();

    private static long max = 1024000;

    private static AtomicBoolean state = new AtomicBoolean();

    private static Long startTime;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                if(state.compareAndSet(false, true)) {
                    startTime = System.currentTimeMillis();
                }
                while(true) {
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    int v = counter.incrementAndGet();
                    if(v > max) {
                        System.out.println(v);
                        break;
                    }
                }
            });
            list.add(t);
            t.start();
        }
//        new Thread(()-> {
//            while(true) {
//
//            }
//        }, "running-alive").start();
        for(Thread t : list) {
            t.join();
        }
        System.out.println("totalTime:" + (System.currentTimeMillis() - startTime));
    }
}
