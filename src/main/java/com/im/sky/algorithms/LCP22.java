package com.im.sky.algorithms;

public class LCP22 {

    public static void main(String[] args) {
        LCP22 lcp22 = new LCP22();
        System.out.println(lcp22.paintingPlan(2, 2));
    }

    public int paintingPlan(int n, int k) {
        if(k == 0 || k == n * n) {
            return 1;
        }
        int res = 0;
        int count;
        int[] arr = new int[n + 1];
        arr[0] = 1;
        int v = 1;
        for(int i = 1; i <= n; i++) {
            v *= i;
            arr[i] = v;
        }
        for(int rows = 0; rows <= n; rows++) {
            int cols = rows;
            count = 0;
            while(cols <= n) {
                count = (rows + cols) * n - rows * cols;
                if(count >= k) {
                    break;
                }
                cols++;
            }
            if(count == k) {
                int tmp = (arr[n] / (arr[rows] * arr[n -  rows])) * arr[n] / (arr[cols] * arr[n -  cols]);
                if(rows != cols) {
                    tmp *= 2;
                }
                res += tmp;
            }
        }
        return res;
    }
}
