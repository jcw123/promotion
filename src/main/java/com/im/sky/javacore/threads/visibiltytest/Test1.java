package com.im.sky.javacore.threads.visibiltytest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-23 18:19
 **/
public class Test1 {

    public static void main(String[] args) throws Exception {
        Test1 test1 = new Test1();
        Thread t1 = new Thread(()-> {
            while (test1.get() != 10) {

            }
            System.out.println("t1 exit");
        });
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                test1.increment();
            }
        });
        t2.start();
    }

    private int value;

    public int get() {
        return this.value;
    }

    public synchronized void increment() {
        value++;
    }
}
