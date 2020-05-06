package com.im.sky.collection;

import com.im.sky.collection.domain.People;

import java.util.TreeSet;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 4:08
 *
 * hashSet 会和对象的equals和hashCode方法有关
 * treeSet 判断对象是否相等只和Comparable 和Comparator有关
 **/
public class TreeSetTest {

    public static void main(String[] args) {
        TreeSet<People> set = new TreeSet<>();
        set.add(new People(0, 2, "zhangsan"));
        set.add(new People(0, 3, "lisi"));
        set.add(new People(0, 3, "lisi2"));
        System.out.println(set.size());
        System.out.println(set.first().getName());
    }
}
