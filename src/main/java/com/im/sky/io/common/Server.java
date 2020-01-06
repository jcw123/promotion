package com.im.sky.io.common;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static void run() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8882);
        Socket client = serverSocket.accept();
        InputStream inputStream = client.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStream outputStream = client.getOutputStream();
        byte[] arr = new byte[1024];
        int len;
        while((len = inputStream.read(arr)) != -1) {
            out.write(arr, 0, len);
        }
        System.out.println("server read:" + out.toString("UTF-8"));
        outputStream.write("客户端你好呀，我是服务端".getBytes("UTF-8"));
        outputStream.flush();
        client.shutdownOutput();
        Thread.sleep(10000);
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
