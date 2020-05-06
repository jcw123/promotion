package com.im.sky.javacore.reference;

import java.util.WeakHashMap;

/**
 * @author jiangchangwei
 * @date 2020-4-7 下午 7:00
 **/
public class WeakHashMapTest {

    public static void main(String[] args) throws Exception {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        map.put(new Object(), "test");
        System.gc();
        Thread.sleep(1000);
        System.out.println(map.size());
    }
}
