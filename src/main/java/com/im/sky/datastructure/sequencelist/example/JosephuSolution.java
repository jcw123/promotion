package com.im.sky.datastructure.sequencelist.example;

/**
 * @Author: jiangcw
 * @Date: 2019-7-7 下午 2:32
 * @Version 1.0
 * 约瑟夫问题
 * 注：涉及元素的出队顺序
 *
 * 可以采用顺序表或者链式表结构
 */
public class JosephuSolution {

    public static void main(String[] args) {
        solveBySequenceList(20, 5, 7);
        System.out.println();
        solveByChainList(20, 5, 7);
    }

    /**
     * 使用顺序表解决这个问题
     * 通过顺序表实现时，需要额外的空间记录某一个位置的元素是否已经出队列了
     * @param n
     * @param k
     * @param m
     */
    public static void solveBySequenceList(int n, int k, int m) {
        if(n <= 0) {
            return;
        }
        int[] arr = new int[n];
        // 用于记录对应位置的元素是否出队
        boolean[] hasDequeue = new boolean[n];
        m = m % n;
        for(int i = 0; i < n; i++) {
            arr[i] =  i + 1;
        }
        int dequeueCount = 0;
        int startIndex = k - 1;
        int incre = 0;
        while(dequeueCount < n) {
            if(hasDequeue[startIndex]) {
                startIndex = (startIndex + 1) % n;
                continue;
            }
            incre++;
            if(incre % m == 0) {
                dequeueCount++;
                hasDequeue[startIndex] = true;
                System.out.print(arr[startIndex] + " ");
                incre = 0;
            }
            startIndex = (startIndex + 1) % n;
        }

    }

    /**
     * 使用链式表解决这个问题
     * @param n
     * @param k
     * @param m
     * 构造一个单向循环列表解决问题
     */
    public static void solveByChainList(int n, int k, int m) {
        if(n <= 0) {
            return;
        }
        Node head = new Node(1, null);
        Node p = head;
        int j = 1;
        m = m % n;
        Node targetPre = null;
        for(int i = 2; i <= n; i++) {
            Node next = new Node(i, null);
            if(j == k - 1) {
                targetPre = p;
            }
            j++;
            p.next = next;
            p = next;
        }
        if(targetPre == null) {
            targetPre = p;
        }
        p.next = head;
        int deQueueCount = 0;
        int i = 0;
        while(deQueueCount < n) {
            if(i == m - 1) {
                Node q = targetPre.next;
                targetPre.next = q.next;
                System.out.print(q.val + " ");
                deQueueCount++;
                i = 0;
            }else {
                i++;
                targetPre = targetPre.next;
            }
        }

    }

    private static class Node {
        private int val;

        private Node next;


        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

}
