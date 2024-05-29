package com.im.sky.javacore.closuretest;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-06-16 17:13
 **/
public class LazyGo {

    public static void main(String[] args) {
        Consumer<Object> consumer = Operator.buildProcessFunction(new ProdEnv());
        consumer.accept(null);
    }
}
