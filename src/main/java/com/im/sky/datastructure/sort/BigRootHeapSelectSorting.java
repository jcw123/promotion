package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 4:04
 * @Version 1.0
 *
 * 堆排序算法
 *
 * 包括大根堆和小根堆(由完全二叉树构成)
 *
 * a(i) >= a(2 * i)
 * a(i) >= a(2 * i + 1)
 */
public class BigRootHeapSelectSorting {

    public static void main(String[] args) {
        int[] a = {1,3,9,7,8,2,6,5,7,10,1};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        int i;
        int tmp;
        //通过这个循环就可以将树调整为大根堆
        for(i = a.length / 2; i >= 0; i--) {
            adjust(a, i, a.length);
        }
        for(i = a.length - 1; i >= 1; i--) {
            tmp = a[0];
            a[0] = a[i];
            a[i] = tmp;
            adjust(a, 0, i);
        }
    }

    /**
     * 将以s为跟节点的树调整为大根堆。
     * @param a
     * @param s
     * @param n
     */
    private static void adjust(int[] a, int s, int n) {
        int tmp = a[s];
        int j = 2 * s + 1;
        while(j < n) {
            if(j < n - 1 && a[j + 1] > a[j]) {
                j++;
            }
            if(a[j] > tmp) {
                a[s] = a[j];
                s = j;
                j = 2 * s + 1;
            }else {
                break;
            }
        }
        a[s] =  tmp;
    }
}
