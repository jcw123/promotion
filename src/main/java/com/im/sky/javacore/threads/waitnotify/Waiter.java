package com.im.sky.javacore.threads.waitnotify;

import lombok.Synchronized;

public class Waiter extends Thread {

    private Message message;

    private String k;

    public Waiter(Message message, String k) {
        this.message = message;
        this.k = k;
    }

    @Override
    public void run() {
        synchronized (message) {
            try {
                message.wait();
                System.out.println(k);
            }catch (Exception ignored) {

            }
        }
    }
}
