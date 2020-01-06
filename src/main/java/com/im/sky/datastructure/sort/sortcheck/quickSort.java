package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * 快排
 */
public class quickSort {

    public static void main(String[] args) {
        int[] arr = new int[] {3,2,1,5,9,10,3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        sort(arr, 0, arr.length - 1);

    }

    private static void sort(int[] arr, int lo, int hi) {
        if(lo >= hi) {
            return;
        }
        int middle = getMiddle(arr, lo, hi);
        sort(arr, lo, middle - 1);
        sort(arr, middle + 1, hi);
    }

    private static int getMiddle(int[] arr, int lo, int hi) {
        if(lo >= hi) {
            return lo;
        }

        int val = arr[lo];
        while(lo < hi) {
            while(lo < hi && arr[hi] >= val) hi--;
            if(lo < hi) {
                arr[lo] = arr[hi];
            }
            while(lo < hi && arr[lo] <= val) lo++;
            if(lo < hi) {
                arr[hi] = arr[lo];
            }

        }
        arr[lo] = val;
        return lo;

    }
}
