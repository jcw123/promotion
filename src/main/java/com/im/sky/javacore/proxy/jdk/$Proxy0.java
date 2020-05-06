package com.im.sky.javacore.proxy.jdk;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 3:11
 *
 * 这个类就是通过jdk动态代理生成的代理类形式
 **/
import com.im.sky.javacore.proxy.jdk.*;
import java.lang.reflect.*;

public final class $Proxy0 extends Proxy implements Animal
{
    private static Method m1;
    private static Method m3;
    private static Method m4;
    private static Method m2;
    private static Method m0;
    private static Method m5;

    public $Proxy0(final InvocationHandler invocationHandler) {
        super(invocationHandler);
    }

    public final boolean equals(final Object o) {
        try {
            return (boolean)super.h.invoke(this, $Proxy0.m1, new Object[] { o });
        }
        catch (Error | RuntimeException error) {
            throw error;
        }
        catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        }
    }

    public final void run() {
        try {
            super.h.invoke(this, $Proxy0.m3, null);
        }
        catch (Error | RuntimeException error) {
            throw error;
        }
        catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        }
    }

    public final void eat() {
        try {
            super.h.invoke(this, $Proxy0.m4, null);
        }
        catch (Error | RuntimeException error) {
            throw error;
        }
        catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        }
    }

    public final String toString() {
        try {
            return (String)super.h.invoke(this, $Proxy0.m2, null);
        }
        catch (Error | RuntimeException error) {
            throw error;
        }
        catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        }
    }

    public final int hashCode() {
        try {
            return (int)super.h.invoke(this, $Proxy0.m0, null);
        }
        catch (Error | RuntimeException error) {
            throw error;
        }
        catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        }
    }

    @Override
    public void life() {
        try {
            super.h.invoke(this, $Proxy0.m5, null);
        }
        catch (Error | RuntimeException error) {
            throw error;
        }
        catch (Throwable t) {
            throw new UndeclaredThrowableException(t);
        }
    }

    static {
        try {
            $Proxy0.m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            $Proxy0.m3 = Class.forName("com.im.sky.javacore.proxy.jdk.Animal").getMethod("run", (Class<?>[])new Class[0]);
            $Proxy0.m4 = Class.forName("com.im.sky.javacore.proxy.jdk.Animal").getMethod("eat", (Class<?>[])new Class[0]);
            $Proxy0.m2 = Class.forName("java.lang.Object").getMethod("toString", (Class<?>[])new Class[0]);
            $Proxy0.m0 = Class.forName("java.lang.Object").getMethod("hashCode", (Class<?>[])new Class[0]);
            $Proxy0.m5 = Class.forName("com.im.sky.javacore.proxy.jdk.Animal").getMethod("life", new Class[0]);
        }
        catch (NoSuchMethodException ex) {
            throw new NoSuchMethodError(ex.getMessage());
        }
        catch (ClassNotFoundException ex2) {
            throw new NoClassDefFoundError(ex2.getMessage());
        }
    }
}

