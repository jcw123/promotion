package com.im.sky.netty.test.test2.config;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 4:07
 **/
public abstract class AbstractIdConfig {

    private final static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private int id;

    public String getId() {
        return "joy-" + ID_GENERATOR.incrementAndGet();
    }

    public void setId(int id) {
        this.id = id;
    }
}
