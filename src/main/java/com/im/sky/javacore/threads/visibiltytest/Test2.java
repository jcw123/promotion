package com.im.sky.javacore.threads.visibiltytest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-15 13:17
 **/
public class Test2 {

    ThreadLocal<Tree> threadLocal = new ThreadLocal<>();

    public Tree getTree() {
        Tree tree = threadLocal.get();
        if(tree == null) {
            tree = Tree.getInstance();
            threadLocal.set(tree);
        }
        return tree;
    }

    public void increment() {
        Tree tree = getTree();
        tree.increment();
    }


    public static class Tree {

        private static final Tree INSTANCE = new Tree();

        public static Tree getInstance() {
            return INSTANCE;
        }

        private Tree() {

        }

        int value;

        public int get() {
            return value;
        }

        public void increment() {
            value++;
        }
    }
}
