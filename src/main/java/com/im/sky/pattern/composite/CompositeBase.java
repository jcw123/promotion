package com.im.sky.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangchangwei on 2019/6/26.
 * 组合模式
 *结构型模式
 *
 * 树状结构是组合模式最直观的体现，将树叶节点和非叶子节点同等看待，树叶节点下是没有节点的
 *
 * 三个组要组件：
 * （1）树叶构件(Leaf)角色：树叶对象是没有下级子对象的对象，定义出参加组合的原始对象的行为。
 * （2）树枝构件(Composite)角色：代表参加组合的有下级子对象的对象
 * （3）抽象构件(Component)角色：这是一个抽象角色，它给参加组合的对象定义出公共的接口及其默认行为，可以用来管理所有的子对象。合
 * 成对象通常把它所包含的子对象当做类型为Component的对象。在安全式的合成模式里，构件角色并不定义出管理子对象的方法，这一定义由树枝构件对象给出。
 *
 **/
public class CompositeBase {

    static abstract class Component {
        abstract void say();

        void add(Component component) throws IllegalAccessException {
            throw new IllegalAccessException();
        }
    }

    static class Leaf extends Component {
        @Override
        void say() {
            System.out.println("i am leaf");
        }

        @Override
        public String toString() {
            return "i am leaf";
        }
    }

    static class Composite extends Component {

        List<Component> components = new ArrayList<>();

        @Override
        void say() {
            for(Component component : components) {
                System.out.println(component);
                component.say();
            }
        }

        @Override
        void add(Component component) throws IllegalAccessException {
            components.add(component);
        }

        @Override
        public String toString() {
            return "i am composite";
        }
    }

    public static void main(String[] args) throws Exception {
        Component component = new Composite();
        Component  component1 = new Composite();
        Component component2 = new Leaf();
        component.add(component1);
        component.add(component2);
        component.say();
    }
}
