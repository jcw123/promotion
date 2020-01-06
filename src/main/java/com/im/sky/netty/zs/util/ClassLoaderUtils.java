package com.im.sky.netty.zs.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ClassLoaderUtils {
    private static final Set<Class> primitiveSet = new HashSet<>();

    static {
        primitiveSet.add(Boolean.class);
        primitiveSet.add(Byte.class);
        primitiveSet.add(Short.class);
        primitiveSet.add(Integer.class);
        primitiveSet.add(Long.class);
        primitiveSet.add(Float.class);
        primitiveSet.add(Double.class);
        primitiveSet.add(Character.class);
    }

    public static ClassLoader getCurrentClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null) {
            classLoader = ClassLoaderUtils.class.getClassLoader();
        }
        return classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
    }

    public static ClassLoader getClassLoader(Class<?> cls) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader != null) {
            return classLoader;
        }
        classLoader = cls.getClassLoader();
        return classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
    }

    public static Class forName(String clsName) throws ClassNotFoundException {
        return forName(clsName, true);
    }

    public static Class forName(String clsName, ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(clsName, true, classLoader);
    }

    private static Class forName(String clsName, boolean initialize) throws ClassNotFoundException{
        return Class.forName(clsName, initialize, getCurrentClassLoader());
    }

    public static <T> T getInstance(Class<T> cls) throws Exception {
        if(primitiveSet.contains(cls)) {
            return null;
        }
        if(cls.isMemberClass() &&  !Modifier.isStatic(cls.getModifiers())) {
            Constructor[] constructors = cls.getDeclaredConstructors();
            Constructor defaultConstructor = null;
            for(Constructor constructor : constructors) {
                if(constructor.getParameterTypes().length == 1) {
                    defaultConstructor = constructor;
                    break;
                }
            }
            if(defaultConstructor != null) {
                if(defaultConstructor.isAccessible()) {
                    return (T)defaultConstructor.newInstance(new Object[]{null});
                }else {
                    try {
                        defaultConstructor.setAccessible(true);
                        return (T)defaultConstructor.newInstance(new Object[]{null});
                    }finally {
                        defaultConstructor.setAccessible(false);
                    }
                }
            }else {
                throw new Exception("The " + cls.getCanonicalName() + " has no default constructor");
            }
        }
        try {
            Constructor constructor = cls.getDeclaredConstructor();
            if(constructor.isAccessible()) {
                return (T)constructor.newInstance();
            }else {
                try {
                    constructor.setAccessible(true);
                    return (T)constructor.newInstance();
                }finally {
                    constructor.setAccessible(false);
                }
            }
        } catch (Exception e) {
            throw new Exception("The " + cls.getCanonicalName() + " has no default constructor");
        }
    }

}
