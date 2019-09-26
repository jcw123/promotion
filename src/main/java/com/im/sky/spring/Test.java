package com.im.sky.spring;

/**
 * Created by jiangchangwei on 2019/6/26.
 */
public class Test {

    public static void main(String[] args) {
        AA o = new A();
        System.out.println(o.getClass());
        System.out.println(AA.class);
    }

    private static interface AA {

    }

    private static class A implements AA{

    }
}
