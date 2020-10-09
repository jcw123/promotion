package com.im.sky.limit.single;

import com.google.common.cache.*;
import com.im.sky.redis.zset.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-5-25 下午 5:03
 **/
public  class AtomicIntegerLimit {

    public static void main(String[] args) {
       AtomicIntegerLimit limit  = new AtomicIntegerLimit(10, 5, TimeUnit.SECONDS);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for(int i = 0; i < 1; i++) {
            executor.execute(() -> {
                while (true) {
                    try {
                        if (limit.isLimit("test")) {
                            System.out.println("限流中");
                        } else {
                            System.out.println("未限流");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private final int maxLimit;

    private final LoadingCache<String, AtomicInteger> cache;

    public AtomicIntegerLimit(int maxLimit, long duration, TimeUnit unit) {
        if(maxLimit <= 0) {
            throw new IllegalArgumentException("illegal parameter maxLimit:" + maxLimit);
        }
        if(duration <= 0) {
            throw new IllegalArgumentException("illegal parameter duration:" + duration);
        }
        this.maxLimit = maxLimit;
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(duration, unit)
                .maximumSize(100000)
                .build(new CacheLoader<String, AtomicInteger>() {
                    @Override
                    public AtomicInteger load(String key) throws Exception {
                        return new AtomicInteger(0);
                    }
                });
    }

    // 5秒10次
    private boolean isLimit(String key) throws Exception {
        int v = cache.get(key).incrementAndGet();
        // v < 0是为了防止增长速度太多，查过int的表示范围
        return v < 0 || v > maxLimit;
    }
}
