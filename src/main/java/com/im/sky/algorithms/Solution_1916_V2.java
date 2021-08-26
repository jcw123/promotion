package com.im.sky.algorithms;

public class Solution_1916_V2 {

    public static final int MOD = 1000000007;

    public int waysToBuildRooms(int[] prevRoom) {
        return 0;
    }

    public static void main(String[] args) {
        int x = 10;
        int y = 100;
        int re1 = quickMul(x, y);
        int re2 = quickMul2(x, y);
        System.out.println(re1 +  " " + re2);
        System.out.println(re1 == re2);
    }

    private static int quickMul(int x, int n) {
        int ret = 1, cur = x;
        while(n > 0) {
            if((n & 1) == 1) {
                ret = (int)((long)ret * cur % MOD);
            }
            cur = (int)((long)cur * cur % MOD);
            n >>= 1;
        }
        return ret;
    }

    private static int quickMul2(int x, int n) {
        if(n == 0) {
            return 1;
        }
        if(n == 1) {
            return x;
        }
        int x1 = quickMul2(x, n & 1);
        int x2 = quickMul2(x, n / 2);
        x2 %= MOD;
        return (int)((long)x2 * x2 * x1 % MOD);
    }
}
