package com.im.sky.javacore.threads.volatiletest;

public class ObjectVolatileTest {

    public static final int  max = 5;

    public volatile  static ValHolder valHolder = new ValHolder();

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = valHolder.val;
            while(localValue < max) {
                if(valHolder.val > localValue) {
                    System.out.println("感知到initValue值得变化，initValue:" + valHolder.val);
                    localValue = valHolder.val;
                }
            }
        }).start();


        new Thread(() -> {
            while(valHolder.val < max) {
                valHolder.val++;
                System.out.println("initValue:" + valHolder.val);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class ValHolder {
        private int val;
    }
}
