package com.im.sky.javacore.stream;

import java.util.function.BiFunction;

/**
 * @author jiangchangwei
 * @date 2020-7-1 下午 5:21
 **/
public class BiFunctionTest {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, String> biFunction = (m1,m2) -> {
            return String.valueOf(m1 + m2);
        };
        biFunction = biFunction.andThen((m) -> m + 1);
        System.out.println(test(biFunction, 1, 2));
    }

    private static String test(BiFunction<Integer, Integer, String> biFunction, Integer m1, Integer m2) {
        return biFunction.apply(m1, m2);
    }
}
