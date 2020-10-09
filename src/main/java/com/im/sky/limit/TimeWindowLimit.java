package com.im.sky.limit;

import redis.clients.jedis.Jedis;

/**
 * @author jiangchangwei
 * @date 2020-5-20 下午 4:23
 *
 * 时间窗口限流
 **/
public class TimeWindowLimit {

    private static Jedis jedis;

    private static final int maxCount = 100;

    /**
     * 1s 10次
     */
    private static final int count = 10;
    private static final int timeSeconds = 1;

    static {
        jedis = new Jedis("192.168.99.100", 6380);
    }

    public static void main(String[] args) {
        int count = 10000;
        int times = 0;
        for(int i = 0; i < count; i++) {
            if(isLimit("test")) {
//                System.out.println("被限制住了");
            }else {
                times++;
                System.out.println("正常访问");
            }
        }
        System.out.println(times);
    }

    private static boolean isLimit(String key) {
        long time = System.nanoTime();
        jedis.zremrangeByScore(key, 0, time - (long)timeSeconds * 1000 * 1000 * 1000);
        long total = jedis.zcard(key);
        if(total > count) {
            return true;
        }else {
            jedis.zadd(key, time, "" + time);
            return false;
        }
    }
}
