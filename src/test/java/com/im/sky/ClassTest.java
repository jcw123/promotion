package com.im.sky;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ClassTest {

    @Test
    public void testLocalClass() {
        int a = 1;
        class A {
            public void sayA() {
                System.out.println("say A");
                System.out.println("a:");
            }
        }
        new A().sayA();
    }

    @Test
    public void testStaticClass() {

    }

    @Test
    public void testGetName() {
        System.out.println("int name:" + int.class.getName());
        System.out.println("byte name:" + byte.class.getName());
        System.out.println("char name:" + char.class.getName());
        System.out.println("boolean name:" + boolean.class.getName());
        System.out.println("int name:" + int.class.getName());
        System.out.println("long name:"+ long.class.getName());
        System.out.println("float name:" + float.class.getName());
        System.out.println("double name:" + double.class.getName());
        System.out.println("new int[]:" + int[][].class.getName());
        System.out.println("new Object[]:" + Object[].class.getName());
    }

    @Test
    public void testGetSimpleName() {
        System.out.println("int name:" + int.class.getSimpleName());
        System.out.println("byte name:" + byte.class.getSimpleName());
        System.out.println("char name:" + char.class.getSimpleName());
        System.out.println("boolean name:" + boolean.class.getSimpleName());
        System.out.println("int name:" + int.class.getSimpleName());
        System.out.println("long name:"+ long.class.getSimpleName());
        System.out.println("float name:" + float.class.getSimpleName());
        System.out.println("double name:" + double.class.getSimpleName());
        System.out.println("new int[]:" + int[][].class.getSimpleName());
        System.out.println("new Object[]:" + Object[].class.getSimpleName());
    }

    @Test
    public void testGetCanonicalName() {
        System.out.println("int name:" + int.class.getCanonicalName());
        System.out.println("byte name:" + byte.class.getCanonicalName());
        System.out.println("char name:" + char.class.getCanonicalName());
        System.out.println("boolean name:" + boolean.class.getCanonicalName());
        System.out.println("int name:" + int.class.getCanonicalName());
        System.out.println("long name:"+ long.class.getCanonicalName());
        System.out.println("float name:" + float.class.getCanonicalName());
        System.out.println("double name:" + double.class.getCanonicalName());
        System.out.println("new int[]:" + int[][].class.getCanonicalName());
        System.out.println("new Object[]:" + Object[].class.getCanonicalName());
    }

    @Test
    public void testGetTypeName() {
        System.out.println("int name:" + int.class.getTypeName());
        System.out.println("byte name:" + byte.class.getTypeName());
        System.out.println("char name:" + char.class.getTypeName());
        System.out.println("boolean name:" + boolean.class.getTypeName());
        System.out.println("int name:" + int.class.getTypeName());
        System.out.println("long name:"+ long.class.getTypeName());
        System.out.println("float name:" + float.class.getTypeName());
        System.out.println("double name:" + double.class.getTypeName());
        System.out.println("new int[]:" + int[][].class.getTypeName());
        System.out.println("new Object[]:" + Object[].class.getTypeName());
    }

    @Test
    public void testGetAnnotatedInterfaces() {
        System.out.println(JSON.toJSONString(BB.class.getAnnotatedInterfaces()));
        System.out.println(JSON.toJSONString(A.class.getAnnotatedInterfaces()));
    }

    @Test
    public void testGetAnnotatedSuperclass() {
        System.out.println(JSON.toJSONString(BB.class.getAnnotatedSuperclass()));
        System.out.println(JSON.toJSONString(A.class.getAnnotatedSuperclass()));
    }



    @Test
    public void testAny() {
    }

    @Test
    public void testMemberClass() throws NoSuchMethodException {
        Class innerStaticClass = OutClass.InnerStaticClass.class;
        System.out.println(innerStaticClass.isMemberClass());
        System.out.println(Modifier.isStatic(innerStaticClass.getModifiers()));
        Constructor[] constructors = innerStaticClass.getDeclaredConstructors();
        for(Constructor constructor : constructors) {
            System.out.println(constructor.getParameterTypes().length);
        }
        Constructor constructor11 = innerStaticClass.getDeclaredConstructor();
        System.out.println("constructor11:" + constructor11);
        OutClass outClass = new OutClass();
        OutClass.InnerClass innerClass  = outClass.new InnerClass();
        Class ics = innerClass.getClass();
        Constructor[] constructors1 = ics.getDeclaredConstructors();
        for(Constructor constructor : constructors1) {
            System.out.println(constructor.getParameterTypes().length);
            if(constructor.getParameterTypes().length > 0) {
                System.out.println(constructor.getParameterTypes()[0].getName());
            }
        }
        Constructor constructor22 = ics.getDeclaredConstructor();
        System.out.println("constructor22:" + constructor22);
        System.out.println(ics.isMemberClass());
        System.out.println(Modifier.isStatic(ics.getModifiers()));
    }

    interface A1 {}

    interface A extends A1 {

    }

    static class B {

    }

    static class BB extends B implements A {

    }

    private static class OutClass {
        static class InnerStaticClass {

        }

        class InnerClass {

        }
    }

    private class OutClass2 {

        class  InnerClass {

        }
    }
}
