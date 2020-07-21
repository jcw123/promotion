package com.im.sky.netty.test.test2.util;

import java.util.concurrent.*;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:45
 **/
public class ThreadPoolUtils {

    public static ThreadPoolExecutor newFixedThreadPool(int corePoolSize) {
        return new ThreadPoolExecutor(corePoolSize,
                corePoolSize,
                0,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());
    }

    public static ThreadPoolExecutor newFixedThreadPool(int corePoolSize,
                                                        BlockingQueue<Runnable> queue) {
        return new ThreadPoolExecutor(corePoolSize,
                corePoolSize,
                0,
                TimeUnit.MILLISECONDS,
                queue);
    }

    public static ThreadPoolExecutor newFixedThreadPool(int corePoolSize,
                                                        BlockingQueue<Runnable> queue,
                                                        ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(corePoolSize,
                corePoolSize,
                0,
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory);
    }

    public static ThreadPoolExecutor newFixedThreadPool(int corePoolSize,
                                                        BlockingQueue<Runnable> queue,
                                                        ThreadFactory threadFactory,
                                                        RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(corePoolSize,
                corePoolSize,
                0,
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory,
                handler);
    }

    public static ThreadPoolExecutor newCachedThreadPool(int corePoolSize,
                                                         int maximumPoolSize) {
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                60000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());
    }

    public static ThreadPoolExecutor newCachedThreadPool(int corePoolSize,
                                                         int maximumPoolSize,
                                                         BlockingQueue<Runnable> queue) {
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                60000,
                TimeUnit.MILLISECONDS,
                queue);
    }

    public static ThreadPoolExecutor newCachedThreadPool(int corePoolSize,
                                                         int maximumPoolSize,
                                                         BlockingQueue<Runnable> queue,
                                                         ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                60000,
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory);
    }

    public static ThreadPoolExecutor newCachedThreadPool(int corePoolSize,
                                                         int maximumPoolSize,
                                                         BlockingQueue<Runnable> queue,
                                                         ThreadFactory threadFactory,
                                                         RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                60000,
                TimeUnit.MILLISECONDS,
                queue,
                threadFactory,
                handler);
    }

    public static BlockingQueue<Runnable> buildQueue(int size) {
        return buildQueue(size, false);
    }

    public static BlockingQueue<Runnable> buildQueue(int size, boolean isPriority) {
        BlockingQueue<Runnable> queue;
        if (size == 0) { // 默认无队列
            queue = new SynchronousQueue<Runnable>();
        } else { // 有限队列或无限队列
            if (isPriority) {
                queue = size < 0 ? new PriorityBlockingQueue<Runnable>()
                        : new PriorityBlockingQueue<Runnable>(size);
            } else {
                queue = size < 0 ? new LinkedBlockingQueue<Runnable>()
                        : new LinkedBlockingQueue<Runnable>(size);
            }
        }
        return queue;
    }
}
