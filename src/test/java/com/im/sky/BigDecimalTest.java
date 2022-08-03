package com.im.sky;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @ http://stackoverflow.com/questions/35435691/bigdecimal-precision-and-scale
 */
public class BigDecimalTest {

    @Test
    public void test() {
        BigDecimal b1 = BigDecimal.valueOf(100, 2);
        System.out.println(b1.precision());
        System.out.println(b1.scale());
    }

    @Test
    public void test2() {
        Logger logger = LogManager.getLogger("test");
        String username = "\\${java:os}";
        logger.info("test:" + username);
    }
}
