package com.im.sky.lock.juc.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangchangwei
 * @date 2020-5-27 下午 6:05
 **/
public class SimpleCyclicBarrier {

    private ReentrantLock lock = new ReentrantLock();

    private Condition tripped = lock.newCondition();

    private int permits;

    private int count;

    private Generation generation;

    private Runnable barrierCommand;

    private static class Generation {
        boolean broken = false;
    }

    public SimpleCyclicBarrier(int permits) {
        this(permits, null);
    }

    public SimpleCyclicBarrier(int permits, Runnable barrierCommand) {
        if(permits <= 0) {
            throw new IllegalArgumentException();
        }
        this.permits = permits;
        count = permits;
        generation = new Generation();
        this.barrierCommand = barrierCommand;
    }

    public void await() throws InterruptedException, BrokenBarrierException {
        try {
            doAwait(false, 0L);
        }catch (TimeoutException e) {
            throw new Error(e);
        }
    }

    private void breakBarrier() {
        generation.broken = true;
        tripped.notifyAll();
        count = permits;
    }

    private void nextGeneration() {
        tripped.notifyAll();
        generation = new Generation();
    }

    public void await(long time, TimeUnit timeUnit) throws InterruptedException, BrokenBarrierException,TimeoutException {
        doAwait(true, timeUnit.toNanos(time));
    }

    private int doAwait(boolean timed, long time) throws InterruptedException, BrokenBarrierException, TimeoutException {
        lock.lock();
        try {
            Generation g =  generation;
            if(g.broken) {
                throw new BrokenBarrierException();
            }
            int index = --count;
            if(index == 0) {
                boolean ranAction = false;
                try {
                    if(barrierCommand != null) {
                        barrierCommand.run();
                        ranAction = true;
                        nextGeneration();
                        return 0;
                    }
                }finally {
                    if(!ranAction) {
                        breakBarrier();
                    }
                }
            }

            for(;;) {
                try {
                    if (!timed) {
                        tripped.await();
                    } else if (time > 0L) {
                        time = tripped.awaitNanos(time);
                    }
                }catch (InterruptedException e) {
                    if(g != generation && !g.broken) {
                        breakBarrier();
                    }else {
                        Thread.currentThread().interrupt();
                    }
                }
                if(g.broken) {
                    throw new BrokenBarrierException();
                }
                if(g != generation) {
                    return count;
                }
                if(timed & time <= 0L) {
                    breakBarrier();
                    throw new TimeoutException();
                }

            }
        }finally {
            lock.unlock();
        }
    }
}
