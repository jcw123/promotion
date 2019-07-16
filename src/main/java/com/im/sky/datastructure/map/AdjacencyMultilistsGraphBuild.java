package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangchangwei on 2019/7/13.
 *
 * 邻接多重表的建立，主要是优化无向图邻接表的建立过程，不需要太多的弧节点。
 *
 *
 * 注：和十字链表法的建立非常相似，只是十字链表法顶点需要额外的两个指针，而邻接多重表只需要1个指针。
 */
public class AdjacencyMultilistsGraphBuild implements GraphBuild {

    private VNode[] graph;

    public static void main(String[] args) throws Exception {
        AdjacencyMultilistsGraphBuild object = new AdjacencyMultilistsGraphBuild();
        object.build();
        System.out.println(object.graph);
    }

    @Override
    public void build() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        graph = new VNode[n];
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            VNode vNode = new VNode();
            vNode.c = br.readLine().charAt(0);
            graph[i] = vNode;
            map.put(vNode.c, i);
        }
        String s;
        String[] sArray;
        while(!"#".equals(s = br.readLine())) {
            sArray = s.split(" ");
            int i = map.get(sArray[0].charAt(0));
            int j = map.get(sArray[1].charAt(0));
            int w = Integer.valueOf(sArray[2]);
            Node node = new Node();
            node.tail = i;
            node.head = j;
            node.w = w;

            node.tailLink = graph[i].next;
            graph[i].next = node;

            node.headLink = graph[j].next;
            graph[j].next = node;
        }

    }

    private static class VNode {
        private char c;

        private Node next;

    }

    private static class Node {
        private int tail;

        private int head;

        private int w;

        private Node tailLink;

        private Node headLink;
    }
}
