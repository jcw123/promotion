package com.im.sky.pattern.decorator;

/**
 * @Author: jiangcw
 * @Date: 2019-6-27 上午 10:56
 * @Version 1.0
 * 装饰器模式 ：结构型模式
 *
 * 四大角色：
 * 1. 抽象构件(Component)角色：给出一个抽象接口，以规范准备接收附加责任的对象。

　　2. 具体构件(ConcreteComponent)角色：定义一个将要接收附加责任的类。

　　3. 装饰(Decorator)角色：持有一个构件(Component)对象的实例，并定义一个与抽象构件接口一致的接口。

　　4. 具体装饰(ConcreteDecorator)角色：负责给构件对象“贴上”附加的责任。
 *
 * 核心：在于附加功能，而不改变原有的核心功能
 * 结构和代理模式非常类似，但代理模式的核心在于控制
 *
 *
 * 注：Java中的I/O流是装饰器模式的经典应用。
 *
 *
 *
 *
 */
public class DecoratorBase {

    static interface Human {
        void eat();
    }

    static class Man implements Human {
        @Override
        public void eat() {
            System.out.println("吃饭饭");
        }
    }

    static abstract class HumanDecorator implements Human {

        protected Human human;
    }

    static class ManDecorator extends HumanDecorator {

        public ManDecorator(Human human) {
            this.human = human;
        }

        @Override
        public void eat() {
            System.out.println("我先喝罐孟婆汤");
            human.eat();
        }
    }

    public static void main(String[] args) {
        Human human = new Man();
        Human decorator = new ManDecorator(human);
        decorator.eat();
    }
}
