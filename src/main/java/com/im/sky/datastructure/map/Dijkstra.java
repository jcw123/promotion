package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by jiangchangwei on 2019/7/20.
 *
 * 最短路径算法
 *
 * 主要是利用递推的思想
 *
 * 每当确认了一条最短路径后，就根据此最短路径修复剩余节点的值。
 */
public class Dijkstra {

    public static void main(String[] args) throws Exception {
        Graph graph = Graph.buildGraph();
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.dijkstra(graph, 0);
    }

    public void dijkstra(Graph graph, int v) {
        int n = graph.vNodes.length;
        Path[] paths = new Path[n];
        int[] s = new int[n];
        int[] dist = new int[n];
        for(int i = 0; i < n; i++) {
            dist[i] = graph.edges[v][i];
            paths[i] = new Path();
            paths[i].pi = new int[n];
            paths[i].pi[0] = v;
        }
        int count = 0;
        int max;
        int u = 0;
        while(count < n) {
            max = Integer.MAX_VALUE;
            // 查找未确认最短路径的最下的值
            for(int i = 0; i < n;  i++) {
                if(s[i] == 0 && dist[i] < max) {
                    u = i;
                    max = dist[i];
                }
            }

            if(max == Integer.MAX_VALUE) {
                break;
            }
            s[u] = 1;
            Path p = paths[u];
            p.end = p.end + 1;
            p.pi[p.end] = u;
            for(int i = 0; i < n; i++) {
                if(s[i] == 0 && dist[i] > dist[u] + graph.edges[u][i]) {
                    dist[i] = dist[u] + graph.edges[u][i];
                    p = paths[i];
                    p.end = paths[u].end;
                    for(int k = 0; k <= paths[u].end; k++) {
                        p.pi[k] = paths[u].pi[k];
                }
                }
            }
            count++;
        }
        System.out.println(Arrays.toString(paths));
    }

    private static class Path {
        int pi[];
        int end;

        @Override
        public String toString() {
            return Arrays.toString(pi) + "##" + end;
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
