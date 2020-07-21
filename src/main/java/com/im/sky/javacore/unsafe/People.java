package com.im.sky.javacore.unsafe;

import lombok.Getter;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author jiangchangwei
 * @date 2020-5-28 上午 8:36
 **/
public class People {

    @Getter
    private String name;

    @Getter
    private int age;

    private static Unsafe unsafe = UnsafeFactory.getUnsafe();

    public static final long nameOffset;

    public static  final long ageOffset;

    static {
        try {
            nameOffset = unsafe.objectFieldOffset(People.class.getDeclaredField("name"));
            ageOffset = unsafe.objectFieldOffset(People.class.getDeclaredField("age"));
            System.out.println("nameOffset:" + nameOffset);
            System.out.println("ageOffset:" + ageOffset);
        }catch (Exception e) {
            throw new Error(e);
        }
    }
}
