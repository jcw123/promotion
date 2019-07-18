package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by jiangchangwei on 2019/7/18.
 */
public class Kruskal {

    Edge[] kruskal() throws Exception {
        Graph graph = Graph.buildGraph();
        Collections.sort(graph.allEdges, new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.w - o2.w;
            }
        });
        int  n = graph.v.length;
        List<Edge> allEdges = graph.allEdges;
        int len = allEdges.size();
        int i = 0;
        Edge[] result = new Edge[n - 1];
        Set<Character> set = new HashSet<>();
        int k = 0;
        Edge e;
        while(i < len) {
            e = allEdges.get(i);
            if(!isCircle(e, result, k)) {
                result[k++] = e;
            }
            i++;
        }
        return result;
    }

    boolean isCircle(Edge e, Edge[] edges, int k) {
        return true;
    }





    private static class Graph {
        /**
         * 顶点
         */
        private char[] v;

        private Map<Character, Integer> vIndexMap;

        private int[][] edges;

        private List<Edge> allEdges;

        static Graph buildGraph() throws Exception {
            Graph graph = new Graph();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.valueOf(br.readLine());
            graph.v = new char[n];
            graph.vIndexMap = new HashMap<>(n);
            graph.allEdges = new ArrayList<>();
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
                Edge edge = new Edge();
                edge.pre = i;
                edge.end = j;
                edge.w = w;
                graph.allEdges.add(edge);
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
