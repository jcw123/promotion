package com.im.sky.mysql.mvcc;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jiangchangwei
 * @date 2020-9-16 下午 8:21
 **/
public class TransactionIdGenerator {

    private static final AtomicLong idGenerator = new AtomicLong();

    public static long generate() {
        return idGenerator.incrementAndGet();
    }
}
