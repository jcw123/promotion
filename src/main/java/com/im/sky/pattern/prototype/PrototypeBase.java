package com.im.sky.pattern.prototype;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 3:24
 * @Version 1.0
 *
 * 原型模式：创建型模式
 *
 * java中天生支持原型模式，通过实现Cloneable接口，重写clone方法，实现拷贝
 * java中的clone方法默认的拷贝是浅拷贝，如果想实现深拷贝，需要重写clone方法。
 *
 * 注：使用原型模式可以将当前对象的状态复制到新创建的对象上。
 */
public class PrototypeBase {

    public static void main(String[] args) throws CloneNotSupportedException {
        M m = new M();
        m.elementA = new ElementA();
        m.elementB = new ElementB();
        M cloneObject = (M)m.clone();
        System.out.println(m.elementA == cloneObject.elementA);
        System.out.println(m.elementB == cloneObject.elementB);
        System.out.println(m == cloneObject);
        ElementA elementA = new ElementA();
        ElementB elementB = new ElementB();
        elementB.clone();
    }

    static class M implements Cloneable {
        private ElementA elementA;

        private ElementB elementB;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            M m =  (M)super.clone();
            m.elementB = new ElementB();
            return m;
        }
    }

    static class ElementA implements Cloneable {

        String a;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return new ElementA();
        }
    }

    static class ElementB implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
