package com.im.sky.javacore.threads.waitnotify;

public class WaitNotifyTest {

    public static void main(String[] args) throws Exception {
        Message message = new Message();
        Waiter waiter = new Waiter(message, "wait 1 ok");
        Waiter waiter2 = new Waiter(message, "wait 2 ok");
        waiter.start();
        waiter2.start();
        Notifier notifier = new Notifier(message, "notifier ok");
        notifier.start();
        Thread.sleep(5 * 1000);
    }
}
