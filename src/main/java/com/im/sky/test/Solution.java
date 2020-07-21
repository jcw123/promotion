package com.im.sky.test;

/**
 * @author jiangchangwei
 * @date 2020-6-17 下午 9:11
 *
 * Serve 100 ml of soup A and 0 ml of soup B
 * Serve 75 ml of soup A and 25 ml of soup B
 * Serve 50 ml of soup A and 50 ml of soup B
 * Serve 25 ml of soup A and 75 ml of soup B
 *
 * Input:
 * 101
 * Output:
 * 0.69531
 * Expected:
 * 0.74219
 **/
class Solution {

    double[][] memo = new double[200][200];

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.f(101, 101));
        System.out.println(solution.soupServings(101));
    }

    public double f(int a, int b) {
        if (a <= 0 && b <= 0) return 0.5;
        if (a <= 0) return 1;
        if (b <= 0) return 0;
        if (memo[a][b] > 0) return memo[a][b];
        memo[a][b] = 0.25 * (f(a - 100, b) + f(a - 75, b - 25) + f(a - 50, b - 50) + f(a - 25, b - 75));
        return memo[a][b];
    }

    public double soupServings(int N) {
        if(N == 0) {
            return 0.5;
        }
        double[][] dp = new double[N + 1][N + 1];
        dp[0][0] = 0.5;
        int i,j;
        for(i = 1 ; i <= N ; i++) {
            dp[0][i] = 1;
        }
        for(i = 1; i <= N; i++) {
            for(j = 1; j <= N; j++) {
                dp[i][j] += 0.25 * dp[Math.max(0, i - 100)][j];
                dp[i][j] += 0.25 * dp[Math.max(0, i - 75)][Math.max(0, j - 25)];
                dp[i][j] += 0.25 * dp[Math.max(0, i - 50)][Math.max(0, j - 50)];
                dp[i][j] += 0.25 * dp[Math.max(0, i - 25)][Math.max(0, j - 75)];
            }
        }
        return dp[N][N];
    }
}
