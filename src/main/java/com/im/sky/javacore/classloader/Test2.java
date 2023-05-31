package com.im.sky.javacore.classloader;

public class Test2 {
    public static void main(String[] args) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        while (cl != null) {
            System.out.println(cl.toString());
            System.out.println(cl.getClass().getClassLoader());
            cl = cl.getParent();
        }
        DefaultClassloader loader2 = new DefaultClassloader("/Users/jiangchangwei/jd_source/promotion/target/classes/");
        DefaultClassloader loader1 = new DefaultClassloader("/Users/jiangchangwei/jd_source/promotion/target/classes/", loader2);
        Class<?> cls = loader1.loadClass("com.im.sky.javacore.classloader.Test2");
        Object o = cls.newInstance();
        System.out.println(loader1 + ", " + loader2);
        System.out.println(o.getClass().getClassLoader());
    }

    public static Test2 test(String o) {
        return null;
    }
}
