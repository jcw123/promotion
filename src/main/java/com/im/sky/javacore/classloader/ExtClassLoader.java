package com.im.sky.javacore.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author jiangchangwei
 * @program promotion
 * @description 自定义扩展类加载器
 * @date 2023-04-03 15:54
 **/
public class ExtClassLoader extends URLClassLoader {

    private ExtClassLoader delegate;

    public ExtClassLoader(URL[] urls) {
        super(urls, null);
    }

    public void setDelegate(ExtClassLoader delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if(delegate != null) {
            return delegate.loadClass(name, resolve);
        }
        return super.loadClass(name, resolve);
    }
}
