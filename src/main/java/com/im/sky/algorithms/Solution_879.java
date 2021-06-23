package com.im.sky.algorithms;

/**
 * 1 <= n <= 100
 * 0 <= minProfit <= 100
 * 1 <= group.length <= 100
 * 1 <= group[i] <= 100
 * profit.length == group.length
 * 0 <= profit[i] <= 100
 *
 * 10
 * 5
 * [2,3,5]
 * [6,7,8]
 *
 * 64
 * 0
 * [80, 40]
 * [88, 88]
 */
class Solution_879 {

    public static void main(String[] args) {
        Solution_879 solution_879 = new Solution_879();
        System.out.println(solution_879.profitableSchemes(64,0,new int[]{80,40}, new int[]{88,88}));
    }

    public static final int BASE = 1000000007;

    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int len = group.length;
        int[][][] dp = new int[n + 1][len + 1][minProfit + 1];
        for(int i = group[0]; i <= n; i++) {
            dp[i][1][0] = 1;
        }
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= len; j++) {
                for(int k = 0; k <= minProfit; k++) {
                    dp[i][j][k] = dp[i][j - 1][k] + dp[Math.max(i - group[j-1], 0)][j-1][Math.max(k - profit[j-1], 0)] + (i >= group[j-1] && profit[j-1]>=k ?1:0);
                    dp[i][j][k] %= BASE;
                }
            }
        }
        return dp[n][len][minProfit] + (minProfit == 0 ? 1 : 0);
    }
}
