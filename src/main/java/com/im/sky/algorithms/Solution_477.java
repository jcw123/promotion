package com.im.sky.algorithms;

public class Solution_477 {

    public static void main(String[] args) {
        Solution_477 solution_477 = new Solution_477();
        System.out.println(solution_477.totalHammingDistance(new int[] {4,14,2}));
    }

    public int totalHammingDistance(int[] nums) {
        int len = nums.length;
        int[] sum = new int[31];
        for(int num : nums) {
            int i = 0;
            while (num != 0) {
                if((num & 1) == 1) {
                    sum[i] += 1;
                }
                i++;
                num >>= 1;
            }
        }
        int res = 0;
        for(int i = 0; i < 31; i++) {
            res += (sum[i]) * (len - sum[i]);
        }
        return res;
    }
}
