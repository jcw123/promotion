package com.im.sky.algorithms;

import java.util.Arrays;

public class Solution_1049_V2 {

    public int lastStoneWeightII(int[] stones) {
        int len = stones.length;
        if(len == 1) {
            return stones[0];
        }
        int sum = Arrays.stream(stones).sum();
        int[] dp = new int[sum / 2 + 1];
        for(int i = 0; i < len; i++) {
            int stone = stones[i];
            for(int j = sum / 2; j >= stone; j--) {
                dp[j] = Math.max(dp[j], dp[j - stone] + stone);
            }
        }
        return sum - 2 * dp[sum / 2];
    }
}
