package com.im.sky.lock.juc.aqs.test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jiangchangwei
 * @date 2020-5-26 下午 9:53
 **/
public class FIFOMutex {

    private final AtomicBoolean state = new AtomicBoolean();

    private final Queue<Thread> queue = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean isInterrupted = false;
        Thread current = Thread.currentThread();
        queue.add(current);
        while(queue.peek() != Thread.currentThread() || !state.compareAndSet(false, true)) {
            LockSupport.park(this);
            if(Thread.interrupted()) {
                isInterrupted = true;
            }
        }
        queue.remove();
        if(isInterrupted) {
            current.interrupt();
        }
    }

    public void unlock() {
        state.set(false);
        LockSupport.unpark(queue.peek());
    }
}
