package com.im.sky.test;

import com.im.sky.collection.domain.People;
import com.im.sky.test.inittest.Apple;
import com.im.sky.test.inittest.Food;
import com.im.sky.test.inittest.Fruit;
import lombok.Builder;
import lombok.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
@Builder(builderMethodName = "builder", builderClassName = "TestBuilder")
public class Test {

    @NonNull
    private String a;

    @NonNull
    private final String b;

    public static TestBuilder builder(final String b) {
        return new TestBuilder().b(b);
    }

    public static void main(String[] args) throws Exception {
        int[] x = new int[1];
        int[] y = new int[1];
        // 3x + 5y = 1
        // 5x0 + 3y0 = 1
        // 3x0 + 2y0 = 1
        // ax0 + y0 = 1
        // x0 + 0y0 = 1
        // bx0 + a % b y0 =1
        // ax0 + by0 = 1
        // bx11 + (a - a / b)y11 = 1
        // bx11 + ay11 - a/b * y11 = 1
        // ax0 + a(x11 - a/b * y11) = 1
        // bx11 + a % b y11 = 1
        // ay11 + bx11 - b * a / b * y11 = 1
        // ay11 + bx11 - ay11 = 1
        // bx11 = 1
        // ay1 + b(x1 - a / b * y1) = bx1 + a % b y1 = 1
        //
        exgcd(3, 5, x, y);
        System.out.println("x:" + x[0] + ",y:" + y[0]);
    }

    static void exgcd(int a, int b, int[] x, int[] y) {
        if(b == 0) {
            x[0] = 1;
            y[0] = 0;
            System.out.println("a:" + a + ", b:" + b + ", x:" + x[0] + ", y:" + y[0]);
            return;
        }
        exgcd(b, a % b, y, x);
        y[0] -= a /  b * x[0];
        System.out.println("a:" + a + ", b:" + b + ", x:" + x[0] + ", y:" + y[0]);
    }

    static <T> List<? super T> list(T... arr) {
        if(arr == null) {
            return null;
        }
        List<? super T> list = new ArrayList<>();
        for (T t : arr) {
            list.add(t);
        }
        return list;
    }

    public static void test2(C<? extends A> c) {

    }

    public static void test3(C<? super A> c) {

    }

    public static void test4() throws Exception {
        Class<C> cClass = C.class;
        ParameterizedType typeVariable = (ParameterizedType) cClass.getDeclaredField("data").getGenericType();
//        System.out.println("annotated bounds:" + Arrays.toString(typeVariable.getAnnotatedBounds()));
//        System.out.println("bounds:" + Arrays.toString(typeVariable.getBounds()));
//        System.out.println("name:" + typeVariable.getName());
        WildcardType wildcardType = (WildcardType) (typeVariable.getActualTypeArguments()[1]);
        System.out.println(wildcardType.getUpperBounds()[0].getTypeName());
        TypeVariable<Class<C>>[] parameters = cClass.getTypeParameters();
//        System.out.println(Object.class.getGenericSuperclass().getTypeName());
    }

    static class C<T> {

        Map<String, ? extends T> data;

//        void set(T a) {
//            data = a;
//        }
//
//        T get() {
//            return data;
//        }

    }

    static class P<T> {
        T data;

        void set(T data) {
            this.data = data;
        }
    }

    static class A<T> extends P<T> {
        T data;
    }
}
