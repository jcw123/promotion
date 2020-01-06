package com.im.sky.io.nio.self;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jiangchangwei
 * @date 2019-12-4 下午 10:58
 **/
public class ChannelTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("d:\\tmp\\a.txt", "r");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            while(buffer.hasRemaining()) {
                System.out.print((char)buffer.get());
            }
            buffer.clear();
        }
        file.close();

        FileInputStream fileInputStream = new FileInputStream(new File("d:\\tmp\\a.txt"));
        byte[] b = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while((len = fileInputStream.read(b)) != -1) {
            sb.append(new String(b, 0, len));
        }
        System.out.println(sb.toString());
    }
}
