package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by jiangchangwei on 2019/7/20.
 *
 * Floyd算法
 *
 *求任意两点之间的最短路径
 */
public class Floyd {

    public static void main(String[] args) throws Exception {
        Floyd floyd = new Floyd();
        Graph graph = new Graph();
        floyd.floyd(graph.buildGraph());
    }

    public void floyd(Graph graph) {
        int n = graph.edges.length;
        int[][] edges = new int[n][n];
        int[][] tmpSwap = new int[n][n];
        int[][] path = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int  j = 0; j < n; j++) {
                tmpSwap[i][j] = graph.edges[i][j];
                edges[i][j] = tmpSwap[i][j];
                if(edges[i][j] == Integer.MAX_VALUE) {
                    path[i][j] = -1;
                }else {
                    path[i][j] = j;
                }
            }
        }
        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(edges[i][k] != Integer.MAX_VALUE &&
                            edges[k][j] != Integer.MAX_VALUE &&
                            (edges[i][j] > edges[i][k] + edges[k][j])) {
                        tmpSwap[i][j] = edges[i][k] + edges[k][j];
                        edges[i][j] = tmpSwap[i][j];
                        path[i][j] = k;
                    }
                }
            }
            int[][] tmp = edges;
            edges = tmpSwap;
            tmpSwap = tmp;
        }
        for(int i = 0; i < n; i++) {
            System.out.println("path:" + Arrays.toString(path[i]));
            System.out.println("distance:" + Arrays.toString(edges[i]));
        }
    }


    private static class Graph {
        /**
         * 用于存放顶点
         */
        char[] vNodes;

        /**
         * 用于存放顶点之间的距离
         */
        int[][] edges;


        public int getIndex(char c) {
            for(int i = 0; i < vNodes.length; i++) {
                if(vNodes[i] == c) {
                    return i;
                }
            }
            return -1;
        }

        public static Graph buildGraph() throws Exception {
            Graph graph = new Graph();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.valueOf(br.readLine());
            graph.vNodes = new char[n];
            for(int i = 0; i < n; i++) {
                graph.vNodes[i] = br.readLine().charAt(0);
            }
            String s;
            int i, j, w;
            String[] sArray;
            graph.edges = new int[n][n];
            for(int k = 0; k < n; k++) {
                Arrays.fill(graph.edges[k], Integer.MAX_VALUE);
                graph.edges[k][k] = 0;
            }
            while(!"#".equals(s = br.readLine())) {
                sArray = s.split(" ");
                i = Integer.valueOf(sArray[0]);
                j = Integer.valueOf(sArray[1]);
                w = Integer.valueOf(sArray[2]);
                graph.edges[i][j] = w;
            }
            return graph;

        }

    }
}
