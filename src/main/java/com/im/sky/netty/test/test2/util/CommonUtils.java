package com.im.sky.netty.test.test2.util;

import java.util.concurrent.ConcurrentMap;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 4:08
 **/
public class CommonUtils {

    public static <K, V> V putToConcurrentMap(ConcurrentMap<K, V> map, K key, V value) {
        V old = map.putIfAbsent(key, value);
        return old != null ? old : value;
    }
}
