package com.im.sky.test;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReactorTest {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) {
        Mono<String> mono = Mono.fromCallable(()-> {
            try {
                System.out.println("name:" + Thread.currentThread().getName());
                Thread.sleep(5000);
            }catch (Exception e){

            }
            return "hello";
        });
        long start = System.currentTimeMillis();
        Disposable subscription = mono.subscribeOn(Schedulers.parallel())
                .subscribe();
        subscription.dispose();
        mono.block();
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    interface ITest {
        Mono<String> test();
    }

    static class TestImpl implements ITest {
        @Override
        public Mono<String> test() {
            return Mono.fromSupplier(() -> {
                try {
                    System.out.println("thead2 name:" + Thread.currentThread().getName());
                    Thread.sleep(3000);
                    return "Result 2";
                }catch (Exception e) {

                }
                return null;
            })
                    .subscribeOn(Schedulers.fromExecutor(executor));
        }
    }
}
