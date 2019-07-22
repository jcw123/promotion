package com.im.sky.datastructure.search;

/**
 * @Author: jiangcw
 * @Date: 2019-7-21 下午 4:42
 * @Version 1.0
 *
 * 平衡二叉排序树
 *
 * 任一一个节点左子树的深度和右子树的深度绝对值之差小于2，则此树就为平衡二叉排序树。
 *
 * 注：删除方法还没有实现，后续再实现 TODO
 */
public class BalancedBinaryTree {

    public static void main(String[] args) {
        BalancedBinaryTreeBase balancedBinaryTree = new BalancedBinaryTreeBase();
//        balancedBinaryTree.insert(1);
//        balancedBinaryTree.insert(2);
//        Node node = balancedBinaryTree.search(2);
//        System.out.println(node.val);
//        balancedBinaryTree.delete(2);
        balancedBinaryTree.insert(2);
        balancedBinaryTree.insert(3);
        balancedBinaryTree.insert(4);
        balancedBinaryTree.insert(5);
        balancedBinaryTree.insert(6);
        balancedBinaryTree.insert(7);
        balancedBinaryTree.insert(8);
        balancedBinaryTree.insert(9);
        balancedBinaryTree.insert(10);
//        inOrder(balancedBinaryTree.head);
        balancedBinaryTree.delete(7);
        balancedBinaryTree.delete(3);
        Node head = balancedBinaryTree.head;
//        Node node3 = balancedBinaryTree.search(2);
        inOrder(head);
//        System.out.println(node3.val);
    }

    static void inOrder(Node head) {
        if(head == null) {
            return;
        }
        inOrder(head.left);
        System.out.println("val:" + head.val + "bf:" + head.bf);
        inOrder(head.right);
    }


    private static class BalancedBinaryTreeBase {

        private Node head;

        /**
         * LL方式旋转的指针变换
         * @param head
         * @return
         */
        Node ll(Node head) {
            Node p = head.left;
            head.left = p.left;
            p.right = head;
            p.bf = head.bf = 0;
            return p;
        }

        /**
         * RR方式旋转的指针变换
         * @param head
         * @return
         */
        Node rr(Node head) {
            Node p = head.right;
            head.right = p.left;
            p.left = head;
            head.bf = p.bf = 0;
            return p;
        }

        /**
         * LR方式旋转的指针变换
         * @param head
         * @return
         */
        Node lr(Node head) {
            Node b = head.left;
            Node c = b.right;

            b.right= c.left;
            c.left = b;
            head.left = c;

            head.left = c.right;
            c.right = head;
            switch (c.bf) {
                case 1: head.bf = -1;b.bf = 0;break;
                case -1:head.bf = 0; b.bf = -1;break;
                case 0: head.bf = b.bf = 0; break;
                default: break;
            }
            c.bf = 0;
            return c;
        }

        /**
         * RL方式旋转的指针变换
         * @param head
         * @return
         */
        Node rl(Node head) {
            Node b = head.right;
            Node c = b.left;

            b.left = c.right;
            c.right = b;
            head.right = c;

            head.right = c.left;
            c.left = head;
            switch (c.bf) {
                case 1: head.bf = 0; b.bf = -1; break;
                case -1: head.bf = -1; b.bf = 0; break;
                case 0: head.bf = b.bf = 0; break;
                default:break;
            }
            c.bf = 0;
            return c;
        }

