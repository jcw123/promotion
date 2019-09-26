package com.im.sky.other;

/**
 * @Author: jiangcw
 * @Date: 2019-9-22 下午 12:11
 * @Version 1.0
 */
public class Consumer {

    private Resources resources;

    public Consumer(Resources resources) {
        this.resources = resources;
    }

    public int get() {
        return resources.get();
    }
}
