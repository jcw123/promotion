package com.im.sky.lock.juc.aqs.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author jiangchangwei
 * @date 2020-8-20 下午 8:25
 *
 * 实现公平的锁
 *
 **/
public class FIFOMutexBaseAQS {

    public static void main(String[] args) {

        FIFOMutexBaseAQS lock = new FIFOMutexBaseAQS();
        CyclicBarrier barrier = new CyclicBarrier(10);
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    lock.lock();
                    System.out.println("start");
                    Thread.sleep(1000);
                    System.out.println("end");
                    lock.unlock();

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }

    private Sync sync;

    public FIFOMutexBaseAQS() {
        this.sync = new Sync();
    }

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            int state = getState();
            if(state == 0) {
                if(!hasQueuedPredecessors() && compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
            }else if(getExclusiveOwnerThread() == Thread.currentThread()) {
                int m = state + arg;
                if(m < 0) {
                    throw new Error("Maximum lock count exceed");
                }
                setState(m);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
                int remain = getState() - arg;
                if(getExclusiveOwnerThread() != Thread.currentThread()) {
                    throw new IllegalMonitorStateException();
                }
                boolean release = false;
                if(remain == 0) {
                    setExclusiveOwnerThread(null);
                    release = true;
                }
                setState(remain);
                return release;
        }

    }
}
