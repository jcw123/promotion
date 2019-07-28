package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 1:46
 * @Version 1.0
 *
 * 冒泡排序：交换排序的一种
 *
 * 经过一趟遍历后能够确定一个元素排序的最终位置
 *
 * 时间复杂度O（N^2）
 */
public class BubbleExchangeSorting {

    public static void main(String[] args) {
        int[] a = {1,3,2,9,7,2,6,4};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int n = a.length;

        for(int i = n - 1; i >= 0; i--) {
            boolean isSwap = false;
            for(int j = 0; j < i; j++) {
                if(a[j] > a[j + 1]) {
                    isSwap = true;
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
            if(!isSwap) {
                break;
            }
        }
    }
}
