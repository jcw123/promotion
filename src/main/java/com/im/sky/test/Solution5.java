package com.im.sky.test;

/**
 * @author jiangchangwei
 * @date 2020-7-9 下午 8:46
 **/
class Solution5 {
    public int minFallingPathSum(int[][] A) {
        int rows = A.length;
        int cols= A[0].length;
        int result = Integer.MAX_VALUE;
        int[][] dp = new int[rows + 1][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                int min = dp[i][j];
                if(j > 0) {
                    min = Math.min(min, dp[i][j-1]);
                }
                if(j < cols - 1) {
                    min = Math.min(min, dp[i][j + 1]);
                }
                dp[i + 1][j] = A[i][j] + min;
                if(i == rows - 1) {
                    result = Math.min(dp[i + 1][j], result);
                }
            }
        }
        return result;
    }
}
