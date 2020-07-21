package com.im.sky.limit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-5-20 下午 5:02
 *
 * 漏桶限流
 **/
public class LeakyBucketLimit {

    private static LoadingCache<String, AtomicInteger> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, AtomicInteger>() {
                @Override
                public AtomicInteger load(String key) throws Exception {
                    return new AtomicInteger(0);
                }
            });

    public static void main(String[] args) throws Exception {
        int count = 100;
        for(int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    if(cache.get("test").get() > 10) {
                        System.out.println("被限流");
                    }else {
                        cache.get("test").getAndIncrement();
                        System.out.println("正常访问");
                    }
                }catch (Exception ignored){}
            }).start();
        }
    }
}
