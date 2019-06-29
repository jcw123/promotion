package com.im.sky.pattern.factory;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 上午 11:46
 * @Version 1.0
 *
 * 工厂模式：创建型模式
 *
 * 简单工厂模式：不属于23中设计模式
 * 工厂模式：涉及一个产品族
 * 抽象工厂模式：涉及多个产品族
 */
public class FactoryBase {

    public static void main(String[] args) {
        /**
         * 简单工厂
         */
        Object simpleObject = SimpleFactory.createSimpleObject();

        /**
         * 工厂模式实例
         */
        Factory factory = new YourFactory();
        factory.createMouse();

        Factory myFactory = new MyFactory();
        myFactory.createMouse();

        /**
         * 抽象工厂实例
         */
        AbstractFactory abstractFactory = new YourFactoryImpl();
        abstractFactory.createKey();
        abstractFactory.createMouse();
    }

    /**
     * 一、
     *
     * 只涉及一个产品，并且直接就可以创建
     */
    static class SimpleFactory {

        public static Object createSimpleObject() {
            return new Object();
        }
    }

    /**
     * 工厂模式
     *
     * 如下面所示只涉及鼠标产品
     *
     * 一个产品族对应一个工厂
     */
    static abstract class Factory {
        abstract Mouse createMouse();
    }

    static class YourFactory extends Factory {
        @Override
        Mouse createMouse() {
            return new YourMouse();
        }
    }

    static class MyFactory extends Factory {
        @Override
        Mouse createMouse() {
            return new MyMouse();
        }
    }



    interface Mouse {

    }

    /**
     * 抽象工厂模式
     *
     * 一个体系对应一个工厂，比如华为手机和华为耳机算是一个体系
     */
    abstract static class AbstractFactory {
        abstract Mouse createMouse();
        abstract Key createKey();
    }

    static class YourFactoryImpl extends AbstractFactory {
        @Override
        Mouse createMouse() {
            return new YourMouse();
        }

        @Override
        Key createKey() {
            return new YourKey();
        }
    }

    static class MyFactoryImpl extends  AbstractFactory {
        @Override
        Mouse createMouse() {
            return new MyMouse();
        }

        @Override
        Key createKey() {
            return new MyKey();
        }
    }

    interface Key {}

    static class YourMouse implements Mouse {}

    static class MyMouse implements Mouse {}

    static class YourKey implements Key {}

    static class MyKey implements Key {}


}
