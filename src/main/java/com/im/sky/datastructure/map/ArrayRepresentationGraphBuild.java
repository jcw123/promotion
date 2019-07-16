package com.im.sky.datastructure.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangchangwei on 2019/7/11.
 *
 * 建立数组表示法的图结构
 *
 * （1）一个数组或者hash结构用于存储顶点；
 * （2）一个二维数组用于存储顶点之间的关系
 *
 * 图使用数组的方式存储很方便，但如果图的边很少的话，而节点数很多的话将严重浪费存储空间。
 *
 * 建立无向图
 */
public class ArrayRepresentationGraphBuild implements GraphBuild {

    private Map<Character, Integer> nodeMap = new HashMap<>();

    private int[][] nodeEdges;

    public static void main(String[] args) throws Exception {
        ArrayRepresentationGraphBuild object = new ArrayRepresentationGraphBuild();
        object.build();
        System.out.println(toString(object.nodeEdges));
    }


    private static String toString(int[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < rows; i++) {
            sb.append("[").append(Arrays.toString(array[i])).append("]\n");
        }
        return sb.toString();
    }


    @Override
    public void build() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        nodeMap.clear();
        for(int i = 0; i < n; i++) {
            nodeMap.put(br.readLine().charAt(0), i);
        }
        nodeEdges = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                nodeEdges[i][j] = Integer.MAX_VALUE;
            }
        }
        String s;
        String[] sArray;
        while(!"#".equals(s = br.readLine())) {
            sArray = s.split(" ");
            int i = nodeMap.get(sArray[0].charAt(0));
            int j = nodeMap.get(sArray[1].charAt(0));
            int w = Integer.valueOf(sArray[2]);
            nodeEdges[i][j] = nodeEdges[j][i] = w;
        }


    }
}
