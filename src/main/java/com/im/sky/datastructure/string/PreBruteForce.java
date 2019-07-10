package com.im.sky.datastructure.string;

/**
 * Created by jiangchangwei on 2019/7/9.
 * 前缀蛮力匹配算法
 */
public class PreBruteForce {

    public static void main(String[] args) {
        System.out.println(strIndex("abcdeabdabdfe", "abdabd"));
    }

    public static int strIndex(String s , String t) {
        int i = 0;
        int j = 0;
        while(i < s.length() && j < t.length()) {
            if(s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            }else {
                i = i - j + 1;
                j = 0;
            }
        }
        if(j == t.length()) {
            return i - j;
        }else {
            return -1;
        }
    }
}
