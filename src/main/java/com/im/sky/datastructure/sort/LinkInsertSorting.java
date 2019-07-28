package com.im.sky.datastructure.sort;

/**
 * @Author: jiangcw
 * @Date: 2019-7-28 上午 11:35
 * @Version 1.0
 *
 * 链表插入排序
 *
 *查找节点的时间复杂度为O（N^2）;记录移动时间复杂度O（n）
 */
public class LinkInsertSorting {

    public static void main(String[] args) {
        Node head = new Node(Integer.MAX_VALUE);
        Node node1 = new Node(1);
        head.next = node1;
        Node node2 = new Node(2);
        node1.next = node2;
        Node node3 = new Node(4);
        node2.next = node3;
        Node node4 = new Node(3);
        node3.next = node4;
        Node node5 = new Node(5);
        node4.next = node5;
        Node node6 = new Node(9);
        node5.next = node6;
        Node node7 = new Node(2);
        node6.next = node7;
        sort(head);
        while (head.next != null) {
            System.out.println(head.next.val);
            head = head.next;
        }

    }

    public static void sort(Node head) {
        if(head == null || head.next == null) {
            return;
        }
        Node p = head;
        Node q = p.next;
        p.next = null;
        Node next;
        while(q != null) {
            while(p.next != null && q.val >= p.next.val) {
                p = p.next;
            }
            next = q.next;
            q.next = p.next;
            p.next = q;
            q = next;
            p = head;
        }
    }

    private static class Node {
        private int val;

        private Node next;

        private Node(int val) {
            this.val = val;
        }
    }
}