        /**
         * 插入节点
         * @param val
         */
        private void insert(int val) {
            Node node = new Node();
            node.val = val;
            Node p = null, q = null, sa = null, f = null;
            int d = 0;
            if(head == null) {
                this.head = node;
                return;
            }
            sa = p = head;
            while(p != null) {
                d = val - p.val;
                // 记录已经存在
                if(d == 0) {
                    return;
                }
                if(p.bf != 0) {
                    sa = p;
                    f = q;
                }
                q = p;
                if(d < 0) {
                    p = p.left;
                }else {
                    p = p.right;
                }
            }
            if(d < 0) {
                q.left = node;
            }else {
                q.right = node;
            }
            if(node.val < sa.val) {
                p = sa.left;
                q = p;
                sa.bf++;
            }else {
                p = sa.right;
                q = p;
                sa.bf--;
            }
            while(p != node) {
                if(node.val < p.val) {
                    p.bf = 1;
                    p = p.left;
                }else {
                    p.bf = -1;
                    p = p.right;

                }
            }
            switch (sa.bf) {
                case 2:
                    switch (q.bf) {
                        case 1: p = ll(sa); break;
                        case -1:p = lr(sa); break;
                        default:break;
                    }
                    if(f == null) {
                        this.head = p;
                    }else if(f.left == sa) {
                        f.left = p;
                    }else {
                        f.right = p;
                    }
                    break;
                case -2:
                    switch (q.bf) {
                        case -1:p = rr(sa);break;
                        case 1: p = rl(sa);break;
                        default:break;
                    }
                    if(f == null) {
                        this.head = p;
                    }else if(f.left == sa) {
                        f.left = p;
                    }else {
                        f.right = p;
                    }
                    break;
                default:break;
            }
        }

        /**
         * 删除节点
         * @param val
         */
        private void delete(int val) {
            if(this.head == null) {
                return;
            }
            // 动态变化的节点
            Node p = head;
            // p节点的父节点
            Node q = null;
            // sa表示离值等于val最近的平衡因子不等于0的节点
            Node sa = head;
            // f表示sa的父节点
            Node f = null;
            // 通过这个循环找到了值等于val的节点
            while(p != null) {
                if(p.bf != 0) {
                    sa = p;
                    f = q;
                }
                if(p.val == val) {
                    break;
                }else if(p.val < val) {
                    q = p;
                    p = p.right;
                }else {
                    q = p;
                    p = p.left;
                }
            }
            if(p == null) {
                return;
            }
            if(p.left == null) {
                Node k = sa;
                while(k != p) {
                    if(k.val < val) {
                        k.bf++;
                        k = k.right;
                    }else {
                        k.bf--;
                        k = k.left;
                    }
                }
                if(q == null) {
                    this.head = p.right;
                }else if(q.left == p) {
                    q.left  = p.right;
                }else {
                    q.right = p.right;
                }

            }else {
                Node t = p;
                Node u = p.left;
                while(u.right != null) {
                    if(u.bf != 0) {
                        sa = u;
                        f = t;
                    }
                    t = u;
                    u = u.right;
                }
                Node k = sa;
                while(k != u) {
                    if(k.val < val) {
                        k.bf++;
                        k = k.right;
                    }else {
                        k.bf--;
                        k = k.left;
                    }
                }
                p.val = u.val;
                if(p == t) {
                    t.left = u.left;
                }else {
                    p.right = u.left;
                }
            }
            if(sa.bf == 2) {
                p = sa.left;
                q = p;
            }else if(sa.bf == -2) {
                p = sa.right;
                q = p;
            }
            switch (sa.bf) {
                case 2:
                    switch (q.bf) {
                        case 1: p = ll(sa); break;
                        case 0: p = ll(sa); break;
                        case -1:p = lr(sa); break;
                        default:break;
                    }
                    if(f == null) {
                        this.head = p;
                    }else if(f.left == sa) {
                        f.left = p;
                    }else {
                        f.right = p;
                    }
                    break;
                case -2:
                    switch (q.bf) {
                        case -1:p = rr(sa);break;
                        case 0: p = rr(sa); break;
                        case 1: p = rl(sa);break;
                        default:break;
                    }
                    if(f == null) {
                        this.head = p;
                    }else if(f.left == sa) {
                        f.left = p;
                    }else {
                        f.right = p;
                    }
                    break;
                default:break;
            }
        }

        /**
         * 搜索节点
         * @param val
         */
        private Node search(int val) {
            if(head == null) {
                return null;
            }
            Node p = head;
            while(p != null) {
                if(p.val == val) {
                    return p;
                }else if(p.val > val) {
                    p = p.left;
                }else {
                    p = p.right;
                }
            }
            return null;
        }
    }

    private static class Node {
        /**
         * 节点自身的值
         */
        private int val;

        /**
         * 节点的平衡因子
         */
        private int bf;

        private Node left;

        private Node right;
    }
}
