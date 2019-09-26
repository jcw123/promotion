package com.im.sky.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<People> list = new ArrayList<>();
        Map<Integer, List<String>> map = list.stream().collect(Collectors.groupingBy(People::getAge, Collectors.mapping(People::getName, Collectors.toList())));
        System.out.println(map.size());
    }

    private static class People {
        private int age;

        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
