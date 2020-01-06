package com.im.sky.spring.bean;

import com.im.sky.spring.annotation.BeanScan;

/**
 * @author jiangchangwei
 * @date 2020-1-2 下午 5:49
 **/
@BeanScan
public class BeanScanA {

    public void name() {
        System.out.println("bean is beanScanA");
    }
}
