package com.im.sky.limit.single;

import redis.clients.jedis.Jedis;

/**
 * @author jiangchangwei
 * @date 2020-5-25 下午 7:51
 *
 * 通过redis实现固定窗口限流
 **/
public class RedisLimit {

    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static boolean isLimit(String key) {
        // 初始化只有一个请求设置成功
        if(jedis.setnx(key, "1") == 1) {
            // 设置超时时间为10s, 设置失败了影响很大
            jedis.expire(key, 10);
            return false;
        }else {
            long val = jedis.incr(key);
            return val > 10;
        }
    }
}
