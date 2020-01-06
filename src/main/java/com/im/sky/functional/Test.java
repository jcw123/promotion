package com.im.sky.functional;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author jiangchangwei
 * @date 2020-1-6 上午 10:26
 **/
public class Test<T> {

    Predicate<T> predicate = (o) -> {
        if(o instanceof Integer) {
            return (Integer)o == 3;
        }
        return false;
    };

    Consumer<Integer> consumer = (o) -> {
        System.out.println("first:" + o);
    };

    Consumer<Integer> sendConsumer = (o) -> {
        System.out.println("second:" + o);
    };

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4));
        Test<Integer> test = new Test<>();
        test.test(test.predicate, 100);
        Integer m = 3;
        List<Integer> list2 = new ArrayList<>(Arrays.asList(3));
        list2.forEach(test.consumer.andThen(test.sendConsumer));
    }

    public void test(Predicate<? super T> predicate, T object) {
        predicate.test(object);
    }

    static class A {}

    static class AA extends A {}
}
