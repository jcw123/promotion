package com.im.sky.pattern.facade;

/**
 * @Author: jiangcw
 * @Date: 2019-6-28 下午 9:07
 * @Version 1.0
 *
 * 外观模式：又称门面模式，属于结构型模式
 *
 *基本构件：
 * （1）外观类对象
 * （2）一个或者多个子系统
 *
 * 适用场景：要为复杂的子系统提供简单的接口；客户系统与多个子系统（子系统多，耦合性多，
 * 通过一个一个外观类，只让客户系统和外观类打交道）存在很大的依赖性
 *
 * 目的：降低耦合性，和“迪米特法则”的要求很像。少打交道，改变时就不会有太大的影响。
 *
 * 典型应用：
 * （1）Tomecat中的RequestFacade和ResponseFacade。对于底层的组件，一些内部的方法不应该暴露给应用层，
 * 通过门面类屏蔽内部使用的一些方法。
 *（2）SLF4J：简单的日志外观模式
 *
 *注：感觉对创建复杂对象的一种封装挺像外观模式的，这个方法本身就是一个外观。
 */
public class FacadeBase {

    static class SystemFacade {

        private SystemA systemA;
        private SystemB systemB;
        private SystemC systemC;

        public SystemFacade(SystemA systemA, SystemB systemB, SystemC systemC) {
            this.systemA = systemA;
            this.systemB = systemB;
            this.systemC = systemC;
        }

        public void payTogether() {
            systemA.consturct();
            systemB.sale();
            systemC.buy();
        }

        public static void main(String[] args) {
            new SystemFacade(new SystemA(), new SystemB(), new SystemC()).payTogether();
        }

    }

    static class SystemA {
        void consturct() {
            System.out.println("我正在构造复杂的对象");
        }
    }

    static class SystemB {
        void sale() {
            System.out.println("使用构造出来的东西卖钱");
        }
    }

    static class SystemC {
        void buy() {
            System.out.println("通过赚的钱买东西");
        }
    }
}
