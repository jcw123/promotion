package com.im.sky.javacore;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-3-31 上午 10:53
 *
 * 线程生成唯一id
 **/
public class ThreadId {

    private static final AtomicInteger nextId = new AtomicInteger(0);

    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(nextId::getAndIncrement);

    public static int get() {
        return threadId.get();
    }
}
