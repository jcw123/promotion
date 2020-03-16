package com.im.sky.functional;

import com.alibaba.fastjson.JSON;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
//        tstZBiFunction();
        testBasicStream();
        testBiConsumer(List::add, new ArrayList<Integer>(), 10);
        Optional<Integer> optional = Optional.ofNullable(100);
        Test test = new Test();
        test2(Optional::isPresent, optional);
    }

    public void test(Predicate<? super T> predicate, T object) {
        predicate.test(object);
    }

    static class A {}

    static class AA extends A {}

    public static void tstZBiFunction() {
        ZBiFunction<? super Integer, ? super Integer, ? extends Integer> zBiFunction1 = Integer::sum;
        Integer result = zBiFunction1.apply(3, 4);
        System.out.println(result);
    }

    public static void testBasicStream() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        List<Integer> result = list.stream().
                filter(o-> {
                    return o > 2;
                }).
                map(o-> {
                    return o * 2;
                }).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(result));
    }


    public static <A, T> void testBiConsumer(BiConsumer<A, T> consumer, A a, T t) {
        consumer.accept(a, t);
        System.out.println(JSON.toJSONString(a));
    }

    private Predicate predicate2;

    public static <T> void test2(Predicate<T> predicate, T value)  {
        if(predicate.test(value)) {
            System.out.println("测试通过");
        }else {
            System.out.println();
        }
    }
}
