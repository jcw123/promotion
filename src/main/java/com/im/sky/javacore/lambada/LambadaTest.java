package com.im.sky.javacore.lambada;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 4:57
 **/
public class LambadaTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        List<People> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(new People(i, i % 2, "p" + i));
        }
        List<Integer> result = list.stream().flatMap(t -> Stream.of(t.age)).collect(Collectors.toList());
        System.out.println(result.size());

        String s = result.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        System.out.println(s);

    }

    private static class People {
        int age;

        String name;

        int sex;

        People(int age, int sex, String name) {
            this.age = age;
            this.name = name;
            this.sex = sex;
        }
    }
}
