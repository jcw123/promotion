package com.im.sky.test;

/**
 * @author jiangchangwei
 * @date 2020-6-14 下午 3:04
 **/
public class NoVisibility {

    private static int number;

    private static boolean ready;

    private static class ChangeThread extends Thread {
        @Override
        public void run() {
            while(!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ChangeThread().start();
        number = 10;
        ready = true;
    }
}
