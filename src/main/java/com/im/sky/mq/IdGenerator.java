package com.im.sky.mq;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-10-10 下午 6:39
 **/
public class IdGenerator {

    private final AtomicInteger counter = new AtomicInteger();

    public Integer generate() {
        return counter.incrementAndGet();
    }
}
