package com.im.sky.test;

import java.util.function.Consumer;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-15 14:48
 **/
public class ClosureTest {

    public static Consumer<String> test(String prefix) {
        // lambada表达式中能够持有i这个局部变量的值
        return t -> System.out.println(prefix + "，" + t);
    }

    public static void main(String[] args) {
        Consumer<String> cry = test("你好吗");
        cry.accept("小明");

        Consumer<String> laugh = test("你笑了");
        laugh.accept("小红");
    }
}
