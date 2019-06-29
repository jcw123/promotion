package com.im.sky.pattern.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 4:53
 * @Version 1.0
 *
 * 观察者模式：行为型模式
 *
 * 观察者模式和发布-订阅模式非常类似，观察者模式对象之间耦合性强，而发布-订阅
 * 模式通常会通过一个第三方组件进行交互（比如JMQ平台，消息队列平台）。
 *
 * 基本构件：
 * 抽象观察者
 * 具体观察者
 * 抽象主题角色
 * 具体主题角色
 */
public class ObserverBase {

    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        subject.addObserver(new ObserverA());
        subject.addObserver(new ObserverB());
        subject.doSomething();
    }

    abstract static class Subject {
        private Set<AbstractObserver> observer = new HashSet<>();

        public void addObserver(AbstractObserver abstractObserver) {
            observer.add(abstractObserver);
        }

        public void removeObserver(AbstractObserver abstractObserver) {
            observer.remove(abstractObserver);
        }

        public void notifyAllObservers() {
            for(AbstractObserver abstractObserver : observer) {
                abstractObserver.trigger();
            }
        }

        abstract void doSomething();
    }

    static class ConcreteSubject extends Subject {
        @Override
        void doSomething() {
            System.out.println("我下定决心开始往前走");
            notifyAllObservers();
        }
    }

    abstract static class AbstractObserver {
        abstract void trigger();

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    static class ObserverA extends AbstractObserver {

        @Override
        void trigger() {
            System.out.println("观察者A做了不可理喻的事");
        }
    }

    static class ObserverB extends AbstractObserver {
        @Override
        void trigger() {
            System.out.println("观察者B决定拯救全人类");
        }
    }
}
