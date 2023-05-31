package com.im.sky.datastructure.queue;

import java.util.Arrays;

public class SkipListV2 {

    private Node head;

    private final int maxLevel = 32;

    private final double factor = 0.5;

    private int curMax = 0;

    public static void main(String[] args) {
        SkipListV2 skipListV2 = new SkipListV2();
        skipListV2.insert(3);
        skipListV2.insert(1);
        skipListV2.insert(3);
        skipListV2.insert(2);
        skipListV2.insert(5);
        System.out.println(skipListV2.find(3));
        skipListV2.remove(3);
        System.out.println(skipListV2.find(3));
        skipListV2.remove(3);
        System.out.println(skipListV2.find(3));
        skipListV2.insert(3);
        System.out.println(skipListV2.find(3));
    }

    public SkipListV2() {
        head = new Node(-1, maxLevel);
    }

    public void remove(int val){
        Node[] pre = new Node[curMax];
        Arrays.fill(pre, head);
        Node node = head;
        int level = curMax - 1;
        while(level >= 0) {
            if(node.next[level] != null && node.next[level].val < val) {
                node = node.next[level];
            }else {
                pre[level] = node;
                level--;
            }
        }
        for(int i = 0; i < curMax; i++) {
            if(pre[i].next[i] != null && pre[i].next[i].val == val) {
                pre[i].next[i] = pre[i].next[i].next[i];
            }
        }
    }

    public void insert(int val) {
        int curMaxLevel = randomLevel();
        Node[] pre = new Node[curMaxLevel];
        Arrays.fill(pre, head);
        Node node = head;
        int level = curMaxLevel - 1;
        while(level >= 0) {
            if(node.next[level] != null && node.next[level].val < val) {
                node = node.next[level];
            }else {
                pre[level] = node;
                level--;
            }
        }
        Node cur = new Node(val, curMaxLevel);
        for(int i = 0; i < curMaxLevel; i++) {
            Node tmp = pre[i].next[i];
            cur.next[i] = tmp;
            pre[i].next[i] = cur;
        }
        curMax = Math.max(curMaxLevel, curMax);
    }

    public boolean find(int val) {
        Node node = head;
        int level = curMax - 1;
        while(level >= 0) {
            if(node.next[level] != null && node.next[level].val < val) {
                node = node.next[level];
            }else {
                level--;
            }
        }
        return node.next[0] != null && node.next[0].val == val;
    }

    private int randomLevel() {
        int level = 1;
        while(Math.random() < factor && level < maxLevel) {
            level++;
        }
        return level;
    }

    private static class Node {
        private int val;

        private Node[] next;

        Node(int val, int maxLevel) {
            this.val = val;
            this.next = new Node[maxLevel];
        }
    }
}
