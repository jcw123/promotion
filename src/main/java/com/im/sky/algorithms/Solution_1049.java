package com.im.sky.algorithms;

import java.util.Arrays;

public class Solution_1049 {

    public static void main(String[] args) {
        Solution_1049 solution_1049 = new Solution_1049();
        System.out.println(solution_1049.lastStoneWeightII(new int[]{1,2}));
    }

    public int lastStoneWeightII(int[] stones) {
        int len = stones.length;
        if(len == 1) {
            return stones[0];
        }
        int sum = Arrays.stream(stones).sum();
        int max = 0;
        boolean isOdd = (sum % 2 != 0);
        Arrays.sort(stones);
        int i = 0;
        while(max < (sum-1) / 2 + 1) {
            max += stones[i++];
        }
        int start = (isOdd?1:0);
        for(i = start; i <= (2 * max - sum); i += 2) {
            if(isFindSequenceEqualsTarget(stones, (i + sum) / 2)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isFindSequenceEqualsTarget(int[] stones, int target) {
        int[] dp  = new int[target + 1];
        dp[0] = 1;
        for(int val : stones) {
            for(int i = target; i >= val; i--) {
                dp[i] += dp[i - val];
                if(i == target && dp[target] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
