package com.im.sky.netty.test.test2.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-6-22 下午 8:26
 **/
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolCount = new AtomicInteger();

    final AtomicInteger threadCount = new AtomicInteger(1);

    final ThreadGroup group;

    final String namePrefix;

    final boolean isDaemon;

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    public NamedThreadFactory(String prefix, boolean isDaemon) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = prefix + "-" + poolCount.getAndIncrement() + "-";
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadCount.getAndIncrement(), 0);
        t.setDaemon(isDaemon);
        if(t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
