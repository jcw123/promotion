package com.im.sky;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class B extends A {

    public static void main(String[] args) {
        System.out.println("hello, world");
    }

    @Test
    public void test() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.parse(null);
    }
}
