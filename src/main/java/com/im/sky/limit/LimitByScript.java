package com.im.sky.limit;

import redis.clients.jedis.Jedis;

/**
 * @author jiangchangwei
 * @date 2020-5-25 下午 4:36
 **/
public class LimitByScript {

    public static void main(String[] args) {
        for(int i = 0; i < 3; i++) {
            System.out.println(isLimit("jiangcw"));
        }
    }

    private static final String sha;

    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    static {
        String script = "local key = \"redis.limit:\" .. KEYS[1] -- 限流key\n" +
                "local limit = tonumber(ARGV[1]) -- 限流大小\n" +
                "local current = tonumber(redis.call('get', key) or \"0\")\n" +
                "if current + 1 > limit then\n" +
                "  return 0\n" +
                "else\n" +
                "  redis.call('INCRBY', key, 1)\n" +
                "  redis.call('expire', key, 2)\n" +
                "  return current + 1;" +
                "end";
        sha = jedis.scriptLoad(script);
        System.out.println(sha);
        String s2;
        s2 = jedis.scriptLoad(script);
        System.out.println(s2);
    }

    public static boolean isLimit(String key) {
        Object isLimit = jedis.evalsha(sha, 1, key, "1");
        return (Long)isLimit == 0;
    }
}
