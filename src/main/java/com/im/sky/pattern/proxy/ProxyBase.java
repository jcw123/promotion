package com.im.sky.pattern.proxy;

/**
 * Created by jiangchangwei on 2019/6/27.
 *
 * 代理模式：结构型模式
 *
 * 代理模式和装饰器模式的结构非常类似，但是它们的目的不同
 *代理模式：对原有的对象进行控制
 * 装饰器模式：对原有的对象进行功能增强
 *
 *
 * 两者界限划分非常模糊
 *
 * 代理模式典型应用：JDK动态代理、CGLIB动态代理、Spring AOP、Intercepter等
 *
 *
 */
public class ProxyBase {

    static interface Boxer {
        void fight();
    }

    static class BoxerImpl implements Boxer {
        @Override
        public void fight() {
            leftHook();
            rightHook();
            leftHook();
        }

        private void leftHook() {
            System.out.println("左勾拳");
        }

        private void rightHook() {
            System.out.println("右勾拳");
        }

        private void legGo() {
            System.out.println("脚踢");
        }
    }

    static class Agent implements Boxer {
        private Boxer boxer;

        public Agent(Boxer boxer) {
            this.boxer = boxer;
        }

        @Override
        public void fight() {
            printLog();
            searchFighter();
            boxer.fight();
        }

        private void printLog() {
            System.out.println("打印日志功能");
        }

        private void searchFighter() {
            System.out.println("寻找合适的对手");
        }
    }

    public static void main(String[] args) {
        BoxerImpl boxer = new BoxerImpl();
        Agent agent = new Agent(boxer);
        agent.fight();
    }
}
