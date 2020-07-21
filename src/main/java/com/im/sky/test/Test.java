package com.im.sky.test;

import java.lang.annotation.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println(new Test().mctFromLeafValues(new int[]{6,2,4}));
    }

    public int mctFromLeafValues(int[] arr) {
        int len = arr.length;
        int[][] dp = new int[len][len];
        int[][] product = new int[len][len];
        for(int i = 0; i < len; i++) {
            int p = 1;
            for(int j = i; j < len; j++) {
                p = p * arr[j];
                product[i][j] = p;
            }
        }
        return reverse(dp, product, 0, len - 1);
    }

    private int reverse(int[][] dp, int[][] product, int start, int end) {
        if(start >= end) {
            return 0;
        }
        if(dp[start][end] != 0) {
            return dp[start][end];
        }
        int sum;
        if(end -start == 1) {
            sum = product[start][end];
        }else {
            int min = Integer.MAX_VALUE;
            for(int i = start; i < end; i++) {
                int leftSum = reverse(dp, product, start, i);
                int rightSum = reverse(dp, product, i + 1, end);
                min = Math.min(min, leftSum + rightSum);
            }
            sum = min + product[start][end];
        }
        dp[start][end] = sum;
        return dp[start][end];
    }
}
