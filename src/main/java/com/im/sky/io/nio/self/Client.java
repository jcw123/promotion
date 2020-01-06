package com.im.sky.io.nio.self;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author jiangchangwei
 * @date 2019-12-1 下午 12:00
 **/
public class Client extends Thread {

    private String ip;

    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            SocketChannel sc = SocketChannel.open(new InetSocketAddress(ip, port));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("I miss you".getBytes("UTF-8"));
            buffer.flip();
            sc.write(buffer);
            buffer.clear();
            sc.shutdownOutput();
            // 接收服务端数据
            int len;
            while((len = sc.read(buffer)) != -1) {
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, len));
                buffer.clear();
            }
            sc.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 8889).start();
    }
}
