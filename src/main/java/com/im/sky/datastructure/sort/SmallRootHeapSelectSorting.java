package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 4:27
 * @Version 1.0
 *
 * 小根堆排序算法
 */
public class SmallRootHeapSelectSorting {

    public static void main(String[] args) {
        int[] a = {1,3,9,7,8,2,6,5,7,10,1};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int tmp;
        // 先初始化小根堆；自下到上
        for(int i = a.length / 2; i >=0; i--) {
            adjust(a, i, a.length);
        }
        //每次交换元素后，调整小根堆。自上到下
        for(int i = a.length - 1; i >= 1; i--) {
            tmp = a[0];
            a[0] = a[i];
            a[i] = tmp;
            adjust(a, 0, i);
        }

    }

    private static void adjust(int[] a, int s, int n) {
        int j = 2 * s + 1;
        int tmp = a[s];
        while(j < n) {
            if(j < n - 1 && a[j + 1] < a[j]) {
                j++;
            }
            if(a[j] < tmp) {
                a[s] = a[j];
                s = j;
                j = 2 * s + 1;
            }else {
                break;
            }
        }
        a[s] = tmp;
    }
}
