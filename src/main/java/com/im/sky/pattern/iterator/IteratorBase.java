package com.im.sky.pattern.iterator;


import java.util.ArrayList;

/**
 * Created by jiangchangwei on 2019/6/30.
 *
 * 迭代子模式：行为性模式
 *
 * 基本构件：
 * （1）抽象迭代子
 * （2）具体迭代子
 * （3）抽象聚集(aggregate)
 * （4）具体聚集
 *
 * 迭代的对象，可以直接在迭代过程修改聚集元素本身，则称为动态迭代子
 * 如果迭代过程不能够修改聚集元素本身，则称之为静态迭代子
 *
 * 典型的应用：Java中的集合类，Collection的子类
 *
 *
 * 目的：为了将具体元素本身和迭代过程分离开，可以让迭代子独立的变化。使迭代子成为客户端和聚集元素的中间层
 *
 * 迭代过程逻辑可能会有多种可能，将这部分易变的逻辑从聚集对象中提取出来，让其和客户端打交道
 *
 *
 *
 */
public class IteratorBase {

    public static void main(String[] args) {
        ConcreteAggregate<String> datas = new ConcreteAggregate<>(10);
        datas.add("1");
        datas.add("2");
        datas.add("3");
        Iterator iterator = datas.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    static interface Iterator<T> {
        boolean hasNext();

        T next();

    }

    static class ConcreteIterator<T> implements Iterator<T> {

        private ConcreteAggregate<T> concreteAggregate;

        private int cursor;

        private int size;

        public  ConcreteIterator(ConcreteAggregate<T> concreteAggregate) {
            this.concreteAggregate = concreteAggregate;
            this.size = concreteAggregate.size;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            return (T)concreteAggregate.elements[cursor++];
        }
    }

    static interface Aggregate {
        Iterator iterator();
    }

    static class ConcreteAggregate<T> implements Aggregate {


        Object[] elements;

        public ConcreteAggregate(int size) {
            if(size < 0) {
                throw new IllegalArgumentException();
            }else {
                elements = new Object[size];
                len = size;
            }
        }

        /**
         * 元素的个数
         */
        private int size;

        private int len;

        public boolean add(T element) {
            if(size >= len) {
                throw new OutOfMemoryError();
            }
            elements[size++] = element;
            return true;
        }

        @Override
        public Iterator iterator() {
            return new ConcreteIterator(this);
        }

        private T elementData(int i) {
            return (T)elements[i];
        }




    }


}

