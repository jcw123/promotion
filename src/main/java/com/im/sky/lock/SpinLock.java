package com.im.sky.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: jiangcw
 * @Date: 2019-10-17 下午 4:08
 * @Version 1.0
 *
 * 自旋锁的基本实现原理
 */
public class SpinLock {

    AtomicReference<Thread> cas = new AtomicReference<>();

    private int count;

    public boolean lock() {
        Thread thread = Thread.currentThread();
        if(cas.get() == thread) {
            count++;
            return true;
        }
        while(!cas.compareAndSet(null, thread)) {
            // do nothing, only spin
        }
        return true;
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        if(cas.get() == thread) {
            count--;
            if(count == 0) {
                cas.compareAndSet(thread, null);
            }
        }
    }

}
