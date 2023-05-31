package com.im.sky.javacore.jvm;

import java.util.ArrayList;
import java.util.List;

public class GCTest {

    public static void main(String[] args) {
        runTime();
    }

    private static void runTime() {
        List<byte[]> list = new ArrayList<>();
        for(int i = 0; i < 100 * 1024; i++) {
            list.add(new byte[1024]);
        }
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        list.get(0);
//        list = null;
//        runtime.gc();
    }
}
