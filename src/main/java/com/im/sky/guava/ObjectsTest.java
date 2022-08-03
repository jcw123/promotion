package com.im.sky.guava;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ObjectsTest {

    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(100);
        limiter.tryAcquire(1, 2, TimeUnit.SECONDS);
        System.out.println("11".matches("\\d+"));
    }

    private static void test1() {
        String a1 = null;
        String a2 = "";
        Objects.equal(a1, a2);
        new HashMap<String, String>(){

        };
    }
}
