package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * @Author: jiangcw
 * @Date: 2019-10-26 上午 9:38
 * @Version 1.0
 */
public class DirectInsertSorting {

    public static void main(String[] args) {
        int[] arr = new int[] {2,2,1,5,9,10,3,15,24};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void sort(int[] arr) {
        if(arr == null || arr.length <= 1) {
            return;
        }
        int tmp;
        int j;
        for(int i = 1; i < arr.length; i++) {
            j = i - 1;
            tmp = arr[i];
            while (j >= 0 && tmp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tmp;
        }
    }
}
