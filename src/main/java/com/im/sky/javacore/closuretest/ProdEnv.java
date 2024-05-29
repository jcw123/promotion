package com.im.sky.javacore.closuretest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-16 16:58
 **/
public class ProdEnv implements Env {
    @Override
    public String getEnv() {
        return "prod";
    }
}
