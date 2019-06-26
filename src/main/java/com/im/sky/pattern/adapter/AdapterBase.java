package com.im.sky.pattern.adapter;

/**
 * Created by jiangchangwei on 2019/6/26.
 * 最基本的适配器模式举例
 * 结构性模式
 *
 * 适配器分配对象适配器和类适配器
 *
 *对象适配器：通过组合的方式实现
 *优点：一个对象适配器可以把多个不同的适配者适配到同一个目标，也就是说，同一个适配器可以把适配者类和它的子类都适配到目标接口。
 *缺点：与类适配器模式相比，要想置换适配者类的方法就不容易。如果一定要置换掉适配者类的一个或多个方法，就只好先做一个适配者类的子类，将适配者类的方法置换掉，
 * 然后再把适配者类的子类当做真正的适配者进行适配，实现过程较为复杂。
 *
 *
 *
 *类适配器：通过继承的方式实现
 * 优点：由于适配器类是适配者类的子类，因此可以在适配器类中置换一些适配者的方法，使得适配器的灵活性更强。
 * 缺点：一次最多只能适配一个适配者类，而且目标抽象类只能为抽象类，不能为具体类，其使用有一定的局限性，不能将一个适配者类和它的子类都适配到目标接口。
 *
 *
 * 缺省适配器：给目标接口都提供一个空实现，然后通过继承缺省适配器实现需要实现的方法。
 */
public class AdapterBase {

    private Target target;

    public AdapterBase(Target target) {
        this.target = target;
    }

    public void say() {
        target.say();
    }


    static interface Man {
        void say();
    }

    static class Dad implements Man {
        @Override
        public void say() {
            System.out.println("我是你爸爸");
        }
    }


    static interface Target {
        void say();
    }

    static class Adapter implements Target {

        private Man man;

        Adapter(Man man) {
            this.man = man;
        }

        @Override
        public void say() {
            man.say();
        }
    }

    public static void main(String[] args) {
        new AdapterBase(new Adapter(new Dad())).say();
    }


}
