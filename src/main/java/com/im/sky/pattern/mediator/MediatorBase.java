package com.im.sky.pattern.mediator;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 4:18
 * @Version 1.0
 * 中介者模式：又称为调停者模式，是一种行为型模式
 *
 * 基本构件：
 * （1）抽象中介者（Mediator）
 * （2）具体中介者（ConcreteMediator）
 * （3）同事类（Colleague）：同事类有多个，并且彼此之间相互打交道
 *
 * 定义：如果对象之前的交互错综复杂，可以通过引入一个中介者，让所有的对象都直接和中介者打交道。'
 *
 * 注：将相互影响中的逻辑放到中介者对象中去做，虽然降低了耦合性，但可能导致中介者对象异常复杂。
 *
 * 中介者模式的典型应用：java中Timer、Executor、ExecutorService。
 * ========================解耦===========================方便扩展修改
 *
 */
public class MediatorBase {

    public static void main(String[] args) {
        ColleagueA colleagueA = new ColleagueA();
        ColleagueB colleagueB = new ColleagueB();
        AbstractMediator mediator = new ConcreteMediator(colleagueA, colleagueB);
        colleagueA.setA(10,mediator);
        System.out.println("colleague value:" + colleagueA.getA());
        System.out.println("colleague value:" + colleagueB.getA());
        colleagueB.setA(20,mediator);
        System.out.println("colleague value:" + colleagueA.getA());
        System.out.println("colleague value:" + colleagueB.getA());
    }

    abstract static class AbstractMediator {
        abstract void aAffectB();
        abstract void bAffectA();
    }

    static class ConcreteMediator extends AbstractMediator {

        private ColleagueA colleagueA;

        private ColleagueB colleagueB;

        public ConcreteMediator(ColleagueA colleagueA, ColleagueB colleagueB) {
            this.colleagueA = colleagueA;
            this.colleagueB = colleagueB;
        }

        @Override
        void aAffectB() {
            int value = colleagueA.getA();
            colleagueB.setA(value * 10);
        }

        @Override
        void bAffectA() {
            int value = colleagueB.getA();
            colleagueA.setA(value / 10);
        }
    }

    abstract static class AbstractColleague {
        private Integer a;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public abstract void setA(int a, AbstractMediator mediator);
    }

    static class ColleagueA extends AbstractColleague {
        @Override
        public void setA(int a, AbstractMediator mediator) {
            setA(a);
            mediator.aAffectB();
        }
    }

    static class ColleagueB extends AbstractColleague {

        @Override
        public void setA(int a, AbstractMediator mediator) {
            setA(a);
            mediator.bAffectA();
        }
    }
}
