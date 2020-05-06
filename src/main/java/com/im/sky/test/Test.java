package com.im.sky.test;

import com.alibaba.fastjson.JSON;

import java.lang.annotation.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) throws Exception {
        BigDecimal t = new BigDecimal(5);
        sub(t);
        System.out.println(t.toPlainString());
    }

    private static void sub(BigDecimal m) {
        m.subtract(new BigDecimal(3));
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
