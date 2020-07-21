package com.im.sky.javacore.threads;

import com.google.common.base.Charsets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jiangchangwei
 * @date 2020-5-15 下午 5:20
 **/
public class NetworkService implements Runnable {

    public static void main(String[] args) throws Exception{
        new NetworkService(8003, 3).run();
    }

    private final ServerSocket serverSocket;

    private final ExecutorService pool;

    public NetworkService(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        try {
            while(true) {
                pool.execute(new Handler(serverSocket.accept()));
            }
        }catch (IOException e) {
            pool.shutdown();
        }
    }

    static class Handler implements Runnable {

        private final Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            OutputStream os;
            InputStream is;
            try {
               is = socket.getInputStream();
               os = socket.getOutputStream();
               byte[] arr = new byte[1024];
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                int len;
                while((len = is.read(arr)) != -1) {
                    bao.write(arr, 0, len);
                }
                String s = bao.toString("UTF-8");
                s = "server response:" + s;
                os.write(s.getBytes(Charsets.UTF_8));
                os.flush();
           }catch (Exception e) {

            }
        }
    }
}
