package com.im.sky.collector;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jiangchangwei
 * @program promotion
 * @description collectorTest
 * @date 2023-03-24 16:54
 **/
public class CollectorTest {

    @Test
    public void testFlatMappings() {
        List<List<String>> list = new ArrayList<>();
        List<String> item1 = new ArrayList<>();
        item1.add("1");
        item1.add("2");
        list.add(item1);
        Set<String> result = list.stream().flatMap(Collection::stream)
                .collect(Collectors.toSet());
        System.out.println(result);
    }
}
