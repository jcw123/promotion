package com.im.sky.lock.juc.aqs.test;

import com.im.sky.lock.juc.aqs.CountDownLatch;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 3:13
 **/
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(2);

        Thread t1 = new Thread(() -> {
            System.out.println("啦啦啦，德玛西亚");
            latch.countDown();
        });

        Thread t2 = new Thread(() -> {
            System.out.println("你的剑就说我的剑");
            latch.countDown();
        });
        t1.start();
        t2.start();
        System.out.println("main start");
        latch.await();
        System.out.println("main end");
    }
}
