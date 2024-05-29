package com.im.sky.javacore.closuretest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-16 16:59
 **/
public class Operator {

    private static Map<String, Consumer<Object>> map = new ConcurrentHashMap<>();

    /**
     * @param env
     */
    public static Consumer<Object> buildProcessFunction(Env env) {
        // 相当于自由变量，env也是自由变量
        int i = 0;
        return map.computeIfAbsent(env.getEnv(),
                k -> t -> System.out.println("我正在处理事情，所处的环境为：" + env.getEnv() + "，i:" + i));
    }
}
