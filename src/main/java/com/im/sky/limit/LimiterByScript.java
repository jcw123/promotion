package com.im.sky.limit;

import redis.clients.jedis.Jedis;

import java.io.*;

/**
 * @author jiangchangwei
 * @date 2020-7-23 上午 11:09
 **/
public class LimiterByScript {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = LimitByScript.class.getResourceAsStream("/lua/count_limit.lua");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] arr = new byte[1024];
        int len;
        while((len = inputStream.read(arr)) != -1) {
            baos.write(arr, 0, len);
        }
        String data = baos.toString("utf-8");
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String sha = jedis.scriptLoad(data);
        System.out.println("sha:" + sha);
        Object o = jedis.evalsha(sha, 1, "test_lua_limit", "10", "20");
        System.out.println(o);
    }
}
