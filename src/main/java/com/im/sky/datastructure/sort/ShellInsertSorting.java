package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 12:33
 * @Version 1.0
 *
 * 希尔排序
 *
 * 另一种插入排序方式，比直接插入排序、折半插入排序、链表插入排序时间复杂度低一些
 */
public class ShellInsertSorting {

    public static void main(String[] args) {
        int[] a = {1, 3, 7, 9, 10, 1, 2};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int d = a.length / 2;
        while(d >= 1) {
            for(int i = d; i < a.length; i++) {
                int j = i - d;
                int tmp = a[i];
                while(j >= 0 && a[j] >= a[i]) {
                    a[j + d] = a[j];
                    j = j - d;
                }
                a[j + d] = tmp;
            }
            d /= 2;
        }
    }
}
