package com.im.sky.limit.single;

import com.google.common.cache.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-5-25 下午 5:03
 **/
public  class AtomicIntegerLimit {

    private static LoadingCache<String, AtomicInteger> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .maximumSize(100000)
            .build(new CacheLoader<String, AtomicInteger>() {
                @Override
                public AtomicInteger load(String key) throws Exception {
                    return new AtomicInteger(0);
                }
            });



    // 5秒10次
    private static boolean isLimit(String key) throws Exception {
        return cache.get(key).intValue() > 10;
    }
}
