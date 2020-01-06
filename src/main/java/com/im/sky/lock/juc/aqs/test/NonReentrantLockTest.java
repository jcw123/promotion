package com.im.sky.lock.juc.aqs.test;

import com.im.sky.lock.juc.aqs.NonReentrantLock;

/**
 * @author jiangchangwei
 * @date 2019-11-30 上午 11:33
 **/
public class NonReentrantLockTest implements Runnable {

    NonReentrantLock lock = new NonReentrantLock();

    int count;

    public static void main(String[] args) {
        NonReentrantLockTest test = new NonReentrantLockTest();
        Thread t1 = new Thread(test, "thread1");
        t1.start();
        Thread t2 = new Thread(test, "thread2");
        t2.start();
    }

    @Override
    public void run() {
        while (this.count < 100) {
            test1();
            System.out.println(Thread.currentThread() + ": count:" + count);
        }
    }

    void test1() {
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
    }

    void test2() {
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
    }
}
