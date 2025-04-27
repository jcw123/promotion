package com.im.sky;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-17 16:17
 **/
public class CompletableFutureTest {

    @Test
    public void test1() {
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("我睡眠了3秒");
            }catch (Exception e) {

            }
        })
                .thenRun(()-> {
                    sleep(7);
                    System.out.println("c1");
                });

        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10 * 1000);
                System.out.println("我睡眠了10秒");
            }catch (Exception e) {

            }
        });
        CompletableFuture<?> anyC = CompletableFuture.anyOf(c1, c2)
                .thenRun(() -> {
                    sleep(7);
                    System.out.println("anyC");
                });
        System.out.println("any finished");
        CompletableFuture<?> allC = CompletableFuture.allOf(c1, c2)
                .thenRunAsync(() -> {
                    sleep(1);
                    System.out.println("allC");
                });
        System.out.println("all finished");
        c1.join();
//        c2.join();
//        anyC.join();
//        allC.join();
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }catch (Exception e) {

        }
    }

    @Test
    public void test2() throws Exception {
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            sleep(3);
            return "1";
        });
        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            throw new RuntimeException();
        });
        CompletableFuture<String>[] futures = new CompletableFuture[2];
        futures[0] = c1;
        futures[1] = c2;
        System.out.println(c1.isCompletedExceptionally());
        System.out.println(c2.isCompletedExceptionally());
        CompletableFuture<List<String>> allC = CompletableFuture.allOf()
                .thenApply(it -> {
                    List<String> list = new ArrayList<>();
                    for (CompletableFuture<String> future : futures) {
                        try {
                            System.out.println("test1:" + future.get());
                            list.add(future.get());
                        }catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                    return list;
                });
        allC.get(4, TimeUnit.SECONDS);
        Thread.sleep(5 * 1000);
    }

    @Test
    public void test3() throws Exception {
        for(int i = 0; i < 100; i++) {
            CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
                try {
//                    Thread.sleep(1000);
                    return "jcw";
                }catch(Exception e) {

                }
                return null;
            })
                    .thenApply(t -> {
                        try {
                            Thread.sleep(10000);
                            return t + "123";
                        }catch (Exception e) {

                        }
                        return null;
                    });
            System.out.println("ok");
        }
    }

    @Test
    public void test4() throws Exception {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("t1 id:" + Thread.currentThread().getId());
                Thread.sleep(10 * 1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).handleAsync((t1, t2) -> {
            try {
                Thread.sleep(5 * 1000);
            }catch (Exception e) {

            }
            System.out.println("t2 id:" + Thread.currentThread().getId());
            System.out.println("你好");
            return t1;
        }, Executors.newSingleThreadExecutor()).whenComplete((t1, t2) -> {
            System.out.println("t3 id:" + Thread.currentThread().getId());
        });
        System.out.println("nihaoya");
        Thread.sleep(20 * 1000);
    }

    @Test
    public void test5() throws Exception {
//        CompletableFuture.runAsync(() -> {
//            try {
//                System.out.println("t1 id:" + Thread.currentThread().getId());
//                Thread.sleep(10 * 1000);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        CompletableFuture.supplyAsync(() -> 1)
                        .thenCompose(t -> {
                            return CompletableFuture.supplyAsync(() -> 2);
                        }).complete(3);
        System.out.println("nihaoya");
        Thread.sleep(3 * 1000);
    }
}
