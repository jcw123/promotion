package com.im.sky.pattern.decorator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * Created by jiangchangwei on 2019/6/27.
 */
public class FileDecoratorTest {

    public static void main(String[] args) throws Exception {
        File file = new File("/tmp/a.txt");
        FileInputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[10];
        bufferedInputStream.read(bytes, 0, 10);
        System.out.println(new String(bytes));
    }
}
