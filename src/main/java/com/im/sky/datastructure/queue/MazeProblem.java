package com.im.sky.datastructure.queue;

import java.util.LinkedList;

/**
 * Created by jiangchangwei on 2019/7/8.
 *
 * 迷宫问题：寻找最短路径
 *
 * 可以采用BFS和DFS
 */
public class MazeProblem {

    public static void main(String[] args) {
        int[][] mazes = new int[][]{{0,1,0,1,0,1,1,1,0,}, {1,0,1,0,1,1,0,1,1,},{0,1,1,1,0,0,0,1,1,},
                {1,1,0,1,1,1,1,0,1},{1,1,1,1,0,0,1,1,0,0},{0,0,1,1,1,0,1,1,0}};
        int rows = mazes.length;
        int cols = mazes[0].length;
        boolean[][] visited = new boolean[rows][cols];
        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(new Node(0, 0, null));
//        Node node =  bfs(mazes, visited, queue, rows, cols);
//        int step = 0;
//        while(node != null) {
//            step++;
//            System.out.println(node.x + ":" + node.y);
//            node = node.pre;
//        }
//        System.out.println("total step:" + step);
        System.out.println(dfs(mazes, visited, 0, 0, 0, Integer.MAX_VALUE, rows, cols));
    }

    /**
     * 广度遍历
     */
    private static Node bfs(int[][] mazes, boolean[][] visited, LinkedList<Node> queue, int rows, int cols) {
        Node node = null;
        boolean isFind = false;
        while(queue.size() > 0) {
            node = queue.peekFirst();
            visited[node.x][node.y] = true;
            int x = node.x;
            int y = node.y + 1;
            if(y <= cols - 1 && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x + 1;
            y = node.y;
            if(x <= rows - 1 && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x;
            y = node.y - 1;
            if(y >= 0 && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x - 1;
            y = node.y;
            if(x >= 0 && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x - 1;
            y = node.y + 1;
            if(x >= 0 && y < cols && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x + 1;
            y = node.y + 1;
            if(x < rows && y < cols && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x + 1;
            y = node.y - 1;
            if(x < rows && y >= 0 && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            x = node.x - 1;
            y = node.y - 1;
            if(x >= 0 && y >= 0 && !visited[x][y] && mazes[x][y] == 0) {
                queue.addLast(new Node(x, y, node));
            }
            if(node.x == rows - 1 && node.y == cols - 1) {
                isFind = true;
                break;
            }else {
                queue.removeFirst();
            }

        }
        return isFind ?node:null;
    }

    /**
     * 深度遍历
     */
    private static int dfs(int[][] mazes, boolean[][] visited, int step, int i, int j, int min, int rows, int cols) {
        if(i < 0 || i >= rows || j < 0 || j >= cols) {
            return Integer.MAX_VALUE;
        }
        if(i == rows - 1 && j == cols - 1) {
            return Math.min(step, min);
        }
        if(visited[i][j] || mazes[i][j] == 1) {
            return Integer.MAX_VALUE;
        }
        visited[i][j] = true;
        int m1 = dfs(mazes, visited, step + 1, i, j + 1, min, rows, cols);
        int m2 = dfs(mazes, visited, step + 1, i, j - 1, min, rows, cols);
        int m3 = dfs(mazes, visited, step + 1, i - 1, j, min, rows, cols);
        int m4 = dfs(mazes, visited, step + 1, i + 1, j, min, rows, cols);
        int m5 = dfs(mazes, visited, step + 1, i + 1, j + 1, min, rows, cols);
        int m6 = dfs(mazes, visited, step + 1, i + 1, j - 1, min, rows, cols);
        int m7 = dfs(mazes, visited, step + 1, i - 1, j + 1, min, rows, cols);
        int m8 = dfs(mazes, visited, step + 1, i - 1, j - 1, min, rows, cols);
        visited[i][j] = false;
        int min1 =  Math.min(m1, Math.min(m2, Math.min(m3, m4)));
        int min2 =  Math.min(m5, Math.min(m6, Math.min(m7, m8)));
        return Math.min(min1, min2);
    }

    private static class Node {
        private int x;
        private int y;
        private Node pre;

        Node(int x, int y, Node pre) {
            this.x = x;
            this.y = y;
            this.pre = pre;
        }
    }
}
