package com.im.sky.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * 竞赛过程中得初始解法
 */
public class Solution_1914 {

    public static int[][] rotateGrid(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;
        int r = rows;
        int c = cols;
        int rIndex = 0;
        int cIndex = 0;
        while(r > 0 && c > 0) {
            int len = 2 * (r - 1) + 2 * (c - 1);
            int kk = k % len;
            int curR = rIndex;
            int curC = cIndex;
            int[] arr = new int[len];
            for(int i = 0; i < len;) {
                for(int j = 0; j < 4; j++) {
                    if(j == 0) {
                        int m = 0;
                        while(m < r - 1) {
                            arr[i++] = grid[curR][curC];
                            curR++;
                            m++;
                        }
                    }else if(j == 1) {
                        int m = 0;
                        while(m < c - 1) {
                            arr[i++] = grid[curR][curC];
                            curC++;
                            m++;
                        }
                    }else if(j == 2) {
                        int m = 0;
                        while(m < r - 1) {
                            arr[i++] = grid[curR][curC];
                            curR--;
                            m++;
                        }
                    }else {
                        int m = 0;
                        while(m < c - 1) {
                            arr[i++] = grid[curR][curC];
                            curC--;
                            m++;
                        }
                    }
                }
            }
            Map<Integer, Integer> map = new HashMap<>();
            for(int i = 0; i < len; i++) {
                map.put(i, arr[i]);
            }
            for(int i = 0; i < len; i++) {
                arr[(i + kk) % len] = map.get(i);
            }
            curR = rIndex;
            curC = cIndex;
            for(int i = 0; i < len;) {
                for(int j = 0; j < 4; j++) {
                    if(j == 0) {
                        int m = 0;
                        while(m < r - 1) {
                            grid[curR][curC] = arr[i++];
                            curR++;
                            m++;
                        }
                    }else if(j == 1) {
                        int m = 0;
                        while(m < c - 1) {
                            grid[curR][curC] = arr[i++];
                            curC++;
                            m++;
                        }
                    }else if(j == 2) {
                        int m = 0;
                        while(m < r - 1) {
                            grid[curR][curC] = arr[i++];
                            curR--;
                            m++;
                        }
                    }else if(j == 3) {
                        int m = 0;
                        while(m < c - 1) {
                            grid[curR][curC] = arr[i++];
                            curC--;
                            m++;
                        }
                    }
                }
            }
            r -= 2;
            c -= 2;
            rIndex++;
            cIndex++;
        }
        return grid;
    }
}
