package com.im.sky.datastructure.sort.sortcheck;

/**
 * 链表插入排序就是改变指针的过程中容易出错
 */
public class LinkedInsertSorting {

    public static void main(String[] args) {
        Node head = new Node(-1, null);
        Node node1 = new Node(3, null);
        Node node2 = new Node(5, null);
        Node node3 = new Node(2, null);
        Node node4 = new Node(4, null);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        sort(head);
        Node p = head.next;
        while(p != null) {
            System.out.println(p.val);
            p = p.next;
        }
    }

    public static void sort(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Node p, q;
        p = head.next;
        q = head;
        q.next = null;
        while(p != null) {
            while(q.next != null && p.val > q.next.val) {
                q = q.next;
            }
            Node tmp = p.next;
            p.next = q.next;
            q.next = p;
            p = tmp;
            q = head;
        }
    }

    private static class Node {

        private Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }

        int val;

        Node next;
    }
}
