package com.im.sky.test.schedule;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    private static final Timer timer = new Timer();

    public static void main(String[] args) throws Exception {
        testDelayAndSchedule();
        Thread.sleep(100 * 1000 );
    }

    private static void testDelay() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello, world");
            }
        }, 1000);
    }

    private static void testDelayAndSchedule() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello, world");
                 try {
                     Thread.sleep(1 * 1000);
                     System.out.println(System.currentTimeMillis());
                 }catch (Exception ignored) {

                 }
            }
        }, 2000, 2000);
    }

}
