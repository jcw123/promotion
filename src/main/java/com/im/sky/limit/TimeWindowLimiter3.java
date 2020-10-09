package com.im.sky.limit;

import com.im.sky.lock.juc.aqs.CountDownLatch;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-7-22 上午 9:47
 **/
public class TimeWindowLimiter3 {

    public static void main(String[] args) throws Exception {
        TimeWindowLimiter3 limiter3  = new TimeWindowLimiter3(5, 100);
        ExecutorService executorService = Executors.newFixedThreadPool(150);
        AtomicInteger limitCount = new AtomicInteger();
        AtomicInteger unLimitCount = new AtomicInteger();
        CountDownLatch latch = new CountDownLatch(150);
        Random random = new Random();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 150; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(random.nextInt(21) * 1000);
                }catch (Exception ignored) {}
                if(limiter3.isLimit("jiangcw")) {
                    limitCount.incrementAndGet();
                    System.out.println(System.currentTimeMillis() + ", limit");
                }else {
                    unLimitCount.incrementAndGet();
                    System.out.println(System.currentTimeMillis() + ", unLimit");
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("totalTime:" + (System.currentTimeMillis() - start));
        System.out.println("limitCount:" + limitCount.get() + ", unLimitCount:" + unLimitCount.get());
        executorService.shutdown();
    }

    // 单位是秒
    private long windowNanos;

    // 窗口内的最大值
    private int maxLimit;

    private Map<String, SortedSet<Long>> counter = new HashMap<>();

    public TimeWindowLimiter3(int windowSeconds, int maxLimit) {
        if (windowSeconds <= 0) {
            throw new IllegalArgumentException("Illegal windowSeconds parameter, must be larger than zero, current value:" + windowSeconds);
        }
        if (maxLimit <= 0) {
            throw new IllegalArgumentException("Illegal maxLimit parameter, must be larger than zero, current value:" + maxLimit);
        }
        this.windowNanos = (long)windowSeconds * 1000 * 1000 * 1000;
        System.out.println(this.windowNanos);
        this.maxLimit = maxLimit;
    }

    public synchronized boolean isLimit(String key) {
        SortedSet<Long> sortedSet = counter.computeIfAbsent(key, o -> new TreeSet<>());
        long current = System.nanoTime();
        sortedSet.removeIf((o) -> current - windowNanos - o > 0);
        sortedSet.add(current);
        System.out.println("size:" + sortedSet.size());
        return sortedSet.size() > maxLimit;
    }
}
