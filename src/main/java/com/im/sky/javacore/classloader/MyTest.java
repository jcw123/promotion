package com.im.sky.javacore.classloader;

public class MyTest {

    public static void main(String[] args) throws Exception {
        String classPath = "/Users/jiangchangwei/tmp/";
        DefaultClassloader c1 = new DefaultClassloader(classPath, null);
        DefaultClassloader c2 = new DefaultClassloader(classPath, null);
        System.out.println(c2.getParent());
        Thread.currentThread().setContextClassLoader(c1);
        Class<?> cls = c2.loadClass("com.jd.test.Test");
    }
}
