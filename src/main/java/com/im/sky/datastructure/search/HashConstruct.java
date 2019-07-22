package com.im.sky.datastructure.search;

/**
 * Created by jiangchangwei on 2019/7/22.
 *
 *
 * 构造hash表示法，是查找的时间复杂度达到O(1)
 */
public class HashConstruct {

    private abstract static class HashForLinear {
        int m;
        Node[] data;

        HashForLinear(int m) {
            this.m = m;
            data = new Node[m];
        }

        int search(int k) {
            int hash = hash(k);
            int i = 0;
            while(i < m) {
                if(data[hash].key != k && data[hash] != null) {
                    i++;
                    hash  = (hash + i) % m;
                }else {
                    break;
                }
            }
            if(i == m) return -1;
            else return hash;
        }

        void insert(int k) {
            Node node = new Node(k);
            int j = search(k);
            if(j == -1 || data[j].key == k) return;
            data[j] = node;
        }

        private static class Node {
            int key;

            Node(int key) {
                this.key = key;
            }
        }

        abstract  int hash(int k);
    }

    private abstract static class HashForLink {

        int m;

        Node[] data;

        HashForLink(int m) {
            this.m = m;
            this.data = new Node[m];
        }

        Node search(int k) {
            int j = hash(k);
            Node p = data[j];
            while(p != null && p.key != k) {
                p =  p.next;
            }
            return p;
        }

        void insert(int k) {
            Node node = new Node(k);
            Node p = search(k);
            if(p != null && p.key == k)
                return;
            int j = hash(k);
            node.next = data[j];
            data[j] = node;
        }

        abstract int hash(int k);

        private static class Node {

            int key;
            Node next;

            Node(int k) {
                this.key  = k;
            }
        }
    }
}
