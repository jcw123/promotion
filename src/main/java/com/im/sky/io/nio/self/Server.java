package com.im.sky.io.nio.self;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author jiangchangwei
 * @date 2019-12-1 下午 12:23
 **/
public class Server extends Thread {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(this.port));
            SocketChannel sc = ssc.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(sc.read(buffer) != -1) {
                buffer.flip();
                System.out.println(new String(buffer.array()));
                buffer.clear();
            }
            buffer.put("我是服务端数据，请注意接收".getBytes("UTF-8"));
            buffer.flip();
            sc.write(buffer);
            sc.close();
            ssc.close();        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Server(8889).start();
    }
}
