package com.im.sky.javacore.classloader.t1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class JokeClassLoader extends ClassLoader {

    private static final String ROOT_PATH = "/Users/jiangchangwei/tmp/";

    private static final String CLASS_PATH = "com.im.sky.javacore.classloader.t1";

    // name格式为："com.name.People"
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String newName = CLASS_PATH + "." + name;
        synchronized (getClassLoadingLock(newName)) {
            Class<?> cls = findLoadedClass(newName);
            if (cls != null) {
                return cls;
            }
            try {
                cls = ClassLoader.getSystemClassLoader().loadClass(name);
            }catch (ClassNotFoundException ignored) {

            }
            if(cls == null) {
                cls = findClass(name);
            }
            return cls;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getClassBytes(name);
        return defineClass(CLASS_PATH + "." + name, bytes, 0, bytes.length);
    }

    private byte[] getClassBytes(String name) throws ClassNotFoundException {
        FileInputStream fis;
        BufferedInputStream is = null;
        try {
            name += ".class";
            name = ROOT_PATH + name;
            File file = new File(name);
            fis = new FileInputStream(file);
            is = new BufferedInputStream(fis);
            byte[] arr = new byte[fis.available()];
            is.read(arr);
            return arr;
        }catch (Exception e) {
            throw new ClassNotFoundException();
        }finally {
            if(is != null) {
                try {
                    is.close();
                }catch (Exception e) {
                    throw new ClassNotFoundException();
                }
            }
        }
    }
}
