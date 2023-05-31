package com.im.sky.lamda;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LamdaTest {

    @Test
    public void test() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal(1));
        list.add(new BigDecimal(2));
        list.add(null);
        BigDecimal result = list.stream().filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        System.out.println(result.toPlainString());
    }

    @Test
    public void test2() {
        Arrays.stream(new int[]{1, 2, 3})
                .findFirst()
                .ifPresent(System.out::println);
    }

}
