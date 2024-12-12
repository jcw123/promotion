package com.im.sky.test.reactor;

import reactor.core.publisher.Mono;

/**
 * 缓存操作
 *
 * @author jiangchangwei
 * @since 2024/11/25
 */
public interface CacheService {

    /**
     * 查询数据
     * @param key
     * @return
     */
    Mono<String> getData(String key);

    /**
     * 写入数据
     * @param key
     * @param value
     * @return
     */
    Mono<String> putData(String key, String value);
}
