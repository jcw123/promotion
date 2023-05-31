package com.im.sky.test.inittest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description 水果
 * @date 2023-04-23 11:51
 **/
public class Fruit implements Food {

    String t2 = "t2";

    {
        System.out.println("步骤一");
        System.out.println("t2:" + t2);
        System.out.println("t3:" + t3);
        System.out.println("t4:" + t4);
    }

    static String t3 = "t3";

    static {
        System.out.println("步骤二");
    }

    static String t4 = "t4";

    String t5 = "t5";

    public Fruit() {
        System.out.println("t5:" + t5);
    }
}
