package com.im.sky.lock.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author jiangchangwei
 * @date 2020-5-26 下午 4:49
 **/
public class CountDownLatchV2 {

    private Syn syn;

    public CountDownLatchV2(int permits) {
        if(permits <= 0) {
            throw new IllegalArgumentException();
        }
        syn = new Syn(permits);
    }

    public void obtain() {
        syn.releaseShared(1);
    }

    public void obtain(int permits) {
        syn.releaseShared(permits);
    }

    public void await() {
        syn.acquireShared(1);
    }

    public void await(long time, TimeUnit timeUnit) throws InterruptedException {
        syn.tryAcquireSharedNanos(1, timeUnit.toNanos(time));
    }

    private static class Syn extends AbstractQueuedSynchronizer {

        Syn(int permits) {
            setState(permits);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            int state = getState();
            return state == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;) {
                int state = getState();
                if(state == 0) {
                    return false;
                }
                int c = state - 1;
                if(compareAndSetState(state, c)) {
                    return c == 0;
                }
            }
        }
    }

}
