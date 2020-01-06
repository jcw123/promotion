package com.im.sky;

import org.junit.Test;

public class TypeNameTest {

    @Test
    public void testJvmName() {
        String[][] arr = new String[2][3];
        String canonicalName = arr.getClass().getCanonicalName();
        String name = arr.getClass().getName();
        String typeName = arr.getClass().getTypeName();
        String simpleName =arr.getClass().getSimpleName();
        System.out.println("canonicalName:" + canonicalName + ", name:" + name + ", typeName:" + typeName
        + ", simpleName:" + simpleName);

    }
}
