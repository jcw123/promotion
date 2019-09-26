package com.im.sky.other;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:11
 * @Version 1.0
 */
public class Producer {

    private Resources resources;

    public Producer(Resources resources) {
        this.resources = resources;
    }

    public void insert(int v) {
        resources.insert(v);
    }
}
