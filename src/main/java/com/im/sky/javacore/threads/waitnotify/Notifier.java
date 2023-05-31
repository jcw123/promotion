package com.im.sky.javacore.threads.waitnotify;

public class Notifier extends Thread {

    private Message message;

    private String k;

    public Notifier(Message message, String k) {
        this.message = message;
        this.k = k;
    }

    @Override
    public void run() {
        synchronized (message) {
            System.out.println(k);
            message.notify();
        }
    }
}
