package com.im.sky.lock.juc.aqs.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 9:33
 **/
public class ReentrantReadWriteLockTest {

    public Map<Integer, Integer> map = new HashMap<>();

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Lock readLock = lock.readLock();

    private Lock writeLock = lock.writeLock();

    public static void main(String[] args) {
        ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
        Thread t1 = new Thread(() -> {
            Random random = new Random();
            while(true) {
                try {
                    System.out.println(test.get(random.nextInt(10)));
                    Thread.sleep(100);
                }catch (Exception e) {}
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            Random random = new Random();
            while(true) {
                try {
                    test.put(random.nextInt(10), random.nextInt(10));
                    Thread.sleep(1000);
                }catch (Exception e) {}
            }
        });
        t2.start();
    }

    public Integer get(Integer key) {
        readLock.lock();
        try {
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }

    public void put(Integer key, Integer value) {
        writeLock.lock();
        try {
            map.put(key, value);
        }finally {
            writeLock.unlock();
        }
    }


}
