package com.im.sky.datastructure.sort;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 6:04
 * @Version 1.0
 *
 * 归并排序
 *
 *采用递归方式写，归并排序写法还是挺简单的，
 * 还是快排稍微难理解些
 */
public class MergeSorting {

    public static void main(String[] args) {
        int[] a = {50, 36, 66,76,95,12,25,36};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int[] tmp  = new int[a.length];
        merge(a, 0, a.length-1, tmp);
    }

    private static void merge(int[] a, int lo, int hi, int[] tmp) {
        if(lo >= hi) {
            return;
        }
        int mid = (lo + hi) >> 1;
        merge(a, lo, mid, tmp);
        merge(a, mid + 1, hi, tmp);
        int firstStart = lo;
        int secondStart = mid + 1;
        int j = lo;
        while(firstStart <= mid && secondStart <= hi) {
            if(a[firstStart] < a[secondStart]) {
                tmp[j++] = a[firstStart++];
            }else {
                tmp[j++] = a[secondStart++];
            }
        }
        if(firstStart <= mid) {
            while(firstStart <= mid) {
                tmp[j++] = a[firstStart++];
            }
        }else {
            while(secondStart <= hi) {
                tmp[j++] = a[secondStart++];
            }
        }
        while(lo <= hi) {
            a[lo] = tmp[lo];
            lo++;
        }
    }
}
