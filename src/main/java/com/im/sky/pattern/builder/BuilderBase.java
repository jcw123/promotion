package com.im.sky.pattern.builder;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 2:16
 * @Version 1.0
 *
 * 建造者模式：创建型模式
 *
 * 基本构件：
 * （1）抽象构建者角色（Builder）
 * （2）具体构建者角色（ConcreteBuilder）
 * （3）导演者角色（Director）
 * （4）产品角色（Product）
 *
 * 注：具体构建者不止一个，但是这些构建产品的基本步骤很相似。
 *
 *用例：java中的StringBuilder和StringBuffer是最常见的建造者模式例子。
 * 一个建造者不能同时出现这多个线程中，否则可能会引起线程安全问题
 *
 * 核心问题：一个对象并不是简简单单就能创建出来了，通过一个建造者，控制建造的过程，
 * 有必要的话也可以引入一个导演者角色，控制建造的过程。
 */
public class BuilderBase {

    public static void main(String[] args) {
        AbstractBuilder builder = new JDBuilder();
        Production production = builder.buildA("1").buildB("2").build();
        System.out.println(production);
    }

    /**
     * 感觉这个对象有时是多余的
     */
    static class Director {

        private AbstractBuilder builder;

        public Director(AbstractBuilder builder) {
            this.builder = builder;
        }

        public Production direct() {
            return builder.buildB("a").buildA("b").build();
        }
    }

    abstract static class AbstractBuilder {

        private String partA;

        private String partB;

        public abstract AbstractBuilder buildA(String partA);

        public abstract AbstractBuilder buildB(String partB);

        public abstract Production build();

        public String getPartA() {
            return partA;
        }

        public String getPartB() {
            return partB;
        }

        public void setPartA(String partA) {
            this.partA = partA;
        }

        public void setPartB(String partB) {
            this.partB = partB;
        }
    }

    static class JDBuilder extends AbstractBuilder {

        @Override
        public JDBuilder buildA(String partA) {
            this.setPartA(partA);
            return this;
        }

        @Override
        public JDBuilder buildB(String partB) {
            this.setPartB(partB);
            return this;
        }

        @Override
        public Production build() {
            return new Production(this);
        }
    }

    static class Production {
        private String partA;

        private String partB;

        public Production(AbstractBuilder builder) {
            this.partA = builder.partA;
            this.partB = builder.partB;
        }

        public String getPartA() {
            return partA;
        }

        public void setPartA(String partA) {
            this.partA = partA;
        }

        public String getPartB() {
            return partB;
        }

        public void setPartB(String partB) {
            this.partB = partB;
        }

        @Override
        public String toString() {
            return new StringBuilder().append("partA:").append(partA)
                    .append("|partB:").append(partB).toString();
        }
    }
}
