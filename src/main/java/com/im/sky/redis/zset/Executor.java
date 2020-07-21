package com.im.sky.redis.zset;

import redis.clients.jedis.Jedis;

/**
 * @author jiangchangwei
 * @date 2020-5-20 上午 11:16
 **/
public class Executor {

    private static Jedis jedis;

    static {
        jedis = new Jedis("192.168.99.100", 6380);
    }

    public static void main(String[] args) {
        jedis.zadd("test", 1, "v1");
        jedis.zadd("test", 1, "v2");
        System.out.println(jedis.zpopmin("test"));
    }

}
