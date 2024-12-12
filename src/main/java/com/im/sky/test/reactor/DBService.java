package com.im.sky.test.reactor;

import reactor.core.publisher.Mono;

/**
 * 数据库操作
 *
 * @author jiangchangwei
 * @since 2024/11/25
 */
public interface DBService {

    Mono<String> getData(String key);
}
