package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * @Author: jiangcw
 * @Date: 2019-7-21 上午 11:03
 * @Version 1.0
 *
 * 图的拓扑排序
 *
 * 步骤：
 * （1）在AOV网中任选一个无前驱的顶点输出；
 * （2）删去输出过的顶点和由它发出的各条弧；
 * （3）重复（1），（2）直到所有可输出顶点去全部输出为止
 */
public class TopologicalSorting {

    public static void main(String[] args) throws Exception {
        Graph graph = Graph.buildGraph();
        TopologicalSorting topologicalSorting = new TopologicalSorting();
        topologicalSorting.topo(graph);
    }

    void topo(Graph graph) {
        int n = graph.vNodes.length;
        //入度表
        int[] id = new int[n];
        calId(graph, id, n);
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < n; i++) {
            if(id[i] == 0) {
                stack.push(i);
            }
        }
        int v, u;
        int count = 0;
        while(!stack.isEmpty()) {
            v  = stack.pop();
            System.out.println(graph.vNodes[v].c + "");
            Graph.Edge edge;
            edge = graph.vNodes[v].fout;
            while(edge != null) {
                u = edge.head;
                id[u]--;
                if(id[u] == 0) {
                    stack.push(u);
                }
                edge = edge.tlink;
            }
            count++;
        }
        System.out.println("count:" + count + ", n:" + n);
        if(count == n) {
            System.out.println("可以构成拓扑排序");
        }else {
            System.out.println("不可构成拓扑排序");
        }
    }

    /**
     * 构建初始化入度表
     */
    private void calId(Graph graph, int[] id, int n) {
        Graph.VNode[] vNodes = graph.vNodes;
        for(int i = 0; i < n; i++) {
            Graph.Edge edge = vNodes[i].fin;
            int count = 0;
            while(edge != null) {
                count++;
                edge = edge.hlink;
            }
            id[i] = count;
        }
    }

    private static class Graph {

        VNode[] vNodes;

        static Graph buildGraph() throws Exception {
            Graph graph = new Graph();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.valueOf(br.readLine());
            graph.vNodes = new VNode[n];
            char c;
            VNode vNode;
            for(int i = 0; i < n; i++) {
                c = br.readLine().charAt(0);
                vNode = new VNode();
                vNode.c = c;
                graph.vNodes[i] = vNode;
            }
            String s;
            String[] sArray;
            while(!"#".equals(s = br.readLine())) {
                sArray = s.split(" ");
                int tail = Integer.valueOf(sArray[0]);
                int head = Integer.valueOf(sArray[1]);
                int w  = Integer.valueOf(sArray[2]);
                Edge edge = new Edge();
                edge.tail = tail;
                edge.head = head;
                edge.w = w;
                edge.hlink = graph.vNodes[head].fin;
                graph.vNodes[head].fin = edge;
                edge.tlink = graph.vNodes[tail].fout;
                graph.vNodes[tail].fout = edge;
            }
            return graph;
        }

        private static class VNode {
            private char c;

            /**
             * 以当前顶点为头的边
             */
            private Edge fin;

            /**
             * 以当前顶点为尾的边
             */
            private Edge fout;
        }

        private static class Edge {
            /**
             * 当前边的尾节点
             */
            private int tail;
            /**
             * 当前边的头节点
             */
            private int head;


            /**
             * 边上的权
             */
            private int w;
            /**
             * 与当前边有相同头节点的边
             */
            private Edge hlink;
            /**
             * 与当前边有相同尾节点的边
             */
            private Edge tlink;
        }
    }
}
