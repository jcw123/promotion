package com.im.sky.test.inittest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-23 13:26
 **/
@Slf4j
public class Slf4jTest {

    static {

    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        Logger logger = LoggerFactory.getLogger("test");
        System.out.println("time:" + (System.currentTimeMillis() - start));
    }
}
