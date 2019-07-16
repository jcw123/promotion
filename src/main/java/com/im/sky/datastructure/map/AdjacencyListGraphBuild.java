package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangchangwei on 2019/7/13.
 *
 * 邻接表表示法
 *
 * 以下面无向图邻接表建立为例。
 *
 * 主要是节点数组的建立，然后在每个头节点后面链接上对应的相连的节点。
 */
public class AdjacencyListGraphBuild implements GraphBuild {


    private VNode[] graph;

    public static void main(String[] args) throws Exception {
        AdjacencyListGraphBuild object = new AdjacencyListGraphBuild();
        object.build();
        System.out.println(Arrays.toString(object.graph));

    }


    @Override
    public void build() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.valueOf(br.readLine());

        graph = new VNode[n];
        Map<Character, Integer> map = new HashMap<>();

        for(int i = 0; i < n; i++) {
            char v = br.readLine().charAt(0);
            graph[i] = new VNode(v, null);
            map.put(v, i);
        }

        String s;
        String[] sArray;
        char a;
        char b;
        int w;
        int i;
        int j;
        while(!"#".equals(s = br.readLine())) {
            sArray = s.split(" ");
            a = sArray[0].charAt(0);
            b =  sArray[1].charAt(0);
            w = Integer.valueOf(sArray[2]);
            i = map.get(a);
            j = map.get(b);
            Node nextForI = new Node();
            nextForI.w = w;
            nextForI.adj = i;
            nextForI.next = graph[j].next;
            graph[j].next = nextForI;

            Node nextForJ = new Node();
            nextForJ.w = w;
            nextForJ.adj = j;
            nextForJ.next = graph[i].next;
            graph[i].next = nextForJ;

        }
    }

    private static class VNode {
        private char c;

        private Node next;

        private VNode(char c, Node next) {
            this.c = c;
            this.next = next;
        }
    }


    private static class Node {
        private int w;

        private int adj;

        private Node next;

        @Override
        public String toString() {
            return w + ":" + adj;
        }

    }
}
