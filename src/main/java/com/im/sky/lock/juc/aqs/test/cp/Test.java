package com.im.sky.lock.juc.aqs.test.cp;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 2:02
 **/
public class Test {

    public static void main(String[] args) {
        DataContainer container = new DataContainer(8);
        Consumer consumer = new Consumer(container);
        Producer producer = new Producer(container);
        Thread t1 = new Thread(() -> {
            while(true) {
                try {
                    producer.put(1);
                    Thread.sleep(1000);
                }catch (Exception e) {}
            }
        });
        Thread t2 = new Thread(() -> {
            while(true) {
                try {
                    Object o = consumer.take();
                    System.out.println(o);
                }catch (Exception e) {}
            }
        });
        t1.start();
        t2.start();
    }
}
