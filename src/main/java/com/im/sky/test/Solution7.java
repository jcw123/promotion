package com.im.sky.test;

/**
 * @author jiangchangwei
 * @date 2020-8-2 下午 8:27
 *
 * [-12,-1,-4,11,-8,6,9,-5,-7,7,12,10]
 * 10
 **/
class Solution7 {
    public static void main(String[] args) {
        int v = new Solution7().kConcatenationMaxSum(new int[]{-12,-1,-4,11,-8,6,9,-5,-7,7,12,10}, 10);
        System.out.println(v);
    }
    public int kConcatenationMaxSum(int[] arr, int k) {
        int len = arr.length;
        int[] dp = new int[len * 2 + 1];
        int max = 0;
        int sum = 0;
        int tmp;
        int preMax = 0;
        int prefixMax = 0;
        int preSum = 0;
        int prefixSum = 0;
        for(int i = 1; i <= len; i++) {
            tmp = arr[i - 1];
            dp[i] = Math.max(tmp, tmp + dp[i - 1]);
            max = Math.max(max, dp[i]);
            sum += tmp;
            preSum += tmp;
            preMax = Math.max(preMax, preSum);
            prefixSum += arr[len - i];
            prefixMax = Math.max(prefixMax, prefixSum);
        }
        if(k == 1) {
            return max;
        }
        for(int i = len + 1; i <= 2 * len; i++) {
            tmp = arr[(i - 1) % len];
            dp[i] = Math.max(tmp, tmp + dp[i - 1]);
            max = Math.max(max, dp[i]);
        }
        if(sum <= 0 || k < 3) {
            return max;
        }
        if((max-preMax - prefixSum) / sum > k - 2) {
            return max;
        }
        int result = 0;
        for(int i = 1; i <= k - 2; i++) {
            result = (sum + result) % 1000000007;
        }
        result = (result + preMax) % 1000000007;
        result = (result + prefixMax) % 1000000007;
        return result;
    }
}
