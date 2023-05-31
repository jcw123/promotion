package com.im.sky;

import org.junit.Test;

/**
 * @author jiangchangwei
 * @program promotion
 * @description 数据范围计算
 * @date 2023-04-08 10:56
 **/
public class DataRangeTest {

    @Test
    public void testInt() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(0X3f3f3f3f);
        System.out.println(0X3f3f3f3f + 0X3f3f3f3f);
        int v = 0X3f3f3f3f + 0X3f3f3f3f;
        System.out.println(Integer.toHexString(v));
    }
}
