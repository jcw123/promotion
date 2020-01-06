package com.im.sky.io.nio.self;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jiangchangwei
 * @date 2019-12-1 上午 11:17
 **/
public class FileReadTest {

    public static void main(String[] args) {
        testNio();
    }

    public static void testNio() {
        try {
            RandomAccessFile file = new RandomAccessFile("D:\\tmp\\a.txt", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer  = ByteBuffer.allocate(10);
            while((channel.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print(buffer.getChar());
                }
                buffer.clear();
            }
            file.close();
        }catch (Exception e) {}
    }
}
