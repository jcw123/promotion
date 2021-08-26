package com.im.sky.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Solution_1914_V3 {

    public int[][] rotateGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int nlayer = Math.min(m / 2, n / 2);   // 层数
        // 从左上角起逆时针枚举每一层
        for (int layer = 0; layer < nlayer; ++layer){
            List<Integer> r = new ArrayList<>(), c = new ArrayList<>(), val = new ArrayList<>();   // 每个元素的行下标，列下标与数值
            for (int i = layer; i < m - layer - 1; ++i){   // 左
                r.add(i);
                c.add(layer);
                val.add(grid[i][layer]);
            }
            for (int j = layer; j < n - layer - 1; ++j){   // 下
                r.add(m - layer - 1);
                c.add(j);
                val.add(grid[m-layer-1][j]);
            }
            for (int i = m - layer - 1; i > layer; --i){   // 右
                r.add(i);
                c.add(n - layer - 1);
                val.add(grid[i][n-layer-1]);
            }
            for (int j = n - layer - 1; j > layer; --j){   // 上
                r.add(layer);
                c.add(j);
                val.add(grid[layer][j]);
            }
            int total = val.size();   // 每一层的元素总数
            int kk = k % total;   // 等效轮转次数
            // 找到每个下标对应的轮转后的取值，确认位置0是由哪个位置旋转获得，而不是0号元素会旋转到哪个位置
            for (int i = 0; i < total; ++i){
                int idx = (i + total - kk) % total;   // 轮转后取值对应的下标
                grid[r.get(i)][c.get(i)] = val.get(idx);
            }
        }
        return grid;
    }
}
