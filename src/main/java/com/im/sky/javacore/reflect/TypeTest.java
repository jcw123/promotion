package com.im.sky.javacore.reflect;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author jiangchangwei
 * @date 2021-1-19 下午 2:54
 **/
public class TypeTest<T, V extends @Custom Number & Serializable> {
    private Number number;
    public T t;
    public V v;
    public List<T> list = new ArrayList<>();
    public Map<String, T> map = new HashMap<>();

    public T[] tArray;
    public List<T>[] ltArray;

    public TypeTest testClass;
    public TypeTest<T, Integer> testClass2;

    public Map<? super String, ? extends Number> mapWithWildcard;

    //泛型构造函数,泛型参数为X
    public <X extends Number> TypeTest(X x, T t) {
        number = x;
        this.t = t;
    }

    //泛型方法，泛型参数为Y
    public <Y extends T> void method(Y y) {
        t = y;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        test1();
        test2();
        test3();
        test4();
    }

    private static void test1() throws NoSuchFieldException {
        Field v = TypeTest.class.getField("v");//用反射的方式获取属性 public V v;
        TypeVariable typeVariable = (TypeVariable) v.getGenericType();//获取属性类型
        System.out.println("TypeVariable1:" + typeVariable);
        System.out.println("TypeVariable2:" + Arrays.asList(typeVariable.getBounds()));//获取类型变量上界
        System.out.println("TypeVariable3:" + typeVariable.getGenericDeclaration());//获取类型变量声明载体
//1.8 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
        AnnotatedType[] annotatedTypes = typeVariable.getAnnotatedBounds();
        System.out.println("TypeVariable4:" + Arrays.asList(annotatedTypes) + " : " +
                Arrays.asList(annotatedTypes[0].getAnnotations()));
        System.out.println("TypeVariable5:" + typeVariable.getName());
    }

    private static <K, V> void test2() {
        Map<K, V> map = new HashMap<>();
        Type type = map.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType)type;
            System.out.println("参数类型列表：" + Arrays.asList(pType.getActualTypeArguments()));
            System.out.println("参数原始类型:" + pType.getRawType());
            System.out.println("参数父类型：" + pType.getOwnerType());
        }
    }

    private static void test3() throws NoSuchFieldException {
        Field tArray = TypeTest.class.getField("tArray");
        System.out.println("数组参数类型1:" + tArray.getGenericType());
        Field ltArray = TypeTest.class.getField("ltArray");
        System.out.println("数组参数类型2:" + ltArray.getGenericType());//数组参数类型2:java.util.List<T>[]
        if (tArray.getGenericType() instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType) tArray.getGenericType();
            System.out.println("数组参数类型3:" + arrayType.getGenericComponentType());//数组参数类型3:T
        }
    }

    private static void test4() throws NoSuchFieldException {
        Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard");
        Type wild = mapWithWildcard.getGenericType();//先获取属性的泛型类型 Map<? super String, ? extends Number>
        if (wild instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) wild;
            Type[] actualTypes = pType.getActualTypeArguments();//获取<>里面的参数变量 ? super String, ? extends Number
            System.out.println("WildcardType1:" + Arrays.asList(actualTypes));
            WildcardType first = (WildcardType) actualTypes[0];//? super java.lang.String
            WildcardType second = (WildcardType) actualTypes[1];//? extends java.lang.Number
            System.out.println("WildcardType2: lower:" + Arrays.asList(first.getLowerBounds()) + "  upper:" + Arrays.asList(first.getUpperBounds()));//WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
            System.out.println("WildcardType3: lower:" + Arrays.asList(second.getLowerBounds()) + "  upper:" + Arrays.asList(second.getUpperBounds()));//WildcardType3: lower:[]  upper:[class java.lang.Number]
        }
    }
}
