package com.im.sky.test.extendtest;

import com.alibaba.fastjson.JSON;

public class Test {

    public static void main(String[] args) {
        A b = new B();
        b.setNum(1);
        System.out.println(b.getNum());
        System.out.println(JSON.toJSONString(b));
    }
}
