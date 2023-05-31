package com.im.sky;

import javassist.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-04 14:25
 **/
public class ExceptionTest {

    @Test
    public void testClassNotFoundException() throws Exception {
        Assertions.assertThatThrownBy(()-> Class.forName("com.test.NoExistClass", false, ClassLoader.getSystemClassLoader()))
                .isInstanceOf(ClassNotFoundException.class);
    }

    @Test
    public void testNoClassDefFoundError() throws Exception {
        MyDIYClassLoader loader = new MyDIYClassLoader();
        Thread.currentThread().setContextClassLoader(loader);
        Class<?> cls = loader.loadClass("AAA");
        System.out.println(cls.getClassLoader());
        System.out.println(cls.newInstance());
        Assertions.assertThatThrownBy(() -> cls.getDeclaredMethod("getValue"))
                .isInstanceOf(NoClassDefFoundError.class);
    }

    @Test
    public void testLinkageError() throws Exception {
        URL url = new File("/Users/jiangchangwei/jd_source/promotion/target/test-classes").toURI().toURL();
        DefaultClassLoader loader1 = new DefaultClassLoader(new URL[]{url});
        DefaultClassLoader loader2 = new DefaultClassLoader(new URL[]{url});
        Class<?> runClass = loader1.loadClass("com.im.sky.ExceptionTest$Run");
        loader1.loadClass("com.im.sky.ExceptionTest$A");
        loader1.setDelegate(loader2);
        Assertions.assertThatThrownBy(runClass::newInstance)
                .isInstanceOf(LinkageError.class);
    }

    public static class A {

    }

    public static class AHolder {
        public A a = new A();
    }

    public static class Run {

        public Run() {
            AHolder aHolder = new AHolder();
            A a = aHolder.a;
        }
    }


    public static class DefaultClassLoader extends URLClassLoader {

        DefaultClassLoader delegate;

        DefaultClassLoader(URL[] urls) {
            super(urls, null);
        }

        void setDelegate(DefaultClassLoader delegate) {
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

    private static class MyDIYClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if("AAA".equals(name)) {
                return makeClass(name);
            }
            throw new ClassNotFoundException();
        }
    }

    private static Class<?> makeClass(String className) throws ClassNotFoundException {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass bb = cp.makeClass("BB");
            CtClass ctClass = cp.makeClass(className);
            CtField ctField = new CtField(bb, "value", ctClass);
            ctClass.addField(ctField);
            CtMethod valueGetter = new CtMethod(bb, "getValue", new CtClass[]{}, ctClass);
            valueGetter.setModifiers(Modifier.PUBLIC);
            valueGetter.setBody("return this.value;");
            ctClass.addMethod(valueGetter);
            ctClass.writeFile("./");
            return ctClass.toClass();
        }catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }
}
