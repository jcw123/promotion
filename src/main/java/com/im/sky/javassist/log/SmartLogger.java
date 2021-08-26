package com.im.sky.javassist.log;

public class SmartLogger implements Logger {
    @Override
    public void log(String msg) {
        System.out.println("smart:" + msg);
    }
}
