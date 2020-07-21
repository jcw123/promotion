package com.im.sky.netty.test.test2.server;

import com.im.sky.netty.test.test2.transport.ServerTransportConfig;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.NamedThreadFactory;
import com.im.sky.netty.test.test2.util.ThreadPoolUtils;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:37
 **/
@Slf4j
public class BusinessPool {

    private final static ConcurrentMap<Integer, ThreadPoolExecutor> poolMap = new ConcurrentHashMap<Integer, ThreadPoolExecutor>();

    private static volatile EventExecutorGroup serializeEventGroup;

    public static EventExecutorGroup getSerializeEventGroup(){
        if(serializeEventGroup == null) {
            serializeEventGroup = initEventExecutors();
        }
        return serializeEventGroup;
    }

    private static synchronized EventExecutorGroup initEventExecutors() {
        if(serializeEventGroup != null) return serializeEventGroup;
        NamedThreadFactory threadName = new NamedThreadFactory("JOY-SERIALIZE-W ", true);
        return new DefaultEventExecutorGroup(30, threadName);
    }

    public static ThreadPoolExecutor getBusinessPool(ServerTransportConfig transportConfig) {
        int port = transportConfig.getPort();
        ThreadPoolExecutor pool = poolMap.get(port);
        if (pool == null) {
            ThreadPoolExecutor newPool = initPool(transportConfig);
            pool = poolMap.putIfAbsent(port, newPool);
            if(pool == null) {
                pool = newPool;
            }

        }
        return pool;
    }

    public static ConcurrentMap<Integer, ThreadPoolExecutor> getBusinessPools() {
        return poolMap;
    }

    public static ThreadPoolExecutor getBusinessPool(int port) {
        return poolMap.get(port);
    }

    private static synchronized ThreadPoolExecutor initPool(ServerTransportConfig transportConfig) {
        int port = transportConfig.getPort();
        int minPoolSize;
        int aliveTime;
        int maxPoolSize = transportConfig.getServerBusinessPoolSize();
        String poolType = transportConfig.getServerBusinessPoolType();
        if(Constants.THREAD_POOL_TYPE_FIXED.equals(poolType)) {
            minPoolSize = maxPoolSize;
            aliveTime = 0;
        }else if(Constants.THREAD_POOL_TYPE_CACHED.equals(poolType)) {
            minPoolSize = 10;
            maxPoolSize = Math.max(minPoolSize, maxPoolSize);
            aliveTime = 60000;
        }else {
            throw new IllegalArgumentException("illegal thread pool type:" + poolType);
        }
        String queueType = transportConfig.getPoolQueueType();
        int queueSize = transportConfig.getPoolQueueSize();
        boolean isPriority = Constants.QUEUE_TYPE_PRIORITY.equals(queueType);
        BlockingQueue<Runnable> configQueue = ThreadPoolUtils.buildQueue(queueSize, isPriority);
        NamedThreadFactory threadFactory = new NamedThreadFactory("JOY-BZ-" + port, true);
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            private int i = 0;
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(i++ % 7 == 0) {
                    i = 1;
                    log.warn("thread pool exhausted");
                }
                throw new RejectedExecutionException("Biz thread pool exhausted");
            }
        };
        return new ThreadPoolExecutor(minPoolSize, maxPoolSize, aliveTime, TimeUnit.MILLISECONDS, configQueue, threadFactory, handler);
    }
}
