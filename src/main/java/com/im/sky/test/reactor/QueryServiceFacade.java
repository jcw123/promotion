package com.im.sky.test.reactor;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * 对外的查询接口
 *
 * @author jiangchangwei
 * @since 2024/11/25
 */
public class QueryServiceFacade {

    private CacheService cacheService = new CacheServiceImpl();

    private DBService dbService = new DBServiceImpl();

    public static void main(String[] args) {
        String data = new QueryServiceFacade().query("test");
        System.out.println(data);
    }

    public String query(String key) {
        return cacheService.getData(key)
                // 打印日志，用于调试
                .log()
                .switchIfEmpty(dbService.getData(key).flatMap(t -> cacheService.putData(key, t).then(Mono.just(t))))
                // 将上述的操作放到一个异步线程池中执行
                .subscribeOn(Schedulers.newParallel("异步线程池", 2))
                .block(Duration.ofSeconds(3));
    }
}
