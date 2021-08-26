package com.im.sky.javassist.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class DefaultClassloader extends URLClassLoader {

    public DefaultClassloader(URL[] urls) {
        super(urls);
    }

    public DefaultClassloader(String[] paths) {
        super(new URL[0]);
        if(paths != null && paths.length > 0) {
            for(String path : paths) {
                try {
                    URL url = new File(path).toURI().toURL();
                    addURL(url);
                }catch (Exception e) {

                }
            }
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clz = this.findLoadedClass(name);
        if(clz != null) {
            return clz;
        }
        try {
            clz = DefaultClassloader.getSystemClassLoader().loadClass(name);
        }catch (ClassNotFoundException e) {

        }
        if(clz != null) {
            return clz;
        }
        return this.findClass(name);
    }
}
