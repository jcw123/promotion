package com.im.sky.lock.juc.aqs.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 4:11
 **/
public class UnSafeTest {

    static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }catch (Exception e) {}
    }

    public static void main(String[] args) throws Exception {
        System.out.println(unsafe.addressSize());
        System.out.println(unsafe.pageSize());
    }
}
