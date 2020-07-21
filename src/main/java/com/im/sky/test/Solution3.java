package com.im.sky.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-7-6 下午 6:34
 **/
class Solution3 {

    public static void main(String[] args) {
        int[] m = {1,2,3,4,5,6,7,8};
        System.out.println(new Solution3().lenLongestFibSubseq(m));
    }

    public int lenLongestFibSubseq(int[] A) {
        int len = A.length;
        int[][] dp = new int[len][len];
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(A[0], 0);
        map.put(A[1], 1);
        for(int i = 2; i < len; i++) {
            for(int j = i - 1; j > 0; j--) {
                int val = A[j];
                int remain = A[i] - val;
                if(remain < val) {
                    if(map.containsKey(remain)) {
                        int tmp = dp[j][map.get(remain)];
                        dp[i][j] = 1 + (tmp > 0 ? tmp : 2);
                        result = Math.max(result, dp[i][j]);
                    }
                }else {
                    break;
                }
            }
            map.put(A[i], i);
        }
        return result;
    }
}
