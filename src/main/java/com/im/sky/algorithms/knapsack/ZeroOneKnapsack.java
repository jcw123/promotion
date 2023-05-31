package com.im.sky.algorithms.knapsack;

import java.util.Arrays;

/**
 *
 * 0-1背包
 *
 * @author jiangchangwei
 * @date 2022/11/9 6:18 下午
 *
 * 有m个wupin，对于第i个物品，其体积为vi，价值为wi，有一个容量为V的背包，在总容量不超过背包大小的情况下，
 * 问如何放入物品能够使背包中总的物品价值最大
 *
**/
public class ZeroOneKnapsack {

    public static void main(String[] args) {
        ZeroOneKnapsack a = new ZeroOneKnapsack();
        int[] v = {1, 2, 3, 4, 5};
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
                dp[i][j] = Math.max(dp[i - 1][j], v[i - 1] <= j ? dp[i - 1][j - v[i - 1]] + w[i - 1] : 0);
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
            for(int j = V; j >= 1; j--) {
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
            ans = Math.max(ans, w[n - 1] +  dfs2(n - 1, v - vArr[n - 1], memory, vArr, w));
        }
        memory[n][v] = ans;
        return ans;
    }

}
