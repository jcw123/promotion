package com.im.sky.test;

import java.util.Arrays;
import java.util.Random;

/**
 * @author jiangchangwei
 * @date 2020-5-26 下午 12:27
 **/
public class QuickSortTest {

    private static Random random = new Random();

    public static void main(String[] args) {
        int[] arr = {3,1,2,1,5,7,3,1,9,0,10,1,1,1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void sort(int[] arr) {
        if(arr == null || arr.length == 0) {
            return;
        }
        int len = arr.length;
        sort(arr, 0, len - 1);
    }

    private static void sort(int[] arr, int start, int end) {
        if(start >= end) {
            return;
        }
        int middle = getMiddle(arr, start, end);
        sort(arr, start, middle - 1);
        sort(arr, middle + 1, end);
    }

    private static int getMiddle(int[] arr, int start, int end) {
        if(start >= end) {
            return start;
        }
        int baseIndex = random.nextInt(end - start + 1) + start;
        int tmp = arr[baseIndex];
        arr[baseIndex] = arr[start];
        while(start < end) {
            while(start < end && arr[end] > tmp) end--;
            if(start < end) {
                arr[start] = arr[end];
            }
            while(start < end && arr[start] <= tmp) start++;
            if(start < end) {
                arr[end] = arr[start];
            }
        }
        arr[start] = tmp;
        return end;
    }
}
