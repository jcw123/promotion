package com.im.sky.test;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.annotation.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:52
 * @Version 1.0
 */
public class Test {

    private int flag = 1;

    private int flag2 = 2;

    private final Object MUTEX = new Object();

    private final Object MUTEX2 = new Object();

    private volatile int m = 1;

    public static void main(String[] args) throws Exception {
        System.out.println(1L << 32);
    }

    public void testScene2() {
        flag = 2;
        m  = 2;
        flag2 = 3;
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

}
