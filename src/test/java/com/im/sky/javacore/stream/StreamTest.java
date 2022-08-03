package com.im.sky.javacore.stream;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author jiangchangwei
 * @date 2020-7-9 下午 2:24
 **/
public class StreamTest {

    @Test
    public void test1() {
       Collector<String, ArrayList<String>, String> collector = Collector.of(ArrayList::new, ArrayList::add,
               (left, right) -> {left.addAll(right); return left;}, JSON::toJSONString);
       String[] sArray = {"a","b","c","d"};
       String s = Arrays.stream(sArray).collect(collector);
        System.out.println(s);
    }

    @Test
    public void test2() {
    }

}
