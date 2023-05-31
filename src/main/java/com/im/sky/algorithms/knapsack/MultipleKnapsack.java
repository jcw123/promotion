package com.im.sky.algorithms.knapsack;

import java.util.ArrayList;
import java.util.List;

/**
 * multiple knapsack
 * @author jiangchangwei
 * @date 2022/11/10 10:23 上午
 *
**/
public class MultipleKnapsack {

    public static void main(String[] args) {
        MultipleKnapsack a = new MultipleKnapsack();
        int[] v = {3, 1, 7, 10, 9, 9};
        int[] w = {4, 1, 3, 15, 9, 4};
        int V = 100;
        int[] nums = {3, 1, 2, 9, 10, 11};
        System.out.println(a.maxV(v, w, V, nums));
        System.out.println(a.maxV2(v, w, V, nums));
        System.out.println(a.maxV3(v, w, V, nums));
        System.out.println(a.maxV4(v, w, V, nums));

    }

    private int maxV(int[] v, int[] w, int V, int[] nums) {
        int n = v.length;
        int[][] dp = new int[n + 1][V + 1];
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= V; j++) {
                for(int k = 1; k <= nums[i - 1]; k++) {
                    dp[i][j] = dp[i - 1][j];
                    if(k * v[i - 1] <= j) {
                        dp[i][j] = Math.max(dp[i][j], w[i- 1] * k + dp[i - 1][j - k * v[i - 1]]);
                    }else {
                        break;
                    }
                }
            }
        }
        return dp[n][V];
    }

    private int maxV2(int[] v, int[] w, int V, int[] nums) {
        int n = v.length;
        int[] dp = new int[V + 1];
        for(int i = 1; i <= n; i++) {
            for(int j = V; j >= 1; j--) {
                for(int k = 1; k <= nums[i - 1]; k++) {
                    if(k * v[i - 1] <= j) {
                        dp[j] = Math.max(dp[j], dp[j - k * v[i - 1]] + k * w[i - 1]);
                    }
                }
            }
        }
        return dp[V];
    }

    /**
     * 将小物品组成更大的物品，然后可以套用0-1背包，保证也能覆盖所有的场景
     *
     * 比如某一个物品的数量为10，将其拆成1，,2，,4，,3，采用0-1背包进行筛选时，也能保证一个物品分别选到 0 ~ 10的范围
     *
     * 拆分组合思想
     * @param v
     * @param w
     * @param V
     * @param nums
     * @return
     *
     */
    private int maxV3(int[] v, int[] w, int V, int[] nums) {
        List<Integer> vList = new ArrayList<>();
        List<Integer> wList = new ArrayList<>();
        int n = v.length;
        for(int i = 0; i < n; i++) {
            int c = 1;
            int k = nums[i];
            while(k - c > 0) {
                vList.add(v[i] * c);
                wList.add(w[i] * c);
                k -= c;
                c *= 2;
            }
            vList.add(k * v[i]);
            wList.add(k * w[i]);
        }
        int[] dp = new int[V + 1];
        for(int i = 1; i <= vList.size(); i++) {
            for(int j = V; j > 0; j--) {
                dp[j] = Math.max(dp[j], vList.get(i - 1) <= j ? dp[j - vList.get(i - 1)] + wList.get(i - 1) : 0);
            }
        }
        return dp[V];
    }

    /**
     * 利用单调队列进行优化
     *
     * 重新定义子问题，由子问题推出原问题的答案
     * @param v
     * @param w
     * @param V
     * @param nums
     * @return
     */
    private int maxV4(int[] v, int[] w, int V, int[] nums) {
        int n = v.length;
        int[] dp = new int[V + 1];
        int[] g;
        int[] q = new int[V + 1];
        for(int i = 0; i < n; i++) {
            int vi = v[i];
            int wi = w[i];
            int ci = nums[i];
            g = dp.clone();
            for(int j = 0; j < vi; j++) {
                int tail = -1;
                int head = 0;
                for(int k = j; k <= V; k += vi) {
                    dp[k] = g[k];
                    if(head <= tail && q[head] < k - vi * ci) head++;
                    if(head <= tail) {
                        dp[k] = Math.max(dp[k], g[q[head]] + (k - q[head]) / vi * wi);
                    }
                    while(head <= tail && g[q[tail]] - (q[tail] - j) / vi * wi <= g[k] - (k - j) / vi * wi) tail--;
                    q[++tail] = k;
                }
            }
        }
        return dp[V];
    }

}
