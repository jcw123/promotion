package com.im.sky.lock.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
/**
 * @author jiangchangwei
 * @date 2019-11-30 上午 10:50
 **/
public class NonReentrantLock implements Lock{

    private Sync sync;

    public NonReentrantLock() {
        this.sync = new NonFairSync();
    }

    public NonReentrantLock(boolean fair) {
        this.sync = fair ? new FairSync() : new NonFairSync();
    }

    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.nonFairTryAcquire();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    private abstract static class Sync extends AbstractQueuedSynchronizer {

        abstract void lock();

        @Override
        protected boolean tryRelease(int arg) {
            if(getExclusiveOwnerThread() != Thread.currentThread()) {
                throw new IllegalMonitorStateException();
            }
            setState(0);
            setExclusiveOwnerThread(null);
            return true;
        }

        final boolean nonFairTryAcquire() {
            Thread t = Thread.currentThread();
            int s = getState();
            if(s == 0) {
                if(compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(t);
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {return new ConditionObject();}
    }

    private static class FairSync extends Sync {

        @Override
        void lock() {
            acquire(1);
        }

        @Override
        protected boolean tryAcquire(int arg) {
             Thread t = Thread.currentThread();
             int state = getState();
             if(state == 0) {
                 if(!hasQueuedPredecessors() && compareAndSetState(0, 1)) {
                     setExclusiveOwnerThread(t);
                     return true;
                 }
             }
             return false;
        }
    }

    private static class NonFairSync extends Sync {
        @Override
        void lock() {
            if(compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            } else {
                acquire(1);
            }
        }

        @Override
        protected boolean tryAcquire(int arg) {
            return nonFairTryAcquire();
        }
    }
}
