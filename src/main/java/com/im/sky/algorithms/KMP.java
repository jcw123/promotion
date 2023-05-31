package com.im.sky.algorithms;

import java.util.Arrays;

/**
 *
 * src :      a b a b a c a
 * pattern :  a b a c
 *
 * 前缀真子串： a ab
 *
 * 后缀真子串：a  ba
 *
 */
public class KMP {

    public static void main(String[] args) {
        System.out.println(new KMP().kmp("abacdacaca", "acaca"));
    }

    public int kmp(String src, String pattern) {
        int n = src.length();
        int m = pattern.length();
        if(n < m) {
            return -1;
        }
        int[] next = buildNext(pattern);
        System.out.println(Arrays.toString(next));
        int j = 0;
        int i = 0;
        while(i < n && j < m) {
            if(src.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }else if(j > 0) {
                j = next[j - 1];
            }else {
                i++;
            }
            if(j == m) {
                return i - j;
            }
        }
        return -1;
    }

    private int[] buildNext(String pattern) {
        int n = pattern.length();
        int[] next = new int[n];
        int count = 0;
        int i = 1;
        while(i < n) {
            while(count > 0 && pattern.charAt(count) != pattern.charAt(i)) {
                count = next[count - 1];
            }
            if(pattern.charAt(count) == pattern.charAt(i)) {
                count++;
            }
            next[i] = count;
            i++;
        }
        return next;
    }
}
