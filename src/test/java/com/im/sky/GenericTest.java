package com.im.sky;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author jiangchangwei
 * @date 2020-3-2 下午 6:14
 **/
public class GenericTest {

    @Test
    public void test1() {
        Map<Class<?>, Consumer<?>> mm = new HashMap<>();
        mm.put(A.class, t->{});
        List<? super B> list = new ArrayList<>();
        list.add(new B());
        list.get(0);
    }

    public class A {
        public A() {

        }
    }

    public class B extends A {
        public B() {

        }
    }

    public class BB extends B {
        public BB() {

        }
    }

    private <T extends Comparable<? super T>> void isLessOrEqual(T left, T right) {
        if(left == null || right == null || left.compareTo(right) > 0) {
            throw new IllegalStateException();
        }
    }

    private <T extends Comparable<T>> void isLessOrEqualV2(T left, T right) {
        if(left == null || right == null || left.compareTo(right) > 0) {
            throw new IllegalStateException();
        }
    }

    public class IntegerCompare implements Comparable<Integer> {

        @Override
        public int compareTo(Integer o) {
            return 0;
        }
    }

    @Test
    public void testM() throws Exception {
        new B();
//        m1(B.class);
//        m2(B.class);
//        m1(BB.class);
        m2(A.class);
    }

    private void m1(Class<? extends B> clz) throws Exception {
        B b = clz.newInstance();
    }

    private void m2(Class<? super B> clz) throws Exception{
        Object o = clz.newInstance();
    }

    private static class GenericDemo<A> {
        private A a;

        private Class<? extends A> b;

        private A[] c;

        private List<A>[] d;

        private List<String>[] e;
    }

    @Test
    public void testGeneric() {
        Field[] fields = GenericDemo.class.getDeclaredFields();
        for(Field field : fields) {
            Type type = field.getGenericType();
            print((type));
        }
    }

    private void print(Type type) {
        System.out.println("start parse type");
        System.out.println("type name:" + type.getTypeName());
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            for(Type actualType : parameterizedType.getActualTypeArguments()) {
                System.out.println("actualType:" + actualType);
            }
            System.out.println("raw type: " + parameterizedType.getRawType());
            System.out.println("owner type:" + parameterizedType.getOwnerType());
        }else if(type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable)type;
            for(Type bound : typeVariable.getBounds()) {
                System.out.println("bound:" + bound);
            }
            System.out.println("declare:" + typeVariable.getGenericDeclaration());
        }else if(type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)type;
            System.out.println("generic component type:" + genericArrayType.getGenericComponentType());
        }else if(type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType)type;
            for(Type upper : wildcardType.getUpperBounds()) {
                System.out.println("upper:" + upper);
            }
            for(Type lower : wildcardType.getLowerBounds()) {
                System.out.println("lower:" + lower);
            }
        }else {
            Class clz = (Class)type;
            System.out.println("clz:" + clz);
        }
        System.out.println("end parse type");
    }
}
