package com.im.sky.reactor;

import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ReactorTest {


    @Test
    public void testBaseFlux() {
        Flux<String> flux = Flux.just("a", "b", "c");
        flux.subscribe(t -> System.out.println(t));
        flux.subscribe(System.out::println);
    }

    @Test
    public void testRangeFlux() {
        Flux<Integer> flux = Flux.range(1, 10);
    }

    @Test
    public void testAsyncFlux() {
        Mono<String> mono1 = Mono.fromCallable(()->{
            try{
                Thread.sleep(5000);
            }catch (Exception e){

            }
            return "hello";
        });
        Mono<String> mono2 = Mono.fromCallable(()->{
            try{
                Thread.sleep(5000);
            }catch (Exception e){

            }
            return "hello2";
        });
        System.out.println("start");
        long start = System.currentTimeMillis();
        Flux.merge(mono1, mono2)
                .parallel()
                .subscribe(System.out::println);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test3() {
        Mono<String> future1 = Mono.fromCallable(() -> {
            Thread.sleep(5000);
            return "Result 1";
        }).subscribeOn(Schedulers.elastic());

        Mono<String> future2 = Mono.fromCallable(() -> {
            Thread.sleep(5000);
            return "Result 2";
        }).subscribeOn(Schedulers.elastic());
        long start = System.currentTimeMillis();
        Mono<List<String>> mono = Flux.merge(future1, future2)
                .collectList()
                .timeout(Duration.ofSeconds(9));
        List<String> res = mono.block();
        System.out.println(res);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test4() throws Exception {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(()-> {
            try {
                Thread.sleep(5000);
            }catch (Exception e){

            }
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(()-> {
            try {
                Thread.sleep(5000);
            }catch (Exception e){

            }
        });
       CompletableFuture.allOf(future1, future2).get(6, TimeUnit.SECONDS);
    }

    @Test
    public void testComplexFlux() {
        Flux<String> flux = Flux.just("a", "b", "c");
        flux.subscribe(System.out::println, t-> {
            System.out.println("失败");
        }, ()->{
            System.out.println("完成");
        }, t-> {
            System.out.println("准备取消");
            t.request(1);
//            t.cancel();
        });
        flux.subscribe(System.out::println, t-> {
            System.out.println("失败");
        }, ()->{
            System.out.println("完成");
        });
    }

    @Test
    public void testBaseMono() {
        Mono<String> data = Mono.just("foo");
        data.subscribe(t -> System.out.println(t));
    }

    @Test
    public void test5() {
        Flux<String> flux = Flux.generate(()->0, (state, sink)->{
            sink.next("3 x " + state + " = " + 3*state);
            if(state == 10) sink.complete();
            return state + 1;
        });
        flux.subscribe(System.out::println);
    }

    @Test
    public void test6() {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                });
        flux.subscribe(System.out::println);
    }

    @Test
    public void test7() {
        Flux<String> flux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> System.out.println("state: " + state));
        flux.subscribe(System.out::println);
    }

    @Test
    public void test8() {
        CompletableFuture.runAsync(() ->{
            doSomething();
        });
    }

    @Test
    public void test9() {
        Mono<String> mono = Mono.fromSupplier(()-> {
            try {
                System.out.println("name:" + Thread.currentThread().getName());
                Thread.sleep(5000);
            }catch (Exception e){

            }
            return "hello";
        });
        long start = System.currentTimeMillis();
        mono.subscribeOn(Schedulers.parallel())
                        .block();
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test10() {
        Flux<String> flux = Flux.just("a", "b", "c");
        flux.subscribeOn(Schedulers.parallel())
                .subscribe(System.out::println);
    }

    @Test
    public void test11() {
        Mono<String> mono = Mono.fromCallable(()-> {
            try {
                System.out.println("name:" + Thread.currentThread().getName());
                Thread.sleep(5000);
            }catch (Exception e) {

            }
            return "sss";
        });
        Flux<String> flux = Flux.merge(mono).share();
        flux.subscribe(System.out::println);
        flux.subscribe(System.out::println);
    }

    @Test
    public void test12() throws Exception {
        ConnectableFlux<String> connectableFlux = Flux.just("A", "B", "C").publish();
        connectableFlux.subscribe(System.out::println);
        connectableFlux.subscribe(System.out::println);
        connectableFlux.connect();
        Thread.sleep(10000);
    }

    @Test
    public void test13() throws Exception {
        Mono<Integer> flux = Mono.fromCallable(() -> {
            System.out.println("name:" + Thread.currentThread().getName());
            return 1;
        });
        Mono<String> stringFlux = flux.map(t -> {
            System.out.println("thread1 name:" + Thread.currentThread().getName());
            return t + 1;
        }).map(t -> {
            System.out.println("thread2 name:" + Thread.currentThread().getName());
            return String.valueOf(t);
        })
                .publishOn(Schedulers.parallel())
                .subscribeOn(Schedulers.elastic())
                        .map(t -> {
                            System.out.println("thread3 name:" + Thread.currentThread().getName());
                            return t;
                        })
                .publishOn(Schedulers.newParallel("pp1", 2));
        stringFlux.subscribe(t -> {
            System.out.println("thread4 name:" + Thread.currentThread().getName());
            System.out.println(t);
        });
//        stringFlux.subscribe(System.out::println);
        Thread.sleep(5000);
    }

    @Test
    public void test14() throws Exception {
        Flux<Integer> flux = Flux.just(1, 2);
        flux = flux.map(t -> {
            System.out.println("thread1 name:" + Thread.currentThread().getName());
            return t + 1;
        });
        flux = flux.publishOn(Schedulers.parallel());
        Flux<String> flux1 = flux.map(t -> {
            System.out.println("thread2 name:" + Thread.currentThread().getName());
            return String.valueOf(t);
        });
        flux1 = flux1.subscribeOn(Schedulers.elastic());
        flux1.subscribe(t -> {
            System.out.println("thread3 name:" + Thread.currentThread().getName());
            System.out.println(t);
        });
        Thread.sleep(5000);
//        flux1.blockLast(Duration.ofSeconds(10));
    }

    @Test
    public void test15() throws Exception {
        Flux<Integer> flux = Flux.just(1, 2);
        flux = flux.map(t -> {
            System.out.println("thread1 name:" + Thread.currentThread().getName());
            return t + 1;
        });
        flux = flux.filter(t -> {
            System.out.println("thread2 name:" + Thread.currentThread().getName());
            return t > 0;
        });
        flux.subscribe(t -> {
            System.out.println("thread3 name:" + Thread.currentThread().getName());
            System.out.println(t);
        });
    }

    interface Callback {
        void callback(String res);
    }

    // 异步回调模拟调用
    public void asyncWork(Callback callback) {
        new Thread(()->{
            // 先做某些事情
            String s = doSomething();
            // 等事情做完之后，执行回调函数
            callback.callback(s);
        }).start();
    }

    private String doSomething() {
        try{
            System.out.println("thread name:" + Thread.currentThread().getName());
            Thread.sleep(5000);
        }catch (Exception ignored){}
        return "test";
    }
}
