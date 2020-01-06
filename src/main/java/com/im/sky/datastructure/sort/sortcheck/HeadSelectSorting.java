package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

/**
 * 堆选择排序
 */
public class HeadSelectSorting {

    public static void main(String[] args) {
        int[] arr = new int[] {1,3,2,1,0,10,9,8};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        int len = arr.length;
        int j;
        for(j = (len - 2) / 2; j >= 0; j--) {
            adjust(arr, j, len - 1);
        }
        for(j = len - 1; j >= 0; j--) {
            swap(arr, 0, j);
            adjust(arr, 0, j - 1);
        }


    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 这个调整是再已满足堆定义的基础上的调整，所有有个初始化操作建立大根堆或者小根堆。
     * @param arr
     * @param s
     * @param n
     */
    private static void adjust(int[] arr, int s, int n) {
        int j = 2 * s + 1;
        int tmp = arr[s];
        while(j <= n) {
            if(j < n && arr[j + 1] > arr[j]) {
                j++;
            }
            if(arr[j] > tmp) {
                arr[s] = arr[j];
                s = j;
                j = 2 * s + 1;
            }else {
                break;
            }
        }
        arr[s] = tmp;
    }
}
