package com.im.sky.datastructure.sort;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 下午 6:36
 * @Version 1.0
 *
 * 基数排序，当需要根据多个key进行数据排序时，可以使用基数排序
 *
 * 最高位优先法
 *
 * 最低位优先法
 *
 * 牺牲空间，如果数据量很大，且key的个数比较好，用基数排序貌似挺快的
 */
public class RadixSorting {

    public static void main(String[] args) {
        Node node = new Node(-1);
        Node node1 = new Node(2);
        node.next = node1;
        Node node2 = new Node(1);
        node1.next = node2;
        sort(node);
        System.out.println(node.next.k);
    }

    public static void sort(Node head) {
        if(head == null || head.next == null) {
            return;
        }
        int d = 5;
        int r = 10;
        Node[] f = new Node[r + 1];
        Node[] e = new Node[r + 1];
        Node p;
        int j;
        Node t;
        for(int i = d; i >=1; i--) {
            for(j = 0; j <= r; j++) {
                f[j] = null;
            }
            p = head.next;
            while(p != null) {
                j = ord(p, d - i);
                if(f[j] == null) {
                    f[j] = p;
                }else {
                    e[j].next = p;
                }
                e[j] = p;
                p = p.next;
            }
            for(j = 0;f[j] == null; j++){}
            head.next = f[j];
            t = e[j];
            while(j < r) {
                j++;
                if(f[j] != null) {
                    t.next = f[j];
                    t = e[j];
                }
            }
            t.next = null;
        }
    }

    private static int ord(Node node, int index) {
        int val = node.k;
        return val / (int)Math.pow(10, index) % 10;
    }

    private static class Node {
        int k;
        Node next;

        private Node(int k) {
            this.k = k;
        }
    }
}
