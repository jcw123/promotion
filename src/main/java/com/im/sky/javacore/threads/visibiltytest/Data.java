package com.im.sky.javacore.threads.visibiltytest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-15 13:10
 **/
public class Data {

    private int value;

    public synchronized int get() {
        return this.value;
    }

    public synchronized void increment() {
        value++;
    }

    public static void main(String[] args) throws Exception {
        Data data = new Data();
        Thread t1 = new Thread(() -> {
           while(data.get() != 10) {

           }
            System.out.println("t1 exit");
        });
        t1.start();
        Thread.sleep(100);
        Thread t2 = new Thread(() -> {
           for(int i = 0; i < 10; i++) {
               data.increment();
           }
        });
        t2.start();
    }
}
