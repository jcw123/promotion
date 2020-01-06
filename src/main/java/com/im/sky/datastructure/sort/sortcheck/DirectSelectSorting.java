package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * 直接选择排序
 */
public class DirectSelectSorting {

    public static void main(String[] args) {
        int[] arr = new int[] {1,3,5,2,1,4,2,9,7};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;
        int i, j, k;
        for(i = 0; i < len - 1; i++) {
            k = i;
            for(j = i + 1; j < len; j++) {
                if(arr[j] < arr[k]) {
                    k = j;
                }
            }
            if(i != k) {
               swap(arr, i, k);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
