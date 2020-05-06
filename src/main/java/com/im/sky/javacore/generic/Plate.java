package com.im.sky.javacore.generic;

/**
 * @author jiangchangwei
 * @date 2020-3-17 下午 12:42
 **/
public class Plate<T> {

    private T item;

    public Plate(T t) {
        item = t;
    }

    public void set(T t) {
        this.item = t;
    }

    public T get() {
        return this.item;
    }
}
