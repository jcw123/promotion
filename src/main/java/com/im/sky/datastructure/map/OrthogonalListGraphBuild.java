package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangchangwei on 2019/7/13.
 *
 * 通过十字链表法优化有向图的建立，使求一个节点的入度和出度都非常方便
 */
public class OrthogonalListGraphBuild implements GraphBuild {

    private VexNode[] graph;

    public static void main(String[] args) throws Exception {
        OrthogonalListGraphBuild object = new OrthogonalListGraphBuild();
        object.build();
        System.out.println(Arrays.toString(object.graph));
    }


    @Override
    public void build() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        graph = new VexNode[n];
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            VexNode vexNode = new VexNode();
            char v = br.readLine().charAt(0);
            vexNode.c = v;
            graph[i] = vexNode;
            map.put(v, i);

        }
        String s;
        String[] sArray;
        while(!"#".equals(s = br.readLine())) {
            sArray = s.split(" ");
            int i = map.get(sArray[0].charAt(0));
            int j = map.get(sArray[1].charAt(0));
            int w = Integer.valueOf(sArray[2]);
            ArcNode arcNode = new ArcNode();
            arcNode.tail = i;
            arcNode.head = j;
            arcNode.w = w;
            arcNode.tlink = graph[i].fout;
            graph[i].fout = arcNode;

            arcNode.hlink = graph[j].fin;
            graph[j].fin = arcNode;
        }
    }

    /**
     * 弧节点
     */
    private static class ArcNode {
        int tail, head;
        ArcNode hlink, tlink;
        int w;
    }

    private static class VexNode {
        char c;
        /**
         * 指向以当前节点作为弧头的节点
         */
        ArcNode fin;
        /**
         * 指向当前节点作为弧尾的节点
         */
        ArcNode fout;
    }
}
