package com.im.sky.test;

/**
 * @author jiangchangwei
 * @date 2020-7-8 下午 9:01
 *
 * A、B，A先选，B随意，只要A能够一定结果最大就返回true
 **/
class Solution4 {
    public boolean stoneGame(int[] piles) {
        int sum = 0;
        int len = piles.length;
        int[][] dp = new int[len][len];
        for(int val : piles) {
            sum += val;
        }
        int max = maxSum(0, len - 1, sum, piles, dp);
        return 2 * max > sum;
    }

    private int maxSum(int start, int end, int sum, int[] piles, int[][] dp) {
        if(end == start) {
            return piles[start];
        }
        if(dp[start][end] != 0) {
            return dp[start][end];
        }
        int max = sum - Math.min(maxSum(start + 1,end, sum - piles[start], piles,dp),
                maxSum(start,end - 1,sum - piles[end],piles, dp));
        dp[start][end] = max;
        return max;
    }
}
