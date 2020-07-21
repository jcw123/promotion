package com.im.sky.bugs;

import java.util.Arrays;
import java.util.List;

/**
 * @author jiangchangwei
 * @date 2020-5-13 下午 12:49
 **/
public class ToArrayBugs {
    public static void main(String[] args) {
        String[] s = {"t1", "t2"};
        System.out.println(Arrays.asList(s).getClass());

        List list = Arrays.asList(s);
        System.out.println(list.toArray());
        Object[] objects = list.toArray();
        // objects[0] = new Object();
        System.out.println(list.toArray(new Object[0]));
        objects = list.toArray(new Object[0]);
        objects[0] = new Object();
    }
}
