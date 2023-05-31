package com.im.sky.javacore.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-03 16:00
 **/
public class LinkageTest {

    public static void main(String[] args) throws Exception {
//        test();
        test2();
    }

    private static void test() throws Exception {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(LinkageTest.class.getClassLoader());
        URL url = new File("/Users/jiangchangwei/jd_source/promotion/target/classes").toURI().toURL();
        URL[] urls = new URL[]{url};
        ExtClassLoader ext0 = new ExtClassLoader(urls);
//        Thread.currentThread().setContextClassLoader(ext0);
        Class<?> ctx = ext0.loadClass("com.im.sky.javacore.classloader.t1.ExtCtx", true);
        Class<?> cls0 = ext0.loadClass("com.im.sky.javacore.classloader.t1.EXT", true);
//        System.out.println(cls0.getClassLoader());

        ExtClassLoader ext1 = new ExtClassLoader(urls);
        Class<?> cls1 = ext1.loadClass("com.im.sky.javacore.classloader.t1.EXT", true);
        ext0.setDelegate(ext1);
        System.out.println("ext0:" + ext0 + ", ext1:" + ext1);
        Object ctxO = ctx.newInstance();
    }

    private static void test2() throws Exception {
        String javaHome = System.getProperty("java.home").replace(File.separator + "jre", "");
        System.out.println("javaHome:" + javaHome);
        System.out.println(LinkageTest.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(Arrays.toString(((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs()));
        URL url = new File("/Users/jiangchangwei/jd_source/promotion/target/classes").toURI().toURL();
        URL[] urls = new URL[]{url};
        ExtClassLoader ext0 = new ExtClassLoader(urls);
        ExtClassLoader ext1 = new ExtClassLoader(urls);
        Class<?> cls = ext0.loadClass("com.im.sky.javacore.classloader.t1.PeopleHolder");
        ext0.loadClass("com.im.sky.javacore.classloader.t1.Man");
        ext0.setDelegate(ext1);
        cls.newInstance();
    }
}
