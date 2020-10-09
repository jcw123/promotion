package com.im.sky.redis.zset;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-5-20 上午 11:16
 **/
public class Executor {

    private static Jedis jedis;

    static {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        jedis.hmset("testHash", map);
    }

}
