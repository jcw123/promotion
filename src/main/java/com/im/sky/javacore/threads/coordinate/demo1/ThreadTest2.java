package com.im.sky.javacore.threads.coordinate.demo1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangchangwei
 * @date 2020-10-13 下午 2:35
 **/
public class ThreadTest2 {

    private static int count = 0;

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition cA = lock.newCondition();

    private static final Condition cB = lock.newCondition();

    private static final Condition cC = lock.newCondition();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
           while(true) {
               lock.lock();
               while(count % 3 != 0) {
                   try {
                       cA.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               System.out.println("t1:" + count);
               count++;
               cB.signal();
               lock.unlock();
           }
        });
        Thread t2 = new Thread(() -> {
            while(true) {
                lock.lock();
                while(count % 3 != 1) {
                    try {
                        cB.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2:" + count);
                count++;
                cC.signal();
                lock.unlock();
            }
        });
        Thread t3 = new Thread(() -> {
            while(true) {
                lock.lock();
                while(count % 3 != 2) {
                    try {
                        cC.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t3:" + count);
                count++;
                cA.signal();
                lock.unlock();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
