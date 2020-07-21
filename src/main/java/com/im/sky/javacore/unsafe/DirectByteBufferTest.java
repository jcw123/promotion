package com.im.sky.javacore.unsafe;

import java.nio.ByteBuffer;


/**
 * @author jiangchangwei
 * @date 2020-5-28 上午 11:20s
 **/
public class DirectByteBufferTest {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        byteBuffer.put((byte)1);
        byteBuffer.flip();
        System.out.println(byteBuffer.get());
    }
}
