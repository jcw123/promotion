package com.im.sky.lock.juc.aqs.test.cp;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 1:44
 **/
public class DataContainer {

    private Object[] items;

    private int size;

    private int count;

    private int readIndex = -1;

    private int writeIndex = -1;

    private final Object o = new Object();

    public DataContainer(int capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException();
        }
        items = new Object[capacity];
        size = capacity;
    }

    public void put(Object o) throws InterruptedException {
        synchronized (this.o) {
            if(count == size) {
                this.o.wait();
            }
            writeIndex = (writeIndex + 1) % size;
            items[writeIndex] = o;
            count++;
            this.o.notify();
        }
    }

    public Object take() throws InterruptedException {
        synchronized (o) {
            if(count == 0) {
                o.wait();
            }
            readIndex = (readIndex + 1) % size;
            count--;
            o.notify();
            return items[readIndex];
        }
    }
}
