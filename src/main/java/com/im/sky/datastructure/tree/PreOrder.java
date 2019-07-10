package com.im.sky.datastructure.tree;

import java.util.Stack;

/**
 * Created by jiangchangwei on 2019/7/10.
 *
 * 二叉树的前序遍历
 *
 * 树的定义符合递归的形式，如果遍历采用递归结构，遍历非常方便。如果采用非递归结构，写起来稍微麻烦些。
 *
 * 遍历的过程类似于深度搜索的过程
 */
public class PreOrder {

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
        noRecursive(head);

    }

    /**
     * 递归形式，写起来非常简单。
      * @param head
     */
    public static void recursive(Node head) {
        if(head == null) {
            return;
        }
        System.out.print(head.val + " ");
        recursive(head.left);
        recursive(head.right);
    }

    public static void noRecursive(Node head) {
        if(head  == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while(!stack.isEmpty()) {
            Node node = stack.pop();
            while(node != null) {
                System.out.print(node.val + " ");
                if(node.right != null) {
                    stack.push(node.right);
                }
                node = node.left;
            }
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
