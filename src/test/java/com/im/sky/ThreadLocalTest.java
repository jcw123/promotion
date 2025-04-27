package com.im.sky;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-05-16 20:46
 **/
public class ThreadLocalTest {

    @Test
    public void testTransmitThreadLocal() throws Exception {
        TransmittableThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<Integer>();
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> System.out.println("hello"));
        Thread.sleep(1000);
        threadLocal.set(1);
        executor.execute(() -> {
            System.out.println(threadLocal.get());
        });
        threadLocal.set(2);
        executor.execute(() -> {
            System.out.println(threadLocal.get());
        });
        Thread.sleep(20 * 1000);
    }

    @Test
    public void testTransmitThreadLocal2() throws Exception {
        TransmittableThreadLocal<Integer> threadLocal = new TransmittableThreadLocal<Integer>();
        Executor executor = Executors.newFixedThreadPool(1);
        threadLocal.set(null);
        executor.execute(() -> {
            System.out.println(threadLocal.get());
        });
        threadLocal.set(2);
        executor.execute(TtlRunnable.get(() -> {
            System.out.println(threadLocal.get());
        }));
        Thread.sleep(20 * 1000);
    }

    @Test
    public void testTransmitThreadLocal3() throws Exception {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
        Executor executor = Executors.newFixedThreadPool(1);
//        executor.execute(() -> System.out.println("hello"));
//        Thread.sleep(1000);
        threadLocal.set(1);
        executor.execute(() -> {
            System.out.println(threadLocal.get());
        });
        threadLocal.set(2);
        executor.execute(() -> {
            System.out.println(threadLocal.get());
        });
        Thread.sleep(20 * 1000);
    }
}
