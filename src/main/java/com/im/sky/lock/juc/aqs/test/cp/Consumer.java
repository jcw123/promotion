package com.im.sky.lock.juc.aqs.test.cp;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 1:29
 **/
public class Consumer {

    private DataContainer container;

    public Consumer(DataContainer container) {
        this.container = container;
    }

    public Object take() throws InterruptedException {
        return container.take();
    }
}
