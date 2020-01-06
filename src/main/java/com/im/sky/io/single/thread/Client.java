package com.im.sky.io.single.thread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable {

    private String ip;

    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip, port);
            process(socket);
        }catch (IOException e) {

        }
    }

    private void process(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("你也好呀，服务器，我是客户端".getBytes("UTF-8"));
        socket.shutdownOutput();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        while((len = in.read(arr)) != -1) {
            bao.write(arr, 0, len);
        }
        System.out.println("从服务端接收到的数据:" + bao.toString("UTF-8"));
    }

    public static void main(String[] args) {
        new Thread(new Client("127.0.0.1", 8881), "client1").start();
        new Thread(new Client("127.0.0.1", 8881), "client2").start();
    }
}
