package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangchangwei on 2019/7/18.
 *
 * 求图的最短生成树
 *
 * Prim算法
 */
public class Prim {

    Edge[] prim() throws Exception {
        Graph graph = Graph.buildGraph();
        int n = graph.v.length;
        Edge[] tr = new Edge[n - 1];
        int i, j, k, m = 0, v, min, d;
        Edge e;
        tr = new Edge[n];
        for(i = 1; i < n; i++) {
            tr[i - 1].pre = 0;
            tr[i - 1].end = i;
            tr[i - 1].w = graph.edges[0][i];
        }
        for(j = 0; j < n - 1; j++) {
            min = Integer.MAX_VALUE;
            for(k = j; k < n -1; k++) {
                if(tr[k].w < min) {
                    min = tr[k].w;
                    m = k;
                }
            }
            e = tr[m]; tr[m] = tr[j]; tr[j] = e;
            v = tr[j].end;
            // 这部分的更新还是挺难理解的
            for(k = j + 1; k < n-1; k++) {
                d = graph.edges[v][tr[k].end];
                if(d < tr[k].w) {
                    tr[k].w = d;
                    tr[k].pre = v;
                }
            }
        }
        return tr;

    }


    private static class Graph {
        /**
         * 顶点
         */
        private char[] v;

        private Map<Character, Integer> vIndexMap;

        private int[][] edges;

        static Graph buildGraph() throws Exception {
            Graph graph = new Graph();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.valueOf(br.readLine());
            graph.v = new char[n];
            graph.vIndexMap = new HashMap<>(n);
            for(int i = 0; i < n; i++) {
                char c = br.readLine().charAt(0);
                graph.v[i] = c;
                graph.vIndexMap.put(c, i);
            }
            graph.edges = new int[n][n];
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    graph.edges[i][j] = Integer.MAX_VALUE;
                }
            }
            String s;
            String[] sArray;
            while(!"#".equals(s = br.readLine())) {
                sArray = s.split(" ");
                int i = graph.vIndexMap.get(sArray[0].charAt(0));
                int j = graph.vIndexMap.get(sArray[1].charAt(0));
                int w = Integer.valueOf(sArray[2]);
                graph.edges[i][j] = graph.edges[j][i] = w;
            }
            return graph;
        }
    }



    private static class Edge {
        private int pre;

        private int end;

        private int w;
    }
}
