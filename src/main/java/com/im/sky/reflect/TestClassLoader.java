package com.im.sky.reflect;

import java.lang.reflect.Method;

/**
 * @author jiangchangwei
 * @date 2020-1-10 下午 2:44
 **/
public class TestClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> clz = Class.forName("com.im.sky.reflect.People");
        Object o = clz.newInstance();
        Method method = clz.getMethod("live");
        for(int j = 0; j < 100; j++) {
            long total = 0;
            for (int i = 0; i < 100; i++) {
                long startTime = System.nanoTime();
                method.invoke(o);
                total += (System.nanoTime() - startTime);
            }
            System.out.println("average for reflect:" + total / 100);
            total = 0;
            People people = new People();
            for (int i = 0; i < 100; i++) {
                long startTime = System.nanoTime();
                people.live();
                total += (System.nanoTime() - startTime);
            }
            System.out.println("average for direct invoke:" + total / 100);
        }
    }
}
