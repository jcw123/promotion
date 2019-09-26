package com.im.sky.netty;

import java.util.Date;

/**
 * @Author: jiangcw
 * @Date: 2019-9-25 上午 7:57
 * @Version 1.0
 */
public class UnixTime {

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}

