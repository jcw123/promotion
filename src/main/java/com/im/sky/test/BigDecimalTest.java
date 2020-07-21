package com.im.sky.test;

import java.math.BigDecimal;

/**
 * @author jiangchangwei
 * @date 2020-6-16 下午 6:53
 **/
public class BigDecimalTest {

    public static void main(String[] args) {
        System.out.println(-1 % 3);
        System.out.println(Math.floorMod(-1, 3));
        BigDecimal bigDecimal = new BigDecimal("3.333");
        BigDecimal val = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING);
        System.out.println(val.toPlainString());
    }
}
