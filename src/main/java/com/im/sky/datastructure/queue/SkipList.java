package com.im.sky.datastructure.queue;

import java.util.*;

/**
 * @author jiangchangwei
 * @date 2020-7-16 下午 5:21
 *
 * 严格有序，不允许重复
 **/
public class SkipList<T extends Comparable<T>> {

    public static void main(String[] args) {
        SkipList<Integer> list = new SkipList<>();
        list.add(3);
        list.add(5);
        list.add(2);
        System.out.println(list.contains(3));
        list.remove(3);
        System.out.println(list.contains(3));
        String s = "";
    }

    private static final Random random = new Random();

    private static final double p = 0.5;

    private static final int maxLevel = 32;

    private final Node root;

    private int size;

    /**
     * 记录拥有元素的最高一层
     */
    private int highestLevel;

    public SkipList() {
        root = new Node(maxLevel);
    }

    public boolean add(T key) {
        if(key == null) {
            return false;
        }
        int level = randomLevel();
        Node<T> newNode = new Node<>(level, key);
        Node cur = root;
        for(int i = maxLevel - 1; i >= 0; i--) {
            Node node = cur;
            Node tmp = null;
            while(i < node.level && (tmp = node.nextPointers[i]) != null && key.compareTo((T)tmp.key) > 0) {
                node = tmp;
            }
            if(tmp != null && key.compareTo((T)tmp.key) == 0) {
                return false;
            }else {
                Node next = null;
                if(i < node.level) {
                    next = node.nextPointers[i];
                }
                if(i < newNode.level) {
                    newNode.nextPointers[i] = next;
                }
                if(i < node.level) {
                    node.nextPointers[i] = newNode;
                }
                cur = node;
            }

        }
        size++;
        return true;
    }

    public void remove(T key) {
        if(key == null) {
            return;
        }
        Node node = root;
        boolean find = false;
        for(int i = maxLevel - 1; i >= 0; i--) {
            Node tmp;
            while(i < node.level && (tmp = node.nextPointers[i]) != null && key.compareTo((T)tmp.key) > 0) {
                node = node.nextPointers[i];
            }
            if(i < node.level && (tmp = node.nextPointers[i]) != null && key.compareTo((T)tmp.key) == 0) {
                find = true;
                Node tmp2 = i < tmp.level ? tmp.nextPointers[i] : null;
                node.nextPointers[i] = tmp2;
            }
        }
        if(find) {
            size--;
        }
    }

    public boolean contains(T key) {
        if(key == null) {
            return false;
        }
        Node node = root;
        for(int i = maxLevel - 1; i >= 0; i--) {
            Node tmp;
            while(i < node.level && (tmp = node.nextPointers[i]) != null && key.compareTo((T)tmp.key) > 0) {
                node = node.nextPointers[i];
            }
            if(i < node.level && (tmp = node.nextPointers[i]) != null && key.compareTo((T)tmp.key) == 0) {
               return true;
            }
        }
        return false;
    }

    public T get(int index) {
        if(index < 0 || index >= size) {
            throw new IllegalArgumentException("index out of bound");
        }
        int i = 0;
        Node node = root.nextPointers[0];
        while(i < index) {
            node = node.nextPointers[i];
            i++;
        }
        return (T)node.key;
    }

    private int randomLevel() {
        int level = 1;
        while(random.nextDouble() < p && level < maxLevel) {
            level++;
        }
        return level;
    }

    private static class Node<M> {
        private M key;

        private Node[] nextPointers;

        private int[] distance;

        private int level;

        Node() {

        }

        Node(int level, M key) {
            nextPointers = new Node[level];
            this.level = level;
            this.key = key;
        }

        Node(int level) {
            nextPointers = new Node[level];
            this.level = level;
        }
    }

}
