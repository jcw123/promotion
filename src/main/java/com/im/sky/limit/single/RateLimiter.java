package com.im.sky.limit.single;

/**
 * @author jiangchangwei
 * @date 2020-5-25 下午 7:48
 *
 * 令牌限流
 **/
public class RateLimiter {

    private static com.google.common.util.concurrent.RateLimiter rateLimiter = com.google.common.util.concurrent.RateLimiter.create(10);

    public static boolean isLimit() {
        return !rateLimiter.tryAcquire();
    }
}
