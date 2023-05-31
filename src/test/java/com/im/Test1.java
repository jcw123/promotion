package com.im;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-21 20:00
 **/
public class Test1 {

    static {
        System.out.println("sleep start");
        try {
            Thread.sleep(20 * 1000);
        }catch (Exception e) {

        }
        System.out.println("sleep end");
        Test2 test2 = new Test2();
    }
}
