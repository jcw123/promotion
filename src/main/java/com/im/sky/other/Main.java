package com.im.sky.other;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:20
 * @Version 1.0
 */
public class Main {

    public static void main(String[] args) {
        Resources resources = new Resources(2);
        Consumer consumer = new Consumer(resources);
        Producer producer = new Producer(resources);
        new Thread(() -> {
            while (true) {
                int m = consumer.get();
                System.out.println("c:" + m);
            }
        }).start();

        new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                producer.insert(1);
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e) {}
            }
        }).start();
    }
}
