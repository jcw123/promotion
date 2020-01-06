package com.im.sky.lock.juc.aqs.test.cp;

/**
 * @author jiangchangwei
 * @date 2019-11-30 下午 2:00
 **/
public class Producer {

    private DataContainer container;

    public Producer(DataContainer container) {
        this.container =  container;
    }

    public void put(Object o) throws InterruptedException {
        container.put(o);
    }
}
