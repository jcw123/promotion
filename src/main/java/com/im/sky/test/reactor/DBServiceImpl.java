package com.im.sky.test.reactor;

import reactor.core.publisher.Mono;

/**
 * 数据库操作实现
 *
 * @author jiangchangwei
 * @since 2024/11/25
 */
public class DBServiceImpl implements DBService {
    @Override
    public Mono<String> getData(String key) {
        System.out.println("从数据库中查询了数据");
        return Mono.just("从数据库中查询到数据");
    }
}
