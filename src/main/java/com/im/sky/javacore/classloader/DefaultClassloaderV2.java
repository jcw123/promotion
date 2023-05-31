package com.im.sky.javacore.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class DefaultClassloaderV2 extends ClassLoader {

    private String classPath;

    public DefaultClassloaderV2(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        return this.defineClass(name, classData, 0, classData.length);
    }

    private byte[] loadClassData(String name) {
        name = name.replace(".", "/");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] arr = new byte[1024];
        try {
            FileInputStream fis = new FileInputStream(new File(classPath + name + ".class"));
            int len;
            while((len = fis.read(arr)) != -1) {
                bao.write(arr, 0, len);
            }
        }catch (Exception e) {

        }
        return bao.toByteArray();
    }
}
