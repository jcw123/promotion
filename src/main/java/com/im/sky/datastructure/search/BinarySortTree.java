package com.im.sky.datastructure.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: jiangcw
 * @Date: 2019-7-21 下午 3:20
 * @Version 1.0
 *
 * 二叉排序树
 *
 * 删除节点后保证还要保证二叉排序树的性质稍微麻烦些。
 */
public class BinarySortTree {

    public static void main(String[] args) throws Exception {
        BinarySortTreeBase binarySortTreeBase = new BinarySortTreeBase();
        binarySortTreeBase.buildBinarySortTree();
        System.out.println(binarySortTreeBase.search(1));
        binarySortTreeBase.add(1);
        binarySortTreeBase.add(3);
        System.out.println(binarySortTreeBase.search(3));
        binarySortTreeBase.add(7);
        binarySortTreeBase.delete(3);
        System.out.println(binarySortTreeBase.search(3));
    }

    private static class BinarySortTreeBase {

        private Node head;

        /**
         * 删除二叉排序树上的一个值
         * @param val
         */
        public void delete(int val) {
            if(head == null) {
                return;
            }
            Node p = head;
            Node q = null;
            while(p != null) {
                if(p.val == val) {
                    break;
                }
                q = p;
                if(p.val > val) {
                    p = p.left;
                }else {
                    p = p.right;
                }
            }
            if(p == null) {
                return;
            }
            if(p.left == null) {
                if(q == null) {
                    head = p.right;
                    return;
                }
                if(q.left == p) {
                    q.left = p.right;
                }else {
                    q.right = p.right;
                }
            }else {  //当p的左子树存在时
                Node f = p;
                Node h = p.left;
                while(h.right != null) {
                    f = h;
                    h = h.right;
                }
                p.val = h.val;
                if(f == p) {
                    f.left = h.left;
                }else {
                    f.right = h.left;
                }
            }
        }

        /**
         * 往二叉排序树上加上一个新的值
         * @param val
         */
        public void add(int val) {
            Node node = new Node();
            node.val = val;
            if(head == null) {
                head = node;
            }else {
                Node p = head;
                while(p != null) {
                    if(p.val == val) {
                        return;
                    }else if(p.val > val) {
                        if(p.left == null) {
                            p.left = node;
                            break;
                        }else {
                            p = p.left;
                        }
                    }else {
                        if(p.right == null) {
                            p.right = node;
                            break;
                        }else {
                            p = p.right;
                        }
                    }
                }
            }
        }

        /**
         * 二叉排序树的查找
         * @param val
         * @return
         */
        public Node search(int val) {
            if(head == null) {
                return null;
            }
            Node p = head;
            while(p != null) {
                if(p.val == val) {
                    return p;
                }else if(p.val < val) {
                    p = p.right;
                }else {
                    p = p.left;
                }
            }
            return null;
        }

        public BinarySortTreeBase buildBinarySortTree() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            int val;
            Node p;
            while((s = br.readLine()) != null && !"".equals(s)) {
                val = Integer.valueOf(s);
                if(head == null) {
                    Node node = new Node();
                    node.val = val;
                    head = node;
                }else {
                    p = head;
                    while(p != null) {
                        if(val > p.val) {
                            if(p.right == null) {
                                Node node = new Node();
                                node.val = val;
                                p.right = node;
                                break;
                            }else {
                                p = p.right;
                            }
                        }else if(val < p.val) {
                            if(p.left == null) {
                                Node node = new Node();
                                node.val = val;
                                p.left = node;
                                break;
                            }else {
                                p = p.left;
                            }
                        }else {
                            break;
                        }
                    }
                }
            }
            return this;
        }

        private static class Node {
            private int val;

            private Node left;

            private Node right;

            @Override
            public String toString() {
                return "val:" + val;
            }
        }

    }


}
