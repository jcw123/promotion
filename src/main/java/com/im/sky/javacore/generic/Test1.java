package com.im.sky.javacore.generic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @date 2020-8-26 下午 2:10
 **/
public class Test1 {

    private static Map<String, Object> context = new HashMap<>();

    public static void main(String[] args) {
        Map<String, String> map  = new HashMap<>();
        map.put("test1", "test1");
        test(map);
    }

    public static void test(Map<? extends String, ?> map) {
        context.putAll(map);
    }
}
