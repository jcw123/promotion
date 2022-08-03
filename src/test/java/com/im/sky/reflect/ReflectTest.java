package com.im.sky.reflect;

import org.junit.Test;

import java.lang.reflect.Array;

public class ReflectTest {

    @Test
    public void test() {
        Object arr = Array.newInstance(A.class, 5);
        Array.set(arr, 1, new B());
        System.out.println(Array.get(arr, 1));
    }

    private static class A {

    }

    private static class B extends A {

    }
}
