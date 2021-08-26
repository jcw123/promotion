package com.im.sky.algorithms;

import java.util.*;

public class Solution_1916 {

    private static final int BASE = 1000000007;

    private Map<Integer, Set<Integer>> neighbors = new HashMap<>();

    public int waysToBuildRooms(int[] prevRoom) {
        int n = prevRoom.length;
        int[] deg = new int[n];
        for(int i = 0; i < n; i++) {
            if(prevRoom[i] < 0) continue;
            Set<Integer> set = neighbors.computeIfAbsent(prevRoom[i], k -> new HashSet<>());
            set.add(i);
            deg[prevRoom[i]]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        int[] dp = new int[n];
        int[] sizes = new int[n];
        Arrays.fill(sizes, 1);
        for(int i = 0; i < n; i++) {
            if(deg[i] == 0) {
                queue.offer(i);
            }
        }
        int[] fac = new int[n + 1];
        Arrays.fill(fac, 1);
        int[] ifac = new int[n + 1];
        Arrays.fill(ifac, 1);
        for(int i =2; i <= n; i++) {
            fac[i] = (int)((long) i * fac[i - 1] % BASE);
        }
        for(int i = 2; i <= n; i++) {
            ifac[i] = power(fac[i], BASE - 2);
        }
        while(!queue.isEmpty()) {
            int u = queue.poll();
            int p = prevRoom[u];
            if(p >= 0) {
                sizes[p] += sizes[u];
                if(--deg[p] == 0) {
                    queue.offer(p);
                }
            }
            int cnt = fac[sizes[u] - 1];
            Set<Integer> childSet = neighbors.get(u);
            if(childSet != null) {
                for(int v : childSet) {
                    cnt = (int)((long)cnt * ifac[sizes[v]] % BASE);
                    cnt = (int)((long)cnt * dp[v] % BASE);
                }
            }
            dp[u] = cnt;
        }
        return dp[0];
    }

    private int power(int x, int n) {
        long res = 1;
        for(int i = n; i > 0; i /= 2) {
            if(i % 2 == 1) res = res * x % BASE;
            x = (int)(1L *x * x % BASE);
        }
        return (int)res;
    }
}
