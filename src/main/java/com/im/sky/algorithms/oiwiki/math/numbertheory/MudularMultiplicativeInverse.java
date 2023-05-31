package com.im.sky.algorithms.oiwiki.math.numbertheory;

import java.util.Arrays;

/**
 *
 * 乘法逆元
 *
 * @author jiangchangwei
 * @date 2022/11/8 11:01 上午
 *
**/
public class MudularMultiplicativeInverse {

    public static void main(String[] args) {
        int a = 3;
        int b = 11;
        int[] x = new int[1];
        int[] y = new int[1];
        exgcd(a, b, x, y);
        if(x[0] < 0) {
            x[0] = x[0] + b;
        }
        System.out.println(x[0]);

        a = 5;
        b = 11;
        System.out.println(qpow(a, b - 2, b));

        long[] inv = calInv(100, (int)1e9 + 7 - 2);
        System.out.println(Arrays.toString(inv));

        long[] inv2 = calInv(new int[]{1,3,5,9}, 11);
        System.out.println(Arrays.toString(inv2));
    }

    /**
     * 扩展欧几里得法
     * @param a
     * @param b
     * @param x
     * @param y
     *
     * ax + by = d 其中d是a、b的最大公约数
     */
    private static void exgcd(int a, int b, int[] x, int[] y) {
        if(b == 0) {
            x[0] = 1;
            y[0] = 0;
            return;
        }
        exgcd(b, a % b, y, x);
        y[0] -= a / b * x[0];
    }

    /**
     * b需要是一个质数
     *
     * ax = 1 (mod b)
     *
     * ax = a^(b - 1) (mod b)
     * x = a ^ (b - 2) (mod b)
     * @param a
     * @param b
     * @return
     */
    private static long qpow(long a, int b, int p) {
        long ans = 1;
        while(b > 0) {
            if((b & 1) > 0) {
                ans = (ans * a) % p;
            }
            a = (a * a) % p;
            b >>= 1;
        }
        return ans;
    }

    // 求 1 ~ n 这些数在p下的逆元
    private static long[] calInv(int n, int p) {
        long[] inv = new long[n + 1];
        inv[1] = 1;
        for(int i = 2; i <= n; i++) {
            inv[i] = (p - p / i) * inv[p % i] % p;
        }
        return inv;
    }

    // 求解任意n个数的逆元
    private static long[] calInv(int[] arr, int p) {
        int n = arr.length;
        long[] s = new long[n + 1];
        long[] sInv = new long[n + 1];
        s[0] = 1;
        long[] inv = new long[n];
        for(int i = 0; i < n; i++) {
            s[i + 1] = s[i] * arr[i] % p;
        }
        sInv[n] = qpow(s[n], p - 2, p);
        for(int i = n; i > 0; i--) {
            sInv[i - 1] = sInv[i] * arr[i - 1] % p;
        }
        for(int i = 0; i < n; i++) {
            inv[i] = s[i] * sInv[i + 1] % p;
        }
        return inv;
    }
}
