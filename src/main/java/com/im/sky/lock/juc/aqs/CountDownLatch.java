package com.im.sky.lock.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 9:01
 **/
public class CountDownLatch {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(2);
        for(int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println("你好吗" + finalI);
                try {
                    Thread.sleep(2000);
                    latch.countDown();
                }catch (Exception ignored) {}
            }).start();
        }
        latch.await();
        System.out.println("main");
    }

    private Sync sync;

    public CountDownLatch(int permits) {
        sync = new Sync(permits);
    }

    public void countDown() {
        sync.releaseShared(1);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public void await(long t, TimeUnit unit) throws InterruptedException {
        sync.tryAcquireSharedNanos(1, unit.toNanos(t));
    }

    private static class Sync extends AbstractQueuedSynchronizer {



        Sync(int permits) {
            if(permits <= 0) {
                throw new IllegalArgumentException();
            }
            setState(permits);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            int s = getState();
            return s == 0 ? 1 : -1;
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
