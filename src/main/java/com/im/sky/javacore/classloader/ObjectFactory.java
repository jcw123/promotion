package com.im.sky.javacore.classloader;

/**
 * @author jiangchangwei
 * @date 2020-7-2 下午 8:08
 **/
public class ObjectFactory {

    private DefaultClassloader defaultClassloader;

    public ObjectFactory(DefaultClassloader classloader) {
        this.defaultClassloader = classloader;
    }

    public <T>  T buildInstance(String name) {
        try {
            Class<T>clazz = (Class<T>)defaultClassloader.loadClass(name);
            return clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
