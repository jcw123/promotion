package com.im.sky.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @date 2020-11-4 下午 6:36
 **/
public class LimiterTest {

    public static void main(String[] args) {
        testSmoothWarmUp();
    }

    private static void testSmoothBurst() {
        RateLimiter limiter = RateLimiter.create(10);
        while(true) {
            limiter.acquire();
            System.out.println(System.currentTimeMillis() % 10000);
        }
    }

    private static void testSmoothWarmUp() {
        RateLimiter limiter = RateLimiter.create(10, 2, TimeUnit.SECONDS);
        int i = 0;
        long start;
        while(i++ < 30) {
            start = System.currentTimeMillis();
            limiter.acquire(2);
            if(i == 20) {
                try {
                    Thread.sleep(2000);
                }catch (Exception e) {

                }
            }
            System.out.println(System.currentTimeMillis()  - start);
        }
    }
}
