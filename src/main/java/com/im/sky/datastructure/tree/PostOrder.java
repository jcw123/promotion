package com.im.sky.datastructure.tree;

import java.util.Stack;

/**
 * Created by jiangchangwei on 2019/7/10.
 *
 * 后续遍历
 */
public class PostOrder {

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

    public static void recursive(Node head) {
        if(head == null) {
            return;
        }
        recursive(head.left);
        recursive(head.right);
        System.out.print(head.val + " ");
    }

    public static void norecursive(Node head) {
        if(head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        // 这个属性用来记录最近一次访问的节点。如果访问某一个节点时，发现其右子树存在，且是最近访问的，则可以直接访问此节点
        //否则，需要重新将此节点进栈然后右子树进栈。
        Node lastPopNode = null;
        Node p = head;
        while(!stack.isEmpty()) {
            while(p != null && p.left != null) {
                stack.push(p.left);
                p = p.left;
            }
            p = stack.pop();
            if(p.right !=  null && p.right != lastPopNode) {
                    stack.push(p);
                stack.push(p.right);
                p = p.right;
            }else {
                System.out.print(p.val + " ");
                lastPopNode = p;
                p = null;
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
