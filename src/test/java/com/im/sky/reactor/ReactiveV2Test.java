package com.im.sky.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * @author jiangchangwei
 * @since 2024/11/25
 */
public class ReactiveV2Test {

    @Test
    public void test() throws Exception {
        Long orderId = 100L;
        Mono<String> mono = readDataFromCache(orderId)
                .log()
//                .subscribeOn(Schedulers.newParallel("异步读取数据线程"))
                .switchIfEmpty(Mono.fromCallable(() -> readDataFromDB(orderId))
                        .log()
                                .doOnNext(t -> {
                                    System.out.println("thread name:" + Thread.currentThread().getName());
                                    System.out.println("do next test");
                                    backToCache(t).subscribe();
                                })
                        .subscribeOn(Schedulers.newParallel("回源缓存线程"))
                        );
        mono.block(Duration.ofSeconds(10));
    }

    @Test
    public void test2() throws Exception {
        Flux<Integer> flux = Flux.just(1, 2);
        flux = flux.map(t -> {
            if(t < 2) {
                return t + 1;
            }else {
                throw new RuntimeException();
            }
        });
        flux.subscribe(System.out::println);
        flux.subscribe(System.out::println);
    }

    /**
     * 打印数据流
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        Flux<Integer> flux = Flux.just(1, 2);
        flux.subscribe(System.out::println);
    }

    /**
     * 每隔3秒打印一下当前时间
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Flux.interval(Duration.ofSeconds(3))
                .subscribe(t -> {
                    System.out.println("current time: " + LocalDateTime.now().format(formatter));
                });
    }

    /**
     * 在一个异步线程中打印线程名称
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        Flux<Integer> flux = Flux.just(1, 2)
                .log()
                // 保证所有的操作都是在这个单独线程池中执行，不阻塞主线程
                .subscribeOn(Schedulers.newParallel("测试线程", 2));
        flux.subscribe(t -> {
            try {
                Thread.sleep(2 * 1000);
                System.out.println("thread name:" + Thread.currentThread().getName() + ", data:" + t);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 等一会，方便测试数据打印
        Thread.sleep(10 * 1000);
    }

    // 从数据库读取数据
    private String readDataFromDB(Long orderId) {
        System.out.println("enter readDataFromDB");
        return "test1";
    }

    // 从缓存中读取数据
    private Mono<String> readDataFromCache(Long orderId) {
        System.out.println("enter readDataFromCache");
        return Mono.empty();
    }

    // 将数据库中的数据回源到缓存
    private Mono<Void> backToCache(String dataFromDB) {
        return Mono.fromRunnable(() -> {
            System.out.println("将数据库中的数据回源到缓存");
        });
    }

    @Test
    public void testCombineLatest() throws Exception {
        Flux<String> flux1 = Flux.interval(Duration.ofSeconds(1)).map(t -> "a" + t).take(3);
        Flux<String> flux2 = Flux.interval(Duration.ofSeconds(2)).map(t -> "b" + t).take(3);
        Flux<String> flux = Flux.combineLatest(flux1, flux2, (o1, o2) -> o1 + o2);
        flux.subscribe(System.out::println);
        Thread.sleep(1000*10);
    }

    @Test
    public void test6() throws Exception {
        Mono<String> mono1 = Mono.fromCallable(() -> {
            try {
                System.out.println("thread1 name:" + Thread.currentThread().getName());
                Thread.sleep(1000);
            }catch (Exception ignore) {

            }
            return "test1";
        })
                .log();
        Mono<String> mono2 = Mono.fromCallable(() -> {
            try {
                System.out.println("thread2 name:" + Thread.currentThread().getName());
                Thread.sleep(2000);
            }catch (Exception ignore) {

            }
            return "test2";
        })
                .log()
                .subscribeOn(Schedulers.parallel());
        Mono<String> mono3 = Mono.fromCallable(() -> {
            try {
                System.out.println("thread3 name:" + Thread.currentThread().getName());
                Thread.sleep(3000);
            }catch (Exception ignore) {

            }
            return "test3";
        })
                .log()
                .subscribeOn(Schedulers.parallel());
        Flux.merge(mono1, mono2, mono3)
                .subscribeOn(Schedulers.newParallel("mynew"))
                .subscribe(t -> {
                    System.out.println("thread name:" + Thread.currentThread().getName() + ", data:" + t);
                });
        Thread.sleep(10 * 1000);
    }

    @Test
    public void test7() throws Exception {
        // 启用 Reactor 的 debug 模式
    }
}
