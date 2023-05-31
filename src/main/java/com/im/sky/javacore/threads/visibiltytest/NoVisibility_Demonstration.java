package com.im.sky.javacore.threads.visibiltytest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-09 15:44
 **/
public class NoVisibility_Demonstration extends Thread {
    boolean ok = true;

    public static void main(String[] args) throws Exception {
        NoVisibility_Demonstration t = new NoVisibility_Demonstration();
        t.setDaemon(false);
        t.start();
        System.out.println("t1:" + t.ok);
        Thread.sleep(1000);
        t.ok = false;
        System.out.println("t2:" + t.ok);
    }

    @Override
    public void run() {
        int x = 1;
        while(this.ok) {
            x++;
        }
        System.out.println("x:" + x);
    }
}
