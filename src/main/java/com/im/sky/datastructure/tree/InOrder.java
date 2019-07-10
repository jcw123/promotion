package com.im.sky.datastructure.tree;

import java.util.Stack;

/**
 * Created by jiangchangwei on 2019/7/10.
 *
 * 中序遍历
 */
public class InOrder {

    public static void main(String[] args) {
        Node head = new Node(1, null, null);
        Node a = new Node(2,null, null);
        Node b = new Node(3, null, null);
        Node c = new Node(4, null, null);
        Node d = new Node(5, null, null);
        Node e = new Node(6, null, null);
        head.left= a;
        head.right = b;
        a.left = c;
        a.right = d;
        b.left = e;
        recursive(head);
        System.out.println();
        norecursive(head);
    }

    /**
     * 递归形式
     * @param head
     */
    public static void recursive(Node head) {
        if(head == null) {
            return;
        }
        recursive(head.left);
        System.out.print(head.val + " ");
        recursive(head.right);
    }

    /**
     * 非递归定义
     * @param head
     */
    public static void norecursive(Node head) {
        if(head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node p = head;
        while(!stack.isEmpty()) {
            while(p != null && p.left != null) {
                stack.push(p.left);
                p = p.left;
            }
            p = stack.pop();
            System.out.print(p.val + " ");
            if(p.right != null) {
                stack.push(p.right);
            }
            p = p.right;
        }
    }

    private static class Node {
        private int val;

        private Node left;

        private Node right;

        private Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
