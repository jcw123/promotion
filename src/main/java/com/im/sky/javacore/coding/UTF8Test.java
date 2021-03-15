package com.im.sky.javacore.coding;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangchangwei
 * @date 2020-11-3 下午 4:56
 **/
public class UTF8Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String testCode = "ab\uD83D\uDE03cd";
        int length = testCode.length();
        int count = testCode.codePointCount(0, testCode.length());

    }

    private static boolean isOver3Byte(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        for(byte item : bytes) {
            int v = Byte.toUnsignedInt(item);
            if(v >= 0xf0) {
                return true;
            }
        }
        return false;
    }
}
