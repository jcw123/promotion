package com.im.sky.javacore.threads.coordinate.demo1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangchangwei
 * @date 2020-9-17 上午 11:16
 *
 * 有一个主线程S，和三个一般线程A、B、C；
 * 让三个线程执行按照如下方式执行 S、A、S、B、S、C循环方式执行；
 *
 * 一个Condition就是一个条件队列，通过调用await方法就相当于将一个新的线程放到Condition的
 * 条件队列中，而调用signal方法就相当于通知条件队列中的方法
 **/
public class ThreadTest {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition SCondition = lock.newCondition();

    public static void main(String[] args) {
        Condition[] conditions = new Condition[3];
        CountDownLatch latch = new CountDownLatch(3);
        for(int i = 0; i < 3; i++) {
            conditions[i] = lock.newCondition();
        }
        Thread S = new Thread(() -> {
            int i = 0;
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            while(true) {
                System.out.println("S execute");
                conditions[i++ % 3].signal();
                SCondition.awaitUninterruptibly();
            }
        });

        Thread A = new Thread(() -> {
            lock.lock();
            latch.countDown();
            while(true) {
                conditions[0].awaitUninterruptibly();
                System.out.println("A execute");
                SCondition.signal();
            }
        });

        Thread B = new Thread(() -> {
            lock.lock();
            latch.countDown();
            while(true) {
               conditions[1].awaitUninterruptibly();
               System.out.println("B execute");
               SCondition.signal();
           }
        });

        Thread C = new Thread(() -> {
            lock.lock();
            latch.countDown();
            while(true) {
               conditions[2].awaitUninterruptibly();
               System.out.println("C execute");
               SCondition.signal();
           }
        });
        S.start();
        A.start();
        B.start();
        C.start();
    }

}
