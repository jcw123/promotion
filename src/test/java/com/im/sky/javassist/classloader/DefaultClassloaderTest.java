package com.im.sky.javassist.classloader;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class DefaultClassloaderTest {

    @Test
    public void loadClass() throws Exception {
        File file = new File("/Users/jiangchangwei/tmp/classfile");
        URL url = file.toURI().toURL();
        DefaultClassloader loader = new DefaultClassloader(new URL[]{url});

        DefaultClassloader loader2 = new DefaultClassloader(new URL[]{url});
        Class<?> clz = loader.loadClass("Test");

        Class<?> clz2 = loader2.loadClass("Test");
        System.out.println(clz.getClassLoader());
        System.out.println(clz2.getClassLoader());

        Assertions.assertThat(clz == clz2).isFalse();
    }
}