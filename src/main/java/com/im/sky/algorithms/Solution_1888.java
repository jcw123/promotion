package com.im.sky.algorithms;

public class Solution_1888 {

    public static void main(String[] args) {
        Solution_1888 solution_1888 = new Solution_1888();
        System.out.println(solution_1888.minFlips("01001001101"));
    }

    public int minFlips(String s) {
        int len = s.length();
        if(len == 1) {
            return 0;
        }
        int[][] arr = new int[2][len + 1];
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '0') {
                arr[0][i + 1] = arr[1][i];
                arr[1][i + 1] = arr[0][i] + 1;
            }else {
                arr[0][i + 1] = arr[1][i] + 1;
                arr[1][i + 1] = arr[0][i];
            }
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i <= s.length(); i++) {
            int m1 = arr[0][i];
            if(i % 2 == 0) {
                m1 += arr[0][len];
                if((len - i) %2 == 0) {
                    m1 -= arr[0][i];
                }else {
                    m1 -= arr[1][i];
                }
            }else {
                m1 += arr[1][len];
                if((len - i) %2 == 0) {
                    m1 -= arr[1][i];
                }else {
                    m1 -= arr[0][i];
                }
            }
            int m2 = arr[1][i];
            if(i % 2 == 0) {
                m2 += arr[1][len];
                if((len - i) %2 == 0) {
                    m2 -= arr[1][i];
                }else {
                    m2 -= arr[0][i];
                }
            }else {
                m2 += arr[0][len];
                if((len - i) %2 == 0) {
                    m2 -= arr[0][i];
                }else {
                    m2 -= arr[1][i];
                }
            }
            min = Math.min(min, Math.min(m1, m2));
        }
        return min;
    }
}
