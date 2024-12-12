package com.im.sky.test.reactor;

import reactor.core.publisher.Mono;

/**
 * 缓存操作实现
 *
 * @author jiangchangwei
 * @since 2024/11/25
 */
public class CacheServiceImpl implements CacheService {
    @Override
    public Mono<String> getData(String key) {
        System.out.println("缓存中未查询到数据");
        // 模拟缓存数据未查询到
        return Mono.empty();
    }

    @Override
    public Mono<String> putData(String key, String value) {
        System.out.println("将查询到的数据写入缓存");
        return Mono.just("完成了缓存数据写入");
    }
}
