package com.im.sky.test;

/**
 * @author jiangchangwei
 * @date 2020-7-26 下午 7:57
 **/
class Solution6 {

    public static void main(String[] args) {
        new Solution6().largest1BorderedSquare(new int[][]{{1,1,1},{1,0,1},{1,1,1}});
    }

    public int largest1BorderedSquare(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] sum = new int[rows + 1][cols + 1];
        for(int i = 1; i <= rows; i++) {
            for(int j = 1; j <= cols; j++) {
                sum[i][j] = grid[i -1][j-1] + sum[i][j-1] + sum[i-1][j] - sum[i-1][j-1];
            }
        }
        int sum1 = 0;
        int[][] row1Count = new int[rows + 1][cols + 1];
        int[][] col1Count = new int[rows + 1][cols + 1];
        for(int i = 1; i <= rows; i++) {
            for(int j = 1; j<= cols; j++) {
                if(grid[i-1][j-1] == 1) {
                    row1Count[i][j] = row1Count[i][j-1] + 1;
                    col1Count[i][j] = col1Count[i-1][j] + 1;
                    int tmpLen = Math.min(row1Count[i][j], col1Count[i][j]);
                    for(int k = tmpLen; k > 0; k--) {
                        if(row1Count[i + 1 - k][j] >= tmpLen && col1Count[i][j + 1 - k] >= tmpLen) {
                            sum1 = Math.max(sum1, sum[i][j] - sum[i][j - k] - sum[i - k][j] +
                                    sum[i-k][j-k]);
                            break;

                        }
                    }
                }
            }
        }
        return sum1;
    }
}
