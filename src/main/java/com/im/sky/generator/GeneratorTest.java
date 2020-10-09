package com.im.sky.generator;

import java.util.UUID;

/**
 * @author jiangchangwei
 * @date 2020-8-12 下午 4:50
 **/
public class GeneratorTest {

    public static void main(String[] args) {
        testUUIDGenerator();
        testUUID();
    }

    private static void testUUIDGenerator() {
        for(int i = 0; i < 10; i++) {
            System.out.println(new UUIDGenerator().idStr());
        }
    }

    private static void testUUID() {
        byte[] bytes = new byte[16];
        for(int i = 0; i < 10; i++) {
            System.out.println(UUID.nameUUIDFromBytes(bytes).version());
        }
    }
}
