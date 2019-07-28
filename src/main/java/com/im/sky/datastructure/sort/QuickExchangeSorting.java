package com.im.sky.datastructure.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 2:07
 * @Version 1.0
 *
 * 快排是对交换排序冒泡排序的一种改进
 */
public class QuickExchangeSorting {

    private static final Random random = new Random();

    public static void main(String[] args) {
        int[] a = {50,36,66,76,36,12,25,95};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        if(a == null || a.length < 2) {
            return;
        }
        int lo = 0;
        int hi = a.length - 1;
        sortInternal(a, lo, hi);
    }

    private static void sortInternal(int[] a, int lo, int hi) {
        if(lo >= hi) {
            return;
        }
        int mid = getMid(a, lo, hi);
        sortInternal(a, lo, mid - 1);
        sortInternal(a, mid + 1, hi);
    }

    private static int getMid(int[] a, int lo, int hi) {
        if(lo == hi) {
            return lo;
        }
        int pivot = random.nextInt(hi - lo + 1) + lo;
        swap(a, pivot, lo);
        int value = a[lo];
        while(lo < hi) {
            while(lo < hi && a[hi] >= value) {
                hi--;
            }
            a[lo] = a[hi];
            while(lo < hi && a[lo] <= value) {
                lo++;
            }
            a[hi] = a[lo];
        }
        a[lo] = value;
        return lo;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
