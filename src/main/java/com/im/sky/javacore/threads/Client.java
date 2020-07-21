package com.im.sky.javacore.threads;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jiangchangwei
 * @date 2020-5-18 下午 12:18
 **/
public class Client implements Runnable{

    private volatile Socket socket;

    private String ip;

    private int port;

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = new Socket(ip, port);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // 利用双重锁进行实例化
            if(this.socket == null) {
                synchronized (this) {
                    if(this.socket == null) {
                        this.socket = new Socket(ip, port);
                    }
                }
            }
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            os.write("你好呀".getBytes(StandardCharsets.UTF_8));
            os.flush();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] arr = new byte[1024];
            int len;
            if((len = is.read(arr)) != -1) {
                bos.write(arr, 0, len);
            }
            System.out.println(bos.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
