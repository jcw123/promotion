package com.im.sky;

import com.im.sky.javacore.generic.Plate;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-21 11:22
 **/
public class FunctionTest {

    @Test
    public void test() {
        int i = 0;
        Object o = new Object();
        Predicate<Integer> p = a -> {
            return a != 1;
        };
        System.out.println(p.test(i));
    }

    @Test
    public void test2() {
        // 定义一个lambda表达式，求一个数组的中位数，因为lambda表达式核心内容是入参和函数体，入参定义为一个数组，
        // 出参定义为一个Double类型的参数
        Function<Double[], Double> f = o -> { // f类似于一个函数变量
            Arrays.sort(o);
            int n = o.length;
            if(n == 0) {
                return 0d;
            }
            return (o[(n - 1) / 2] + o[n / 2]) / 2;
        };
        Double[] arr1 = new Double[]{3d, 1d, 2d, 4d};
        Double[] arr2 = new Double[]{1d, 3d, 2d};
        Assertions.assertThat(f.apply(arr1)).isEqualTo(2.5d);
        Assertions.assertThat(f.apply(arr2)).isEqualTo(2d);
        int[] arr3 = new int[]{1, 2, 3, 5};
        Optional.ofNullable(arr3)
                // map入参传入一个lambda表达式
                .map(t -> Arrays.stream(arr3).sum())
                // filter入参传入一个lambda表达式
                .filter(t -> t > 10)
                // ifPresent入参传入了一个lambda表达式
                .ifPresent(t -> System.out.println("数组之和大于10，求和结果为:" + t));

        int[] arr4 = new int[]{1, 2, 3, 5};
        Map<Integer, Integer> res = Arrays.stream(arr4)
                .map(t -> t + 1)
                .filter(t -> t > 2)
                .collect(HashMap::new, (t, v) -> t.put(v, v), HashMap::putAll);
        System.out.println(res);
        List<? super Fruit> list = new ArrayList<Fruit>();
        Container<? extends Fruit> container = new Container<Apple>(new Apple());
    }

    private class Fruit {

    }

    private class Apple extends Fruit {

    }

    private class Container<T> {
        T data;

        Container(T data) {
            this.data = data;
        }

        T get() {
            return data;
        }

        void set(T data) {
            this.data = data;
        }
    }

    // 因为go中支持函数变量，所以写起来相当于java中更优雅写，参见如下：
}
