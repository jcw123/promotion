package com.im.sky.limit.single;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-7-21 下午 6:17
 **/
public class CounterLimiter {

    public static void main(String[] args) {
        CounterLimiter limiter = new CounterLimiter();
        for(int i = 0; i < 200; i++) {
            if(limiter.isLimit()) {
                System.out.println("limit");
            }else {
                System.out.println("no limit");
            }
        }
    }

    private final long interval;

    private final int maxLimit;

    private final AtomicInteger counter;

    private long lastStartTime = -1;

    public CounterLimiter() {
        this.interval = 10;
        this.maxLimit = 100;
        this.counter = new AtomicInteger();
    }

    public CounterLimiter(long interval, int maxLimit) {
        this.interval = interval;
        this.maxLimit = maxLimit;
        this.counter = new AtomicInteger();
    }

    public boolean isLimit() {
        long current = System.currentTimeMillis();
        if(lastStartTime == -1) {
            lastStartTime = current;
            return counter.incrementAndGet() > maxLimit;
        }
        if(current > lastStartTime + interval) {
            lastStartTime = current;
            return false;
        }
        return counter.incrementAndGet() > maxLimit;
    }
}
