package com.im.sky.lock.juc.aqs.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 12:56
 **/
public class ReentrantLockTest<T> {

    /**
     * 公平锁的实现
     */
    private static ReentrantLock lock = new ReentrantLock(false);

    private static Condition notFull = lock.newCondition();

    private static Condition notEmpty = lock.newCondition();

    private Object[] items;

    private static final int DEFAULT_CAPACITY = 8;

    private int readIndex = -1;

    private int writeIndex = -1;

    private int count;

    private int size = DEFAULT_CAPACITY;

    public ReentrantLockTest(int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException();
        }
        items = new Object[capacity];
        this.size = capacity;
    }

    public ReentrantLockTest() {
        items = new Object[DEFAULT_CAPACITY];
    }

    public static void main(String[] args) {
        ReentrantLockTest<Integer> test = new ReentrantLockTest<>();
        Thread consumer1 = new Thread(()->{
            while(true) {
                try {
                    int m = test.take();
                    System.out.println(Thread.currentThread() + ":" + m);
                }catch (Exception e) {}
            }
        });

        Thread consumer2 = new Thread(() -> {
            while(true) {
                try {
                    int m = test.take();
                    System.out.println(Thread.currentThread() + ":" + m);
                }catch (Exception e) {}
            }
        });

        Thread producer = new Thread(()->{
            while(true) {
                try {
                    test.put(1);
                    Thread.sleep(1000);
                }catch (Exception e) {}
            }
        });
        consumer1.start();
        consumer2.start();
        producer.start();
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            if(count == size) {
                notFull.await();
            }
            count++;
            writeIndex = (writeIndex + 1) % size;
            items[writeIndex] = t;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            if(count == 0) {
                notEmpty.await();
            }
            readIndex = (readIndex + 1) % size;
            count--;
            notFull.signal();
            return (T)items[readIndex];
        }finally {
            lock.unlock();
        }
    }
}
