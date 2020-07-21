package com.im.sky.test;

import java.util.Arrays;

/**
 * @author jiangchangwei
 * @date 2020-5-26 下午 1:13
 **/
public class MergeSortTest {
    public static void main(String[] args) {
        int[] arr = {3,1,2,4,1,3,6,7,9,1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        if(arr == null || arr.length < 2) {
            return;
        }
        int len = arr.length;
        int[] tmp = new int[len];
        sort(arr, 0, len - 1, tmp);
    }

    public static void sort(int[] arr, int start, int end, int[] tmp) {
        if(start >= end) {
            return;
        }
        int mid = (start + end) >> 1;
        sort(arr, start, mid, tmp);
        sort(arr, mid + 1, end, tmp);
        int firstStart = start;
        int secondStart = mid + 1;
        int k = start;
        while (firstStart <= mid && secondStart <= end) {
            if(arr[firstStart] < arr[secondStart]) {
                tmp[k++] = arr[firstStart++];
            }else {
                tmp[k++] = arr[secondStart++];
            }
        }
        while (firstStart <= mid) {
            tmp[k++] = arr[firstStart++];
        }
        while (secondStart <= end) {
            tmp[k++] = arr[secondStart++];
        }
        while (start <= end) {
            arr[start] = tmp[start];
            start++;
        }

    }
}
