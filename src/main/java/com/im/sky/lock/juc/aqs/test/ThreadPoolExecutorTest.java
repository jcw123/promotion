package com.im.sky.lock.juc.aqs.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 10:38
 **/
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
        executor.submit(() -> {
            while (true) {
                System.out.println("你好1");
                try { Thread.sleep(5000); } catch (Exception e) {}
            }
        });

        executor.submit(() -> {
            while (true) {
                System.out.println("你好2");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
            }
        });

        executor.submit(() -> {
            while(true) {
                System.out.println("你好3");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }

            }
        });

    }
}
