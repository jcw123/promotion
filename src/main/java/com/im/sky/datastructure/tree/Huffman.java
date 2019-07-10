package com.im.sky.datastructure.tree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by jiangchangwei on 2019/7/10.
 *
 *
 */
public class Huffman {

    public static void main(String[] args) throws Exception {
        Node head = buildHuffman();
        huffmanDecode(head);
        System.out.println(huffmanEncode(head).toString());
    }

    public static Set<CEncode> huffmanEncode(Node head) throws Exception {
        if(head == null) {
            return null;
        }
        Set<CEncode> result = new HashSet<>();
        String s = "";
        Stack<Node> stack = new Stack<>();
        Stack<String> stackEncode = new Stack<>();
        stack.push(head);
        stackEncode.push("");
        Node p = head;
        while(!stack.isEmpty()) {
            while(p != null && p.left != null) {
                stack.push(p.left);
                stackEncode.push(s + "0");
                p = p.left;
            }
            p = stack.pop();
            s = stackEncode.pop();
            if(p.left == null && p.right == null) {
                result.add(new CEncode(s, p.val));
            }
            if(p.right != null) {
                stack.push(p.right);
                stackEncode.push(s + "1");
            }
            p = p.right;
        }
        return result;
    }

    public static void huffmanDecode(Node head) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        Node p = head;
        for(char c : s.toCharArray()) {
            if(c == '0') {
                p = p.left;
            }else {
                p = p.right;
            }
            if(p.left == null && p.right == null) {
                System.out.print(String.valueOf(p.val));
                p = head;
            }
        }
    }


    public static Node buildHuffman() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        PriorityQueue<Node> queue = new PriorityQueue<>(2 * n, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.weight - o2.weight;
            }
        });
        for(int i = 1; i <= n; i++) {
            String[] s = br.readLine().split(" ");
            Node t = new Node(Integer.valueOf(s[0]), s[1].charAt(0));
            queue.offer(t);
        }
        Node p;
        Node q;
        // 利用java自带的优先级队列，很容易找到权值最小的两个元素。
        for(int i = n + 1; i < 2 * n; i++) {
            if(queue.size() <= 1) {
                break;
            }
            p = queue.poll();
            q = queue.poll();
            Node m = new Node(p.weight + q.weight);
            m.left = p;
            m.right = q;
            queue.offer(m);
            p.parent = m;
            q.parent = m;
        }
        return queue.poll();
    }

    private static class Node {
        private char val;

        private int weight;

        private Node parent;

        private Node left;

        private Node right;

        private Node(int weight, char val) {
            this.weight = weight;
            this.val = val;
        }

        private Node(int weight) {
            this.weight = weight;
        }

    }

    private static class CEncode {
        /**
         * 由0和1构成的字符串
         */
        private String s;

        /**
         * 字符本身
         */
        private char c;

        public CEncode(String s, char c) {
            this.s = s;
            this.c = c;
        }
    }
}
