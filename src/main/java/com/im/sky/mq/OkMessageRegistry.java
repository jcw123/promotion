package com.im.sky.mq;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author jiangchangwei
 * @date 2020-10-10 下午 4:32
 **/
public class OkMessageRegistry {

    private static ConcurrentMap<String, Set<OKMessageBroker>> brokers = new ConcurrentHashMap<>();

    private static int port = 9999;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        new Acceptor(serverSocket).start();
        System.out.println("注册中心已启动");

    }

    private static class Acceptor extends Thread {

        ServerSocket serverSocket;

        public Acceptor(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    new Handler(serverSocket.accept()).start();
                }catch (Exception e) {

                }
            }
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] arr = new byte[1024];
                int len;
                while((len = is.read(arr)) != -1) {
                    bos.write(arr, 0, len);
                }
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.write("SUCCESS");
                pw.flush();
                socket.shutdownOutput();
            }catch (Exception e) {

            }
        }
    }



}
