package com.im.sky.datastructure.sort.sortcheck;

import java.util.Arrays;

public class ShellInsertSorting {

    public static void main(String[] args) {
        int[] arr = new int[] {3,1,2,5,9,1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length <= 1){
            return;
        }

        int d;
        d = arr.length / 2;
        int len = arr.length;
        int i, j;
        while(d >= 1) {
            for(i = d; i < len; i++) {
                int tmp = arr[i];
                j = i - d;
                while(j >= 0 && arr[j] > tmp) {
                    arr[j + d] = arr[j];
                    j -= d;
                }
                arr[j + d] = tmp;
            }
            d /= 2;
        }
    }
}
