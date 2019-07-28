package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-23 下午 8:50
 * @Version 1.0
 *
 * 直接插入排序
 */
public class InsertDirectSorting {

    public static void main(String[] args) {
        int[] a = {3,1,3,7,9,6,5,0};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length <= 1) {
            return;
        }
        for(int i = 1; i < a.length; i++) {
            int m = a[i];
            int j = i - 1;
            while(j >= 0 && m < a[j]) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = m;
        }
    }
}
