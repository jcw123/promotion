package com.im.sky.javacore.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author jiangchangwei
 * @date 2021-2-1 下午 6:07
 **/
public class ThreadLocalTest {

    private static final ExecutorService EXECUTOR = TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(1, 1, 1,TimeUnit.SECONDS
            , new LinkedBlockingQueue<>()));

    private static final InheritableThreadLocal<Integer> t1 = new InheritableThreadLocal<>();

    private static final TransmittableThreadLocal<Integer> t2 = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        t2.set(1);
        for(int i = 0; i < 20; i++) {
            EXECUTOR.submit(() -> {
                System.out.println(t2.get());
            });
        }
    }
}

