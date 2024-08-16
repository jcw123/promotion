package com.im.sky.test;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-06-01 15:58
 **/
class MyType<E> {
    List<String> getNames() {
        return Arrays.asList("John", "Mary");
    }

    public static void main(String[] args) {
        MyType<Integer> rawType = new MyType<>();
        // unchecked warning!
        // required: List<String> found: List
        List<String> names = rawType.getNames();
        // compilation error!
        // incompatible types: Object cannot be converted to String
        for (String str : rawType.getNames())
            System.out.print(str);
    }
}
