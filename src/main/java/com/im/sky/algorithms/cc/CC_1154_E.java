package com.im.sky.algorithms.cc;

import java.util.*;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-13 23:45
 * @link https://codeforces.com/problemset/problem/1154/E
 *
 * 思路：
 * 思路一：用一个优先级队列维护基于值排序的索引信息，每次从队列中获取元素值最大的索引；
 * 思路二：用一个visited集合维护哪些位置已经被选中了；
 * 思路三：用一个平衡树维护所有的索引，用于取左右两边k的索引；
 **/
public class CC_1154_E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        TreeSet<Integer> indexTree = new TreeSet<>();
        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2)->o2[0]-o1[0]);
        Set<Integer> visited = new HashSet<>();
        for(int i = 0; i < n; i++) {
            int v = sc.nextInt();
            indexTree.add(i);
            q.offer(new int[]{v, i});
        }
        int[] ans = new int[n];
        int v = 0;
        while(!indexTree.isEmpty()) {
            int idx = -1;
            while(!q.isEmpty()) {
                int[] cur = q.poll();
                if(!visited.contains(cur[1])) {
                    idx = cur[1];
                    break;
                }
            }
            ans[idx] = v + 1;
            indexTree.remove(idx);
            int tmp = k;
            while(!indexTree.isEmpty() && tmp > 0) {
                Integer nextIdx = indexTree.higher(idx);
                if(nextIdx != null) {
                    ans[nextIdx] = v + 1;
                    visited.add(nextIdx);
                    indexTree.remove(nextIdx);
                }else {
                    break;
                }
                tmp--;
            }
            tmp = k;
            while(!indexTree.isEmpty() && tmp > 0) {
                Integer nextIdx = indexTree.lower(idx);
                if(nextIdx != null) {
                    ans[nextIdx] = v + 1;
                    visited.add(nextIdx);
                    indexTree.remove(nextIdx);
                }else {
                    break;
                }
                tmp--;
            }
            v = (v + 1) % 2;
        }
        for(int i = 0; i < n; i++) {
            System.out.print(ans[i]);
        }
    }
}
