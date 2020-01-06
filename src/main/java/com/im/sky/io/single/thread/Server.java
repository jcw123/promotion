package com.im.sky.io.single.thread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(this.port);
                while(true) {
                    Socket socket = serverSocket.accept();
                    process(socket);
                }
            } catch (IOException e) {

            }
    }

    public static void process(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        byte[]  arr = new byte[1024];
        int len;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        while((len = in.read(arr)) != -1) {
              bao.write(arr, 0, len);
        }
        System.out.println("服务端从客户端接收到的数据：" + bao.toString("UTF-8"));
        OutputStream out = socket.getOutputStream();
        out.write("你好呀，客户端".getBytes("UTF-8"));
        socket.shutdownOutput();
    }


    public static void main(String[] args) {
        new Thread(new Server(8888), "Server").start();
    }
}
