package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-10-26 上午 9:48
 * @Version 1.0
 */
public class BinaryInsertSorting {

    public static void main(String[] args) {
        int[] arr = new int[] {3,1,3,5,7,8,1,2};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length <= 1) {
            return;
        }
        int lo, hi, mid, j;
        for(int i = 1; i < arr.length; i++) {
            lo = 0;
            hi = i - 1;
            int tmp = arr[i];
            while(lo <= hi) {
                mid = (lo + hi) >> 1;
                if(tmp < arr[mid]) {
                    hi = mid - 1;
                }else {
                    lo = mid + 1;
                }
            }
            for(j = i - 1; j >= lo; j--) {
                arr[j + 1] = arr[j];
            }
            arr[lo] = tmp;
        }

    }
}
