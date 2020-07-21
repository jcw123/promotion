package com.im.sky.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @date 2020-5-20 下午 4:40
 *
 * 令牌限流
 **/
public class TokenLimit {

    public static void main(String[] args) throws Exception{
//        RateLimiter rt = RateLimiter.create(5, 5, TimeUnit.SECONDS);
        RateLimiter rt = RateLimiter.create(2);
        while(true) {
            rt.acquire(5);
            System.out.println("111");
            try {

            }catch (Exception e) {}
        }
    }
}
