package com.im.sky.datastructure.map;

/**
 * Created by jiangchangwei on 2019/7/16.
 *
 * 图的深度遍历方式
 */
public class DFSVisit {

    public static void main(String[] args) throws Exception {
        DFSVisit visit = new DFSVisit();
        AdjacencyListGraphBuild.VNode[] graph = visit.buidGraph();
        int n = graph.length;
        boolean[] visited = new boolean[n];
        visit.dfs(graph, 0, visited);

    }

    AdjacencyListGraphBuild.VNode[] buidGraph() throws Exception {
        AdjacencyListGraphBuild object = new AdjacencyListGraphBuild();
        object.build();
        return object.getGraph();
    }

    void dfs(AdjacencyListGraphBuild.VNode[] graph, int v, boolean[] visited) {
        int u;
        System.out.println(graph[v].getC() + "");
        visited[v] = true;
        u = graph[v].getNext().getAdj();
        AdjacencyListGraphBuild.Node p = graph[v].getNext();
        while(u >= 0) {
            if(!visited[u]) {
                dfs(graph, u, visited);
            }
            p = p.getNext();
            if(p != null) {
                u = p.getAdj();
            }else {
                u = -1;
            }
        }

    }
}
