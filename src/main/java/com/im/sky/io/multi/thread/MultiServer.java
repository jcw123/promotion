package com.im.sky.io.multi.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer implements Runnable {

    private int port;

    public MultiServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        new Thread(new MultiServer(8881)).start();
    }
}
