package com.im.sky.datastructure.sequencelist.chainlist;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-7 下午 1:14
 * @Version 1.0
 * 静态链表
 * 使用顺序表实现链式存储结构，一个元素包含数据本身和后继元素的索引位置
 */
public class StaticChainList {

    private Element[] cArray;

    private int unUsedHeadIndex = 0;

    public static void main(String[] args) {
        StaticChainList staticChainList = new StaticChainList();
        staticChainList.init(5);
        staticChainList.addBefore('a', 1);
        staticChainList.addBefore('b', 2);
        staticChainList.addBefore('c', 3);
        staticChainList.addBefore('d', 2);
        System.out.println(Arrays.toString(staticChainList.cArray));
    }

    private void init(int size) {
        cArray = new Element[size];
        cArray[0] = new Element(' ', 1);
        for(int i = 1; i < size - 1; i++) {
            cArray[i] = new Element(' ', i + 1);
        }
        cArray[size - 1] =  new Element(' ', 0);
        unUsedHeadIndex = 1;

    }

    /**
     * 表示在第i个元素之前插入元素
     * @param c
     * @param i
     */
    public void addBefore(char c, int i) {
        if(unUsedHeadIndex == 0) {
            throw new RuntimeException("no free space");
        }
        int index = unUsedHeadIndex;
        unUsedHeadIndex =  cArray[unUsedHeadIndex].nextIndex;
        int j = 0;
        int m = 0;
        while(j++ < i - 1) {
            m = cArray[m].nextIndex;
            if(m == 0) {
                throw new RuntimeException();
            }
        }
        cArray[index].c = c;
        cArray[index].nextIndex = cArray[m].nextIndex;
        cArray[m].nextIndex = index;

    }

    /**
     * 表示删除第i个元素
     * @param i
     */
    public void remove(int i) {
        int j = 0;
        int m = 0;
        while(j++ < i - 1) {
            m = cArray[m].nextIndex;
            if(m == 0) {
                throw new RuntimeException();
            }
        }
        int k = cArray[m].nextIndex;
        cArray[k].nextIndex = unUsedHeadIndex;
        unUsedHeadIndex = k;
        cArray[m].nextIndex = cArray[k].nextIndex;
    }

    private static class Element {
        char c;
        int nextIndex;
        public Element(char c, int nextIndex) {
            this.c = c;
            this.nextIndex = nextIndex;
        }

        public Element() {

        }

        @Override
        public String toString() {
            return "Element{" +
                    "c=" + c +
                    ", nextIndex=" + nextIndex +
                    '}';
        }
    }
}
