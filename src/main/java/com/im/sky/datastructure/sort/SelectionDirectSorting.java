package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 3:50
 * @Version 1.0
 *
 * 直接选择排序
 */
public class SelectionDirectSorting {

    public static void main(String[] args) {
        int[] a = {1,9,8,7,4,2,3,9,3};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int n = a.length;
        for(int i = 0; i < n - 1; i++) {
            int minIndex = i;
            int min = a[i];
            for(int j = i + 1; j < n; j++) {
                if(a[j] < min) {
                    min = a[j];
                    minIndex = j;
                }
            }
            swap(a, i, minIndex);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
