package com.im.sky.javacore.unsafe;

import sun.misc.Cleaner;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author jiangchangwei
 * @date 2020-5-28 上午 8:27
 **/
public class UnsafeTest {

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        }catch (Exception ignored) {
        }

    }

    public static void main(String[] args) {
        testMemoryOperate();
    }

    private static void testMemoryOperate() {
        long address = unsafe.allocateMemory(1024);
        System.out.println(address);
        long address2 = unsafe.reallocateMemory(address, 1024);
        System.out.println(address2);
        People people = new People();
//        unsafe.setMemory(address2, 0, Integer.valueOf(100).byteValue());
        unsafe.putInt(address2, Integer.valueOf(100).byteValue());
        System.out.println("address2:" + unsafe.getInt(address2));
        // 相当于在一个指针的ageOffset偏移量位置，分配2个字节，存储一个65535这个值
        unsafe.setMemory(people,People.ageOffset,2, Integer.valueOf(65535).byteValue());
        People people1 = new People();
        unsafe.copyMemory(people, People.ageOffset, people1, People.ageOffset, 4);
        System.out.println(people.getAge());
        System.out.println(people1.getAge());
    }
}
