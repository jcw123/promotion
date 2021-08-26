package com.im.sky.algorithms;

public class BCD {

    public static void main(String[] args) {
        BCD bcd = new BCD();
        System.out.println(bcd.exBcd(4, 2));
        System.out.println(bcd.x + " " + bcd.y);
    }

    private int bcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return bcd(b, a % b);
    }

    private int x;

    private int y;

    private int exBcd(int a, int b) {
        if(b == 0) {
            x = 1;
            y = 0;
            return a;
        }
        int d = exBcd(b, a % b);
        int tmp = y;
        y = x - a / b * y;
        x  = tmp;
        return d;
    }
}
