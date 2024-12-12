package com.im.sky.test;

/**
 * @author jiangchangwei
 * @since 2024/12/5
 */
public class ClassTest {

    public static void main(String[] args) {
        A b = new B();
        b.say();
    }


    static class A {
        void say() {
            System.out.println(getClass().getSimpleName());
        }

        void hello() {

        }
    }

    static class B extends A {

    }


}
