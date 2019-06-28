package com.im.sky.pattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangcw
 * @Date: 2019-6-28 下午 9:36
 * @Version 1.0
 * 享元模式：结构型模式
 *
 * 目的：节省内存空间
 *
 * 基本构件：
 *
 * 》单纯享元模式
 * （1）抽象享元角色
 * （2）具体享元角色
 * （3）享元工厂角色
 *
 * 》复合享元模式
 * （1）抽象享元
 * （2）具体享元
 * （3）复合享元
 * （4）享元工厂角色
 *
 * 注：对象复用是享元模式的经典体现
 *
 * 对于创建的对象有内蕴状态和外蕴状态，将变化的状态提取出来，并且变化的状态不会影响到内蕴状态
 * */
public class FlyweightBase {

    abstract static class AbstractFlyweight {
        abstract void say();
    }

    static class Flyweight extends AbstractFlyweight {

        private String name;

        public Flyweight(String name) {
            this.name = name;
        }

        @Override
        void say() {
            System.out.println("我是元素：" + name);
        }
    }

    static class FlyweightFactory {
        private static Map<String, AbstractFlyweight> factory = new HashMap<>();

        public static AbstractFlyweight getFlyweight(String name) {
            if(factory.containsKey(name)) {
                return factory.get(name);
            }else {
                AbstractFlyweight flyweight = new Flyweight(name);
                factory.put(name, flyweight);
                return flyweight;
            }
        }
    }

    public static void main(String[] args) {
        AbstractFlyweight flyweight = FlyweightFactory.getFlyweight("hehe");
        flyweight.say();
        AbstractFlyweight flyweight1 = FlyweightFactory.getFlyweight("hehe");
        flyweight1.say();
        System.out.println(flyweight == flyweight1);
    }
}
