package com.im.sky.pattern.strategy;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 8:28
 * @Version 1.0
 *
 * 策略模式：行为型模式
 *
 * 基本构件：
 * （1）上下文类（Context）：拥有抽象策略的引用，决定具体使用哪种策略
 * （2）Strategy（抽象策略类）
 * (3)ConcreteStrategy(具体策略类)
 *
 * 具体策略类是平等的，分离了使用和定义
 */
public class StrategyBase {

    public static void main(String[] args) {
        HomeContext context = new HomeContext();
        context.setStrategy(new BikeHomeGo());
        context.execute();
    }

    static class HomeContext {
        private HomeGoStrategy strategy;

        public HomeGoStrategy getStrategy() {
            return strategy;
        }

        public void setStrategy(HomeGoStrategy strategy) {
            this.strategy = strategy;
        }

        public void execute() {
            strategy.start();
        }
    }

    static interface HomeGoStrategy {
         void start();
    }

    static class WalkHomeGo implements HomeGoStrategy {
        @Override
        public void start() {
            System.out.println("走回家，花费时间30分钟");
        }
    }

    static class BikeHomeGo implements HomeGoStrategy {
        @Override
        public void start() {
            System.out.println("骑行回家，仅仅花费了15分钟");
        }
    }
}
