package com.im.sky.test;

import java.util.*;

class Solution {
    List<Integer>[] tree;
    int n;
    int[] coins;
    Map<String, Integer> mm = new HashMap<>();
    Map<String, Integer> pp = new HashMap<>();
    Map<String, Integer> tt = new HashMap<>();
    int ans = Integer.MAX_VALUE;
    int total = 0;
    public int collectTheCoins(int[] coins, int[][] edges) {
        this.coins = coins;
        n = coins.length;
        tree = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }
        for(int[] e : edges) {
            int u = e[0];
            int v = e[1];
            tree[u].add(v);
            tree[v].add(u);
        }
        for(int i = 0; i < n; i++) {
            dfs(i, -1, 0);
            ans = Math.min(ans, total);
            total = 0;
        }
        return ans;
    }



    private int count(int i, int p) {
        String key = p + "_" + i;
        if(pp.containsKey(key)) {
            return pp.get(key);
        }
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{i, p, 0});
        int ans = 0;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            if(cur[2] == 2) {
                ans += dfs3(cur[0], cur[1]);
            }else {
                List<Integer> child = tree[cur[0]];
                for(int ne : child) {
                    if(ne != cur[1]) {
                        q.offer(new int[]{ne, cur[0], cur[2] + 1});
                    }
                }
            }
        }
        return ans;
    }

    private int dfs3(int i, int p) {
        String key = p + "_" + i;
        if(tt.containsKey(key)) {
            return tt.get(key);
        }
        List<Integer> child = tree[i];
        int ans = 0;
        for(int ne : child) {
            if(ne != p) {
                ans += dfs3(i, ne);
            }
        }
        tt.put(key, ans);
        return ans;
    }

    private void dfs(int i, int p, int pLen) {
        List<Integer> child = tree[i];
        for(int ne : child) {
            if(ne != p) {
                if(count(i, p) == 0) {
                    ans += pLen * 2;
                }else {
                    dfs(ne, i, pLen + 1);
                }
            }
        }
    }
}
