package com.im.sky.algorithms.knapsack;

import java.util.Arrays;

/**
 * complete knapsack
 * @author jiangchangwei
 * @date 2022/11/9 8:33 下午
 *
**/
public class CompleteKnapsack {

    public static void main(String[] args) {
        CompleteKnapsack a = new CompleteKnapsack();
        int[] v = {4, 2, 3, 4, 10};
        int[] w = {3, 2, 1, 4, 8};
        int m = 5;
        int V = 10;
        System.out.println(a.maxV(v, w, m, V));
        System.out.println(a.maxV2(v, w, m, V));
        System.out.println(a.maxV3(v, w, m, V));
    }

    private int maxV(int[] v, int[] w, int m, int V) {
        int[][] dp = new int[m + 1][V + 1];
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= V; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], v[i - 1] <= j ? dp[i][j - v[i - 1]] + w[i - 1] : 0);
            }
        }
        return dp[m][V];
    }

    private int maxV2(int[] v, int[] w, int m, int V) {
        int[][] memory = new int[m + 1][V + 1];
        for(int i = 0; i <= m; i++) {
            Arrays.fill(memory[i], -1);
        }
        return dfs2(m, V, memory, v, w);
    }

    private int maxV3(int[] v, int[] w, int m, int V) {
        int[] dp = new int[V + 1];
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= V; j++) {
                dp[j] = Math.max(dp[j], v[i - 1] <= j ? dp[j - v[i - 1]] + w[i - 1] : 0);
            }
        }
        return dp[V];
    }

    private int dfs2(int n, int v, int[][] memory, int[] vArr, int[] w) {
        if(memory[n][v] != -1) {
            return memory[n][v];
        }
        if(v == 0 || n == 0) {
            return 0;
        }
        int ans = dfs2(n - 1, v, memory, vArr, w);
        if(v >= vArr[n - 1]) {
            ans = Math.max(ans, w[n - 1] +  dfs2(n, v - vArr[n - 1], memory, vArr, w));
        }
        memory[n][v] = ans;
        return ans;
    }
}
