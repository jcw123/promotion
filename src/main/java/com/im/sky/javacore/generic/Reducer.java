package com.im.sky.javacore.generic;

import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-3-17 下午 12:12
 **/
public class Reducer<A, R> {

    public static <K, V, A extends Map<K, V>> Reducer<A, Map<K, V>> test() {
        return null;
    }
}
