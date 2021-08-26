package com.im.sky.javassist.log;

public class StupidLogger implements Logger {

    @Override
    public void log(String msg) {
        System.out.println("stupid:" + msg);
    }
}
