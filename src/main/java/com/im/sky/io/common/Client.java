package com.im.sky.io.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static void run() throws Exception {
        Socket socket = new Socket("127.0.0.1", 8882);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        // 往服务端写数据
        outputStream.write("服务端你好".getBytes("UTF-8"));
        outputStream.flush();
        socket.shutdownOutput();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        // 读取服务端的数据
        while((len = inputStream.read(arr)) != -1) {
            bao.write(arr, 0, len);
        }
        System.out.println("接收到的服务端数据:" + bao.toString());
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
