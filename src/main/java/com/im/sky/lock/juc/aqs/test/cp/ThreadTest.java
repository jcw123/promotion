package com.im.sky.lock.juc.aqs.test.cp;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 3:52
 **/
public class ThreadTest {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    return;
                }catch (Exception e) {

                }
            }
        });
        t.start();

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("start t2");
                t.join();
                System.out.println("end t2");
            }catch (Exception e) {}
        });
        t2.start();
//        t.interrupt();
    }
}
