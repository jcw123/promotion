package com.im.sky;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-13 11:04
 **/
public class ThreadTest {

    public static final Object P = new Object();

    static List<Integer> list = new ArrayList<>();

    @Test
    public void test2() throws Exception {
        Executor executor = Executors.newFixedThreadPool(12);
        for(int i = 0; i < 12; i++) {
            Runnable runnable = () -> {
                while(true) {

                }
            };
            executor.execute(runnable);
        }
        Thread.sleep(500000);
    }

    @Test
    public void test() throws Exception {

        Thread thread1 = new Thread(()-> {
            while(true) {
                try {
                    product();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            while(true) {
                try {
                    consume();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private static void product() throws Exception {
        synchronized (P) {
            if(list.size() == 1) {
                // 让出锁
                P.wait();
            }
            list.add(1);
            System.out.println("produce");
            P.notify();
        }
    }

    private static void consume() throws Exception {
        synchronized (P) {
            if(list.size() == 0) {
                P.wait();
            }
            list.remove(list.size() - 1);
            System.out.println("consume");
            P.notify();
        }
    }
}
