package com.im.sky.lock.juc.aqs;

/**
 * @author jiangchangwei
 * @date 2020-5-26 下午 8:24
 *
 * 用于控制同时只能有几个线程进行共享资源
 **/
public class LimitLock {

    public LimitLock(int threads) {
        if(threads <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
