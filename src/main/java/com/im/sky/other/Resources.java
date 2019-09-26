package com.im.sky.other;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 上午 11:58
 * @Version 1.0
 */
public class Resources {

    private int[] data;

    private volatile int count;

    private Object o = new Object();

    private static final int DEFAULT_SIZE = 128;

    private int n;

    public Resources(int size) {
        if(size <= 0) {
            size = DEFAULT_SIZE;
        }
        data = new int[size];
        this.n = size;
    }

    public int get() {
        synchronized (o) {
            if(count > 0) {
                if(count == n) {
                    o.notify();
                }
                return data[--count];
            }
            try {
                o.wait();
            }catch (Exception e) {

            }
            return data[--count];
        }
    }

    public void insert(int v) {
        synchronized (o) {
            if(count < n) {
                data[count++] = v;
                if(count == 1) {
                    o.notify();
                }
                return;
            }
            try {
                o.wait();
            }catch (Exception e) {

            }
            data[count++] = v;
        }
    }

}
