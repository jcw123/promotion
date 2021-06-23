package com.im.sky.test.javassist;

import com.im.sky.test.javassist.data.Man;
import com.im.sky.test.javassist.data.People;
import javassist.ClassPool;
import javassist.CtClass;

import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws Exception {
        testChangeSuperClass();
    }

    private static void testChangeSuperClass() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.im.sky.test.javassist.data.Man");
        cc.setSuperclass(pool.get("com.im.sky.test.javassist.data.People"));
//        cc.toClass();
        Object people = new Man();
        Method method = People.class.getMethod("say");
        method.invoke(people);
    }

    private static void testCreateNewClassAndMethod() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Joy");
        pool.do
    }
}
