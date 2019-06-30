package com.im.sky.pattern.memento;

import javax.swing.*;
import javax.swing.text.Caret;
import java.util.Random;
import java.util.Stack;

/**
 * Created by jiangchangwei on 2019/6/30.
 *
 * 备忘录模式：行为型模式
 *
 *
 * 基本构件：
 * （1）原发器（Originator）：备忘录中就是存储这个对象的状态
 * （2）备忘录（Memento）：一个对象就相当于原发器的一个中间状态
 * （3）负责人（Caretaker）：用于存储备忘录信息，同时将这个信息传递给原发器
 *
 *
 * 典型应用：像传统中的撤销、回滚等功能是这个模式的典型体现
 *
 *
 *适用场景：
 * （1）涉及到中间状态的恢复场景
 * （2）不想讲对象的状态直接透露给外部，可以通过负责人间接访问对象的内部状态
 *
 *
 */
public class MementoBase {

    public static void main(String[] args) {
        Caretaker caretaker = new Caretaker();
        Mine mine = new Mine(caretaker);
        for(int i = 0; i <  100; i++) {
            mine.run();
        }
        mine.recovery();
        mine.run();
    }

    static class Mine {

        private Caretaker caretaker;

        public Mine(Caretaker caretaker) {
            this.caretaker = caretaker;
            init();
        }

        private static Random random = new Random();

        private int remainPower;

        private void init() {
            this.remainPower = 100;
        }

        public void run() {
            if(remainPower <= 0) {
                System.out.println("没有能量了，不能继续跑了");
                return;
            }
            System.out.println("愉快的运动吧");
            int reduce =  random.nextInt(10);
            caretaker.save(new Memento(remainPower));
            remainPower-= reduce;
        }

        public void recovery() {
            Memento memento = caretaker.pop();
            if(memento != null) {
                this.remainPower = memento.remainPower;
            }else {
                System.out.println("没有更多的记录了，无法恢复现场");
            }
        }
    }

    static class Memento {
        private int remainPower;

        public Memento(int remainPower) {
            this.remainPower = remainPower;
        }
    }

    static class Caretaker {
        Stack<Memento> stack = new Stack<>();

        public Memento pop() {
            if(!stack.isEmpty()) {
                return stack.pop();
            }
            return null;
        }

        public void save(Memento memento) {
            stack.push(memento);
        }
    }
}
