package com.im.sky.datastructure.string;

public class KMP_V1 {

    public static void main(String[] args) {
        String s = "dsfsfa";
        String p = "sfa";
        System.out.println(search2(s, p));
    }

    public static int search2(String s, String pattern) {
        int n = s.length();
        int m = pattern.length();
        if(m > n) {
            return -1;
        }
        int[] next = buildNext(pattern);
        int i = 0;
        int j = 0;
        while(i < n && j < m) {
            if(s.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }else if(j != 0) {
                j = next[j - 1];
            }else {
                i++;
            }
        }
        if(j == m) {
            return i - j;
        }
        return -1;
    }

    private static int[] buildNext(String p) {
        int m = p.length();
        int[] next = new int[m];
        int count = 0;
        int i = 1;
        while(i < m  - 1) {
            if(p.charAt(count) == p.charAt(i)) {
                count++;
                next[i] = count;
                i++;
            }else if(count > 0) {
                count = next[count - 1];
            }else {
                i++;
            }
        }
        return next;
    }
}
