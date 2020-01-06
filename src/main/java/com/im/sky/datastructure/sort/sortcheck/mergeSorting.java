package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * 归并排序
 */
public class mergeSorting {

    public static void main(String[] args) {
        int[] arr = new int[] {1,3,9,3,5,3,2,0};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        sort(arr, 0, arr.length - 1, new int[arr.length]);

    }

    private static  void sort(int[] arr, int lo, int hi, int[] tmp) {
        if(lo >= hi) {
            return;
        }
        int mid = (lo + hi) >> 1;
        sort(arr, lo, mid, tmp);
        sort(arr, mid + 1, hi, tmp);
        merge(arr, tmp, lo, mid + 1, hi);
    }

    public static void merge(int[] arr, int[] tmp, int s, int m, int e) {
        int i = s;
        int j = m;
        int k = s;
        while(i <= m - 1 && j <= e) {
            if(arr[i] <= arr[j]) {
                tmp[k++] = arr[i++];
            }else {
                tmp[k++] = arr[j++];
            }
        }
        int r;
        if(i <= m - 1) {
            r = m - 1;
        }else {
            i = j;
            r = e;
        }
        while(i <= r) {
            tmp[k++] = arr[i++];
        }
        while(s <= e) {
            arr[s] = tmp[s];
            s++;
        }
    }
}
