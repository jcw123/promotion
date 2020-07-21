package com.im.sky.javacore.stream;

import java.util.ArrayList;

/**
 * @author jiangchangwei
 * @date 2020-7-1 下午 5:11
 **/
public class StreamTest {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Integer val = list.stream().reduce(1, Integer::sum, (a, b) -> -1);
        System.out.println(val);
        list.stream().findFirst().orElse(0);
    }
}
