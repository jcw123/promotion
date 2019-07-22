package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Author: jiangcw
 * @Date: 2019-7-21 下午 1:05
 * @Version 1.0
 *
 * 关键路径
 */
public class CirticalPath {

    public static void main(String[] args) throws Exception{
        Graph graph = Graph.buildGraph();
        CirticalPath cirticalPath = new CirticalPath();
        cirticalPath.criticalPath(graph);

    }

    void criticalPath(Graph graph) {
        int i, j, k, m;
        int n = graph.n;
        int eCount = graph.eCount;
        int[] eV = new int[n];
        int[] lV = new int[n];
        int[] e = new int[eCount + 1];
        int[] l = new int[eCount + 1];
        int[] id = new int[n];
        // 拓扑序列表
        int[] topo = new int[n];
        calId(graph, id, n);
        m = 0;
        LinkedList<Integer> queue = new LinkedList<>();
        for(i = 0; i < n; i++) {
            if(id[i] == 0) {
              topo[m++] = i;
              queue.addLast(i);
            }
        }
        Graph.Edge p;
        while(!queue.isEmpty()) {
            j = queue.removeFirst();
            p = graph.vNodes[j].fout;
            while(p != null) {
                k = p.head;
                if(eV[j] + p.w > eV[k]) {
                    eV[k] = eV[j] + p.w;
                }
                id[k]--;
                if(id[k] == 0) {
                    queue.addLast(k);
                    topo[m++] = k;
                }
                p = p.tlink;
            }
        }
        if(m < n) {
            System.out.println("不能构成拓扑序列, 无法构成关键路径");
            return;
        }
        for(i = 0; i < n; i++) {
            lV[i] = eV[n - 1];
        }
        for(i = n - 2; i >= 0; i--) {
            j = topo[i];
            p = graph.vNodes[j].fout;
            while(p != null) {
                k = p.head;
                if(lV[k] - p.w < lV[j]) {
                    lV[j] = lV[k] - p.w;
                }
                p = p.tlink;
            }
        }
        i = 0;
        System.out.println("eV:" + Arrays.toString(eV));
        System.out.println("lV:" + Arrays.toString(lV));
        for(j = 0; j < n; j++) {
            p = graph.vNodes[j].fout;
            while(p != null) {
                k = p.head;
                e[++i] = eV[j];
                l[i] = lV[k] - p.w;
                System.out.println("j:" + j + ", k:" + k + ", e["+i+"]="+ e[i] + ", l[" + k + "]=" + l[k]);
                if(l[i] == e[i]) {
                    System.out.println("i:" + i + " is a critical activity");
                }
                p = p.tlink;
            }
        }
        System.out.println("e:" + Arrays.toString(e));
        System.out.println("l:" + Arrays.toString(l));
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

        int n;

        int eCount;

        static Graph buildGraph() throws Exception {
            Graph graph = new Graph();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.valueOf(br.readLine());
            graph.n = n;
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
                graph.eCount++;
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
