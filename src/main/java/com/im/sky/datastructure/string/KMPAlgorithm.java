package com.im.sky.datastructure.string;

import java.util.Arrays;

/**
 * Created by jiangchangwei on 2019/7/9.
 *
 * KMP算法
 *
 * 源字符串S，模式串M
 *
 * 若S串的长度为n，模式串M的长度m，则整体的时间复杂度为O（m + n）
 */
public class KMPAlgorithm {

    public static void main(String[] args) {
        String s = "abcdeabfabdfe";
        String t = "abdabc";
        System.out.println(strIndex(s, t));



    }

    public static int strIndex(String s, String t) {
        int[] next = next(t);
        System.out.println(Arrays.toString(next));
        int i = 0;
        int j = 0;
        while(i < s.length() && j < t.length()) {
            if(j == -1 || s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            }else {
                j = next[j];
            }
        }
        if(j == t.length()) {
            return i - j;
        }
        return -1;
    }

    private static int[] next(String t) {
        int k = -1;
        int j = 0;
        int[] next = new int[t.length()];
        next[0] = -1;
        while(j < t.length() - 1) {
           if(k == -1  || t.charAt(k) == t.charAt(j)) {
               k++;
               j++;
               if(t.charAt(j) != t.charAt(k)) {
                   next[j] = k;
               }else {
                   next[j] = next[k];
               }
           }else {
               k = next[k];
           }
        }
        return next;
    }


}
