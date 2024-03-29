package com.im.sky.test;

import com.alibaba.fastjson.JSON;
import com.im.sky.reflect.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
public class Test {

    private int flag = 1;

    private int flag2 = 2;

    private final Object MUTEX2 = new Object();

    private volatile int m = 1;

    private static final Object MUTEX = new Object();

    public static void main(String[] args) throws Exception {
        String s1 = "33" + "44";
        String s2 = "334" + "4";
        Map<byte[], byte[]> map = new HashMap<>();
        System.out.println("3344".getBytes(StandardCharsets.UTF_8).hashCode());
        System.out.println(Arrays.hashCode("3344".getBytes(StandardCharsets.UTF_8)));
        System.out.println("3344".getBytes(StandardCharsets.UTF_8).equals("3344".getBytes(StandardCharsets.UTF_8)));
        map.put("3344".getBytes(StandardCharsets.UTF_8), new byte[0]);
        System.out.println(map.containsKey(s1.getBytes(StandardCharsets.UTF_8)));
        System.out.println(map.containsKey(s2.getBytes(StandardCharsets.UTF_8)));
    }

    private static class He {
        private int age = -999;

        private String name = "jcw";
    }

    public void testScene2() {
        flag = 2;
    }

    public void testScene1() throws Exception {
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    if (m == 1) {
                        if(flag != 1) {
                            break;
                        }
                    }
                }
                System.out.println("flag:" + flag);
            }).start();
        }
        Thread.sleep(1000);
//        synchronized (MUTEX) {
////            flag = 2;
////        }
        flag = 2;
    }

    static String readContent(String path) {
        try {
            File file = new File(path);
            Long length = file.length();
            byte[] data = new byte[length.intValue()];
            FileInputStream in = new FileInputStream(file);
            in.read(data);
            in.close();
            return new String(data, "utf-8");
        } catch (Exception e) {
        }
        return null;
    }

}
