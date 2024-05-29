package com.im.sky.test.threadpooltest;

import java.util.concurrent.CompletableFuture;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-27 14:57
 **/
public class CompletableFutureTest {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(5000);
            System.out.println("finish");
            return "11";
        });
        long start = System.currentTimeMillis();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        });
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(future, future1);
        allFuture.whenComplete((u, v) -> {
            try {
                System.out.println("end");
                System.out.println(future.get());
                System.out.println(future1.get());
            }catch (Throwable e) {

            }

        });
        allFuture.get();
    }

    private static void sleep(int millSeconds) {
        try {
            Thread.sleep(millSeconds);
        }catch (Exception ignored) {

        }
    }
}
