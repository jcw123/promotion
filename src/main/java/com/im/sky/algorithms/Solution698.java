package com.im.sky.algorithms;

import com.alibaba.fastjson.JSON;

public class Solution698 {

    public static void main(String[] args) {
        int[] arr = new int[]{2,2,2,2,3,4,5};
        int k = 4;
        System.out.println(new Solution698().canPartitionKSubsets(arr, k));
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        int size = nums.length;
        for(int v : nums) {
            sum += v;
        }
        if(sum % k != 0) {
            return false;
        }
        if(k == 1) {
            return true;
        }
        int ave = sum / k;
        for(int v : nums) {
            if(v > ave) {
                return false;
            }
        }
        int[] sums = new int[1 << size];
        for(int i = 1; i < (1 << size); i++) {
            sums[i] = sums[i & (i - 1)] + nums[Integer.numberOfTrailingZeros(i)];
        }
        System.out.println(JSON.toJSONString(sums));
        boolean[][] dp = new boolean[1 << size][k + 1];
        dp[0][0] = true;
        for(int i = 1; i < (1 << size); i++) {
            for(int j = 1; j <= k; j++) {
                for(int m = 0; m < size; m++) {
                    if((i & (1 << m)) != 0) {
                        if(sums[i ^ (1 << m)] / ave == j - 1) {
                            dp[i][j] = dp[i][j] || dp[i ^ (1 << m)][j - 1];
                        }
                        if(sums[i ^ (1 << m)] % ave + nums[m] <= ave && sums[i ^ (1 << m)] / ave == j - 1) {
                            dp[i][j] = dp[i][j] || dp[i ^ (1 << m)][j];
                        }
                    }
                }
            }
        }
        return dp[(1 << size) - 1][k];
    }
}
