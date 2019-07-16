package com.im.sky.datastructure.map;

import java.util.LinkedList;

/**
 * Created by jiangchangwei on 2019/7/16.
 *
 * 图的广度遍历方式
 */
public class BFSVisit {
    public static void main(String[] args) throws Exception {
        BFSVisit visit = new BFSVisit();
        AdjacencyListGraphBuild.VNode[] graph = visit.buidGraph();
        int n = graph.length;
        boolean[] visited = new boolean[n];
        visit.bfs(graph, 0);

    }

    AdjacencyListGraphBuild.VNode[] buidGraph() throws Exception {
        AdjacencyListGraphBuild object = new AdjacencyListGraphBuild();
        object.build();
        return object.getGraph();
    }

    void bfs(AdjacencyListGraphBuild.VNode[] graph, int v) {
        if(graph == null || graph.length <= 0) {
            return;
        }
        boolean[] visited = new boolean[graph.length];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(v);
        AdjacencyListGraphBuild.Node p = null;
        while(!queue.isEmpty()) {
            int i = queue.removeFirst();
            AdjacencyListGraphBuild.VNode node = graph[i];
            System.out.println(node.getC() + "");
            visited[i] = true;
            p = node.getNext();
            while(p != null) {
                if(!visited[p.getAdj()]) {
                    queue.addLast(p.getAdj());
                }
                p = p.getNext();
            }
        }

    }
}
