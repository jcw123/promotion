package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubSort {

    public static void main(String[] args) {
        int[] arr = new int[] {1,3,5,8,3,2,1,10,7};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }

        for(int i = 0; i < arr.length - 1; i++) {
            boolean isChange = false;
            for(int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    isChange = true;
                }
            }
            if(!isChange) {
                break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
