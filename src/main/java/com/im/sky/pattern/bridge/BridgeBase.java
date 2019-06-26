package com.im.sky.pattern.bridge;

import com.im.sky.pattern.adapter.AdapterBase;

/**
 * Created by jiangchangwei on 2019/6/26.
 * 结构型模式
 * 桥梁模式
 * 桥梁模式的用意是将抽象化和实现化脱藕，使两者可以独立的变化
 *
 * 关键角色：
 * Abstraction：抽象化角色；并保存一个对实现化角色的应用
 * Refined Abstraction：修正抽像化角色；扩展抽象化角色，改变或者修正对父类抽象化的定义
 * 实现化角色接口：具体实现的接口，但不给出具体的实现
 * 具体实现化接口：角色接口的具体实现。
 *
 * 聚合关系将抽象化角色和实现化角色关联起来，像桥梁一样。
 */
public class BridgeBase {

    private static Abstractor abstractor;

    /**
     * 抽象化角色
     */
    static abstract class Abstractor {

        // 通过聚合模式保持对实现化角色的引用
        private Implementer implementer;

        public Abstractor(Implementer implementer) {
            this.implementer = implementer;
        }

        abstract void print();

        public Implementer getImplementer() {
            return implementer;
        }
    }


    /**
     * 实现化角色
     */
    static abstract class Implementer {
        abstract String say(String s);
    }

    static class HiAbstractor extends Abstractor {

        public HiAbstractor(Implementer implementer) {
            super(implementer);
        }

        @Override
        void print() {
            System.out.println("hi" + getImplementer().say("zzz"));
        }
    }

    static class HelloAbstractor extends Abstractor {

        public HelloAbstractor(Implementer implementer) {
            super(implementer);
        }

        @Override
        void print() {
            System.out.println("hello" + getImplementer().say(" what?"));
        }
    }

    static class LowImplementer extends Implementer {
        @Override
        String say(String s) {
            return s + " GG";
        }
    }

    static class HighImplementer extends Implementer {
        @Override
        String say(String s) {
            return s  + "MM";
        }
    }

    public static void main(String[] args) {
        Abstractor abstractor = new HelloAbstractor(new LowImplementer());
        abstractor.print();
    }
}
