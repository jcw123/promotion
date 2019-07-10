package com.im.sky.datastructure.tree;

import java.util.Stack;

/**
 * Created by jiangchangwei on 2019/7/10.
 */
public class MaxDepthForTree {

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
        System.out.println(maxDepthRecursive(head));
        System.out.println(maxDepthUsingInOrder(head));
    }

    public static int maxDepthRecursive(Node head) {
        if(head == null) {
            return 0;
        }
        return 1 + Math.max(maxDepthRecursive(head.left), maxDepthRecursive(head.right));
    }

    public static int maxDepthUsingInOrder(Node head) {
        if(head == null) {
            return 0;
        }
        //当访问到叶子节点时就记录下最大的深度
        int maxDepth = 1;
        Stack<Node> stack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        stack.push(head);
        depthStack.push(1);
        Node p = head;
        int max = maxDepth;
        boolean pIsNull = false;
        while(!stack.isEmpty()) {
            while(p != null && p.left != null) {
                stack.push(p.left);
                maxDepth++;
                depthStack.push(maxDepth);
                p = p.left;
            }
            p = stack.pop();
            maxDepth = depthStack.pop();
            if(p.left == null && p.right == null) {
                max = Math.max(max, maxDepth);
            }
            p = p.right;
            maxDepth++;
        }
        return max;
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
