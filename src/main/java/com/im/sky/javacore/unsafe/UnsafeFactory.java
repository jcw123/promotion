package com.im.sky.javacore.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author jiangchangwei
 * @date 2020-5-28 上午 8:38
 **/
public class UnsafeFactory {

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        }catch (Exception ignored) {}
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}
