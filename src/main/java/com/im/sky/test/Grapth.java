package com.im.sky.test;

import java.util.Arrays;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-15 23:21
 **/
class Graph {
    int n;
    int[][] grid;
    int inf = 0x3f3f3f3f;
    int[][] dp;
    public Graph(int n, int[][] edges) {
        this.n = n;
        this.grid = grid;
        grid = new int[n][n];
        dp = new int[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(grid[i], inf);
            Arrays.fill(dp[i], inf);
        }
        for(int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }
        for(int[] e : edges) {
            int u = e[0];
            int v = e[1];
            int w = e[2];
            grid[u][v] = w;
        }
        for(int p = 0; p < n; p++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(dp[i][p] != inf && dp[p][j] != inf) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][p] + dp[p][j]);
                    }
                }
            }
        }
    }

    public void addEdge(int[] edge) {
        int u = edge[0];
        int v = edge[1];
        int w = edge[2];
        for(int i = 0; i < n; i++) {
            dp[u][i] = Math.min(dp[u][i], w + dp[v][i]);
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                dp[i][j] = Math.min(dp[i][j], dp[i][u] + dp[u][j]);
            }
        }
    }

    public int shortestPath(int n1, int n2) {
        if(dp[n1][n2] >= inf) {
            return -1;
        }
        return dp[n1][n2];
    }
}

/**
 * Your Graph object will be instantiated and called as such:
 * Graph obj = new Graph(n, edges);
 * obj.addEdge(edge);
 * int param_2 = obj.shortestPath(node1,node2);
 */
