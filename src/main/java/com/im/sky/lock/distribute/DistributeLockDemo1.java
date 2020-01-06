package com.im.sky.lock.distribute;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangchangwei
 * @date 2019-12-8 下午 2:13
 *
 * 利用zookeeper的分布式锁思想来控制线程的同步访问
 **/
public class DistributeLockDemo1 {

    private static AtomicLong id = new AtomicLong();

    private static SmallHeap heap = new SmallHeap();

    private static Set<Thread> allThread = new HashSet<>();

    public static boolean lock() {
        Thread thread = Thread.currentThread();
        if(heap.first().thread == thread) {
            heap.first().state++;
            return true;
        }
        return false;
    }

    public static void unlock() {
        if(heap.first().thread == Thread.currentThread()) {
            if(heap.first().state > 1) {
                heap.first().state--;
                return;
            }
            heap.deleteRoot();
            allThread.remove(Thread.currentThread());
        }else {
            throw new RuntimeException("当前线程并没有持有锁，不能执行当前操作");
        }
    }

    public static void register(Thread thread) {
        if(allThread.contains(thread)) {
            return;
        }
        Node node = new Node(thread);
        allThread.add(thread);
        heap.add(node);
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            new Thread(() -> {
                while(true) {
                    try {
                        register(Thread.currentThread());
                        if (lock()) {
                            System.out.println("obtain lock, threadName:" + Thread.currentThread().getName());
                            unlock();
                        }
                        Thread.sleep(1000);
                    }catch (Exception e) {

                    }
                }
            }).start();
        }
    }


    private static class Node {
        /**
         * 小根堆对应的节点
         */
        private Thread thread;

        /**
         * 跟节点绑定唯一id，这个id是全局唯一的
         */
        private long selfId;

        private int state;


        Node(Thread thread) {
            this.thread = thread;
            this.selfId = id.incrementAndGet();
        }
    }

    /**
     * 这个小根堆只是为了服务于分布式锁而建立的，支持的操作只有增加节点、
     * 删除根节点，并且这些操作需要是同步的
     */
    private static class SmallHeap {

        List<Node> list;

        private static ReentrantLock lock = new ReentrantLock();

        Node first() {
            if(list.size() > 0) {
                return list.get(0);
            }
            return null;
        }

        void add(Node node) {
            lock.lock();
            try {
                if(list == null) {
                    list = new ArrayList<>();
                    list.add(node);
                }else {
                    list.add(node);
                    int len = list.size();
                    int i = len - 1;
                    int s;
                    Node tmp = list.get(i);
                    while(i > 0) {
                        s = (i - 1) / 2;
                        if(list.get(s).selfId > tmp.selfId) {
                            list.set(i, list.get(s));
                            i = s;
                        }else {
                            list.set(i, tmp);
                            break;
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        void deleteRoot() {
            lock.lock();
            try {
                if(list == null || list.size() == 0) {
                    return;
                }
                if(list.size() == 1) {
                    list.clear();
                    return;
                }
                Node last = list.get(list.size() - 1);
                list.set(0, last);
                list.remove(list.size() - 1);
                int len = list.size();
                int s = 0;
                int p = 2 * s + 1;
                Node root = list.get(0);
                while(p <= len - 1) {
                    if(p + 1 <= len - 1) {
                        if(list.get(p).selfId > list.get(p + 1).selfId) {
                            p = p + 1;
                        }
                    }
                    if(list.get(s).selfId > list.get(p).selfId) {
                        list.set(s, list.get(p));
                        s = p;
                        p = 2 * p + 1;
                    } else {
                        break;
                    }
                }
                list.set(s, root);
            }finally {
                lock.unlock();
            }
        }
    }
}
