package com.im.sky.javacore.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-16 16:24
 **/
public class InvokeDynamicTest {

    public static void main(String[] args) throws Throwable {
        MethodType mt = MethodType.methodType(String.class);
        MethodHandles.Lookup lk = MethodHandles.lookup();
        InvokeDynamicTest invokeDynamicTest = new InvokeDynamicTest();
        MethodHandle mh = lk.findVirtual(InvokeDynamicTest.class, "toString", mt);
        System.out.println(mh.invoke(invokeDynamicTest));

        mt = MethodType.methodType(void.class);
        mh = lk.findStatic(InvokeDynamicTest.class, "test", mt);
        mh.invoke();

    }

    @Override
    public String toString() {
        return "test toString";
    }

    public static void test() {
        System.out.println("i am test static");
    }
}
