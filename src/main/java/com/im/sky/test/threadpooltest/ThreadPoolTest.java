package com.im.sky.test.threadpooltest;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-05 12:02
 **/
public class ThreadPoolTest {

    public static void main(String[] args) throws Exception {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(1);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.afterPropertiesSet();
        int max = 10;
        List<Future<Boolean>> futures = new ArrayList<>();
        for(int i = 0; i < max; i++) {
            Future<Boolean> future = executor.submit(() -> {
                try {
                    Thread.sleep(10 * 1000);
                    return true;
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            });
            futures.add(future);
        }
        System.out.println(futures.size());
        System.out.println("test");
        for(int i = 0; i < max; i++) {
            System.out.println(futures.get(i).get());
            System.out.println(i);
        }
        System.out.println("end");
    }
}
