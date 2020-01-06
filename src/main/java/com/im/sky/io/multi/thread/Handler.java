package com.im.sky.io.multi.thread;

import com.im.sky.io.single.thread.Server;

import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {

    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Server.process(socket);
        }catch (IOException e) {

        }
    }

    public void start() {
        new Thread(this).start();
    }
}
