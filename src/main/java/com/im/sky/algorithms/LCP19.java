package com.im.sky.algorithms;

public class LCP19 {

    public static void main(String[] args) {
        LCP19 lcp19 = new LCP19();
        System.out.println(lcp19.minimumOperations("yyyyryyyy"));
    }

    public int minimumOperations(String leaves) {
        int len = leaves.length();
        // dp[0][x] only r
        // dp[1][x] for rrrryyyy
        // dp[2][x] for rrryyyyxxxx
        int[][] dp = new int[3][len + 1];
        boolean isR;
        for(int i = 0; i < len; i++) {
            isR = leaves.charAt(i) == 'r';
            dp[0][i + 1] = (isR?0:1) + dp[0][i];
            dp[1][i + 1] = Integer.MAX_VALUE;
            if(i > 0) {
                dp[1][i + 1] = Math.min(dp[1][i + 1], dp[0][i] + (isR?1:0));
            }
            if(i > 1) {
                dp[1][i + 1] = Math.min(dp[1][i + 1], dp[1][i] + (isR?1:0));
            }
            dp[2][i + 1] = Integer.MAX_VALUE;
            if(i > 1) {
                dp[2][i + 1] = Math.min(dp[2][i + 1], dp[1][i]  + (isR?0:1));
            }
            if(i > 2) {
                dp[2][i + 1] = Math.min(dp[2][i + 1], dp[2][i]  + (isR?0:1));
            }
        }
        return dp[2][len] == Integer.MAX_VALUE ? 0: dp[2][len];
    }
}
