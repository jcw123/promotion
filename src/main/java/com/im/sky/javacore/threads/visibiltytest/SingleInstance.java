package com.im.sky.javacore.threads.visibiltytest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-23 18:12
 **/
public class SingleInstance {

    private static SingleInstance INSTANCE;

    private SingleInstance() {

    }

    public static SingleInstance getInstance() {
        if(INSTANCE == null) {
            synchronized (SingleInstance.class) {
                if(INSTANCE == null) {
                    INSTANCE = new SingleInstance();
                }
            }
        }
        return INSTANCE;
    }
}
