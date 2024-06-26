package com.im.sky.algorithms.template;

/**
 * @author jiangchangwei
 * @program promotion
 * @description 并查集
 * @date 2023-06-02 17:48
 **/
public class UnionFind {

    int[] parent;

    int[] rank;

    public UnionFind(int n) {
        this.parent = new int[n];
        this.rank = new int[n];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    /**
     * 扁平化树结构
     * @param x
     * @return
     */
//    public int find1(int x) {
//        return  parent[x] == x ? x : (parent[x] = find1(parent[x]));
//    }

    /**
     * 构建多层的树结构
     * @param x
     * @return
     */
    public int find(int x) {
        return parent[x] == x ? x : find(parent[x]);
    }

    public void union(int x, int y) {
        int xp = find(x);
        int yp = find(y);
        int xr = rank[xp];
        int yr = rank[yp];
        if(xr > yr) {
            parent[yp] = xp;
        }else {
            parent[xp] = yp;
            if(xr == yr) {
                rank[yp]++;
            }
        }
    }

    public boolean isConnect(int x, int y) {
        return find(x) == find(y);
    }

    public static void main(String[] args) {
        UnionFind uf = new UnionFind(10);
        uf.union(1, 3);
        uf.union(2, 4);
        uf.union(4, 8);
        System.out.println(uf.find(2));
        System.out.println(uf.find(8));
        System.out.println(uf.isConnect(2, 8));
        System.out.println(uf.find(1));
    }
}

