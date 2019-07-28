package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 上午 10:58
 * @Version 1.0
 *
 * 折半插入排序
 *
 * 相对于直接插入排序，折半插入查找待插入的位置速度更快
 */
public class BinaryInsertSorting {

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,5,3,2};
        sort(a);
        System.out.println(Arrays.toString(a));

    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int n = a.length;
        int lo;
        int hi;
        for(int i = 1; i < n; i++) {
            if(a[i] >= a[i - 1]) {
                continue;
            }
            lo = 0;
            hi = i - 1;
            int mid;
            while(lo < hi) {
                mid = (lo + hi) >> 1;
                if(a[i] > a[mid]) {
                    lo = mid + 1;
                }else if(a[i] < a[mid]) {
                    hi = mid- 1;
                }else {
                    lo = mid;
                    break;
                }
            }
            if(a[i] > a[lo]) {
                lo++;
            }
            int tmp = a[i];
            for(int j = i - 1; j >= lo; j--) {
                a[j + 1] = a[j];
            }
            a[lo] = tmp;

        }
    }

    public static void sort2(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int lo, hi;
        for(int i = 1; i < a.length; i++) {
            lo = 0;
            hi = i - 1;
            while(lo <= hi) {
                int mid = (lo + hi) >> 1;
                if(a[i] >= a[mid]) {
                    lo = mid + 1;
                }else {
                    hi = mid - 1;
                }
            }
            int tmp = a[i];
            for(int j = i - 1; j >= lo; j--) {
                a[j + 1] = a[j];
            }
            a[lo] = tmp;
        }
    }
}
