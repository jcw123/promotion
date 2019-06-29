package com.im.sky.pattern.singleton;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 上午 11:02
 * @Version 1.0
 *
 * 单例模式：创建型模式
 *
 * 这个类的实例是可以共享的，为啥可以共享呢？因为不会才在变化的状态，就是
 * 没有变化的数据影响类的行为（主要是多线程环境的线程安全）
 *
 * 常见的单例模式有：
 * 饿汉模式
 * 懒汉模式
 * 双重检查锁模式
 * 内部类模式
 * 枚举模式
 */
public class SingletonBase {

    /**
     * 饿汉模式
     *
     * 类只要进行加载工作，就是实例化
     *
     * 注：构造函数要声明为私有的
     */
    static class EagerSingleton {

        private static final EagerSingleton instance = new EagerSingleton();

        private EagerSingleton() {}

        public static EagerSingleton getInstance() {
            return instance;
        }
    }

    /**
     * 懒汉模式
     *
     * 类加载后并不需要实例化，只有
     *真正需要这个实例时才进行实例化
     */
    static class LazySingleton {
        private static LazySingleton instance;

        private LazySingleton() {}

        public static synchronized LazySingleton getInstance() {
            if(instance == null) {
                instance = new LazySingleton();
            }
            return instance;

        }
    }

    /**
     * 双重检查锁模式
     *
     * 是对懒汉模式的一种优化， 对于懒汉模式，只要获取实例，首先就进行同步。
     * 而双重检查锁模式首先判断实例是否为null，不为null直接返回，如果
     * 为null再进行同步实例化
     *
     * 注：需要加上volatile修饰，因为指令重排序可能会因为还没有进行初始化就被
     * 使用了。
     * 实例化构成分为三步：（1）为对象分配空间，（2）进行<init>初始化工作。（3）将
     * 对象的引用指向内存空间。
     */
    static class Singleton {
        private static volatile Singleton instance;

        private Singleton() {}

        public static Singleton getInstance() {
            if(instance == null) {
                synchronized (Singleton.class) {
                    if(instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }

    /**
     * 使用内部类实现单例模式
     *
     * 主要是利用jvm加载类的机制，加载外部类的时候，如果调用不到内部类，不会自动
     * 加载内部类的。
     */
    static class SingletonByInnerClass {

        private SingletonByInnerClass() {}

        private static class SingletonHolder {
            private static SingletonByInnerClass instance = new SingletonByInnerClass();
        }

        public static SingletonByInnerClass getInstance() {
            return SingletonHolder.instance;
        }
    }

    /**
     * 枚举类其实也是一种恶汉模式，进行
     * 类加载时会实例化类
     */
    static enum SingletonByEnum {
        INSTANCE;

        public void operation() {

        }
    }
}
