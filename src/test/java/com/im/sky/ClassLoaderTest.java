package com.im.sky;

import org.junit.Test;

/**
 * @author jiangchangwei
 * @date 2020-2-28 上午 9:37
 **/
public class ClassLoaderTest {

    @Test
    public void testBootPath() {
        String bootClassPath = System.getProperty("sun.boot.class.path");
        System.out.println(bootClassPath);
    }

    @Test
    public void testLoadClass() throws ClassNotFoundException {
        Class<?> cls = Class.forName("com.im.sky.test.Test");
        System.out.println(cls.getName());
    }

}
