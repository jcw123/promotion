package com.im.sky.lock.juc.aqs.test.cp;

import com.im.sky.lock.juc.aqs.SimpleCyclicBarrier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangchangwei
 * @date 2020-5-27 下午 7:44
 **/
public class CyclicBarrierTest {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        Runnable runnable = () -> {
            System.out.println("任务执行完了");
            System.out.println(map.size());
        };
        SimpleCyclicBarrier cyclicBarrier = new SimpleCyclicBarrier(2, runnable);
        new Thread(() -> {
            System.out.println("上半部分执行完了");
            map.put("t1", "t1");
            try {
                cyclicBarrier.await();
            }catch (Exception e) {}
        }).start();
        new Thread(() -> {
            System.out.println("下半部分执行完了");
            map.put("t2", "t2");
            try {
                cyclicBarrier.await();
            }catch (Exception e) {}
        }).start();
    }
}
