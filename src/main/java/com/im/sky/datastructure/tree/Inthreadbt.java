package com.im.sky.datastructure.tree;

/**
 * Created by jiangchangwei on 2019/7/10.
 *
 * 建立中序下的线索化二叉树，并给出求前驱和后继的方法
 *
 * 还有前序下和后序下的线索化二叉树，线索化二叉树充分利用多余的
 * 指针，从而使查询一个节点的直接前驱和直接后继更为方便，同时遍历也更方便。
 */
public class Inthreadbt {


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
        inOrder(head);
    }

    private static Node pre;

    public static Node buildInThread(Node head) {
        if(head == null) {
            return null;
        }
        buildInThread(head.left);
        if(head.left == null) {
            head.leftTag = 1;
            head.left = pre;
        }
        if(head.right == null) {
            head.rightTag = 1;
        }
        if(pre != null && pre.rightTag == 1) {
            pre.right = head;
        }
        pre = head;
        buildInThread(head.right);
        return head;
    }

    /**
     *
     * 节点的前序
     * @return
     */
    public static Node inPre(Node p) {
        if(p == null) {
            return null;
        }
        Node pre = p.left;
        if(p.leftTag == 0) {
            while(pre != null && pre.rightTag == 0) {
                pre = pre.right;
            }
        }
        return pre;
    }

    /**
     * 节点的直接后继
     * @param p
     * @return
     */
    public static Node inSucc(Node p) {
        if(p == null) {
            return null;
        }

        Node succ = p.right;
        if(p.rightTag == 0) {
            while(succ != null && succ.leftTag == 0) {
                succ = succ.left;
            }
        }
        return succ;
    }

    public static void inOrder(Node head) {
        if(head == null) {
            return;
        }
        buildInThread(head);
        Node p = head;
        // 这一步是找到中序遍历下的第一个节点
        while(p.left != null && p.leftTag == 0) {
            p = p.left;
        }
        while(p != null) {
            System.out.print(p.val + " ");
            p = inSucc(p);
        }
    }

    private static void insert_R(Node p, Node s) {
        if(p != null && s != null) {
            s.rightTag = p.rightTag;
            s.right = p.right;
            p.rightTag = 0;
            p.right = s;
            s.leftTag = 1;
            s.left = p;
            if(s.rightTag == 0) {
                Node w = inSucc(s);
                w.left = s;
            }
        }
    }

    private static class Node {
        private int val;

        private Node left;

        private Node right;

        /**
         * 标志等于0时，表示左子树，等于1时，指向对应的前驱
         */
        private int leftTag;

        private int rightTag;

        private Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}

