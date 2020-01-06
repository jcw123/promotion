package com.im.sky.lock.juc.aqs.test;

import java.util.concurrent.Semaphore;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 2:16
 **/
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        int i = 0;
        while(i++ < 3) {
            Thread t = new Thread(() -> {
                while(true) {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }finally {
                        semaphore.release();
                    }
                }
            });
            t.start();
        }

    }
}
