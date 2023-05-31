package com.im.sky.test.inittest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-23 11:54
 **/
public class Apple extends Fruit {

    String t6 = "t6";

    {
        System.out.println("t6:" + t6);
        System.out.println("t7:" + t7);
        System.out.println("t8:" + t8);
    }

    static String t7 = "t7";

    static {
        System.out.println("步骤四");
    }

    static String t8 = "t8";

    String t9 = "t9";

    public Apple(){
        System.out.println("t9:" + t9);
    }

    public static void main(String[] args) {
        Apple apple = new Apple();
    }
}
