package com.im.sky.spi.demo1;

/**
 * @author jiangchangwei
 * @date 2020-9-18 下午 2:03
 **/
public class Main {

    public static void main(String[] args) throws Exception {
        ITest iTest = ServiceLoader.load(ITest.class);
        iTest.test();
    }
}
