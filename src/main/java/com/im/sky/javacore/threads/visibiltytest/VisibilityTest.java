package com.im.sky.javacore.threads.visibiltytest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-09 15:31
 **/
public class VisibilityTest {

    private static boolean start = false;

    private static final Object mutex1 = new Object();

    private static final Object mutex2 = new Object();

    public static void main(String[] args) throws Exception {
        People people = new People();
        people.setAge(10);
        Method setter = People.class.getDeclaredMethod("setAge", int.class);
        Field ageField = People.class.getDeclaredField("age");
        Thread t2 = new Thread(() -> {
            int x = 0;
            while(people.getAge() != 11) {
                try {
                    Thread.sleep(10);
                }catch (Exception e) {

                }
                x++;
            }
            System.out.println("start become true, t2 exit, x:"+ x);
        });
        t2.start();
        try {
            Thread.sleep(1000);
        }catch (Exception e) {

        }
        Thread t1 = new Thread(() -> {
            try {
//                synchronized (mutex1) {
//
//                }
//                people.setAge(11);
                setter.invoke(people, 11);
//                ageField.set(people, 11);
                System.out.println("test:" + people.getAge());
            }catch (Exception e) {
                e.printStackTrace();
            }
//            try {
//                Thread.sleep(3000);
//            }catch (Exception e) {
//
//            }
        });
        t1.setDaemon(false);
        t1.start();
        System.out.println("end");
        t1.join();
    }

//    public static boolean getStart() {
//        synchronized (mutex1) {
//
//        }
//        return start;
//    }
//
//    public static void updateStart() {
//        start = true;
//    }
}
