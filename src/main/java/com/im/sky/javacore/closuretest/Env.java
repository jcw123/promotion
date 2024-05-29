package com.im.sky.javacore.closuretest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description 环境接口
 * @date 2023-06-16 16:56
 **/
public interface Env {

    default String getEnv() {
        return null;
    }
}
