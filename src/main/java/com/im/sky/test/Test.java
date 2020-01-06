package com.im.sky.test;

import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) throws Exception {
        Class cls = B.class;
        System.out.println(cls.getName());
        System.out.println(cls.getGenericSuperclass());
        System.out.println(cls.getAnnotatedSuperclass().getType().getTypeName());
        Type type = cls.getGenericSuperclass();
        System.out.println(type.getTypeName());
        System.out.println(cls.getAnnotation(TestAnnotations.class));
    }

    private interface interface1 {

    }

    private interface Interface2 {

    }

    private static class A implements interface1 {

    }

    @TestAnnotation(name = "jcw1")
    @TestAnnotation(name = "jcw2")
    private static class B extends  A  implements Interface2{

    }

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(TestAnnotations.class)
    private @interface TestAnnotation {
        String name() default "jcw";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    private @interface TestAnnotations {
        TestAnnotation[] value();
    }
}
