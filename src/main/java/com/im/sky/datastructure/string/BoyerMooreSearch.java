package com.im.sky.datastructure.string;

import java.util.Arrays;

/**
 * Created by jiangchangwei on 2019/7/9.
 *
 * BM算法，仅仅利用坏字符来进行跳跃，将好后缀的跳跃先省略掉
 */
public class BoyerMooreSearch {

    public static void main(String[] args) {
        System.out.println(strIndex("abcdeabdabdfe", "abdabd"));
    }

    public static int strIndex(String s, String t) {
        int[] right = bad_table(t);
        int n = s.length();
        int m = t.length();
        int step;
        for(int i = m - 1, j; i < n; i += step) {
            step = 0;
            for(j = m - 1; j >= 0; j--) {
                if(s.charAt(i - (m - 1 - j)) != t.charAt(j)) {
                    step = j - right[s.charAt(i - (m - 1 - j))];
                    if(step < 1) {
                        step = 1;
                    }
                    break;
                }
            }
            if(j < 0) {
                return i - m + 1;
            }
        }
        return -1;
    }

    /**
     * 构建坏字符数据
      * @param t
     * @return
     */
    private static int[] bad_table(String t) {
        int table_size = 256;
        int[] bad_table = new int[256];
        for(int i = 0; i < table_size; i++) {
            bad_table[i] = -1;
        }
        for(int i = 0; i < t.length(); i++) {
            bad_table[t.charAt(i)] = i;
        }
        return bad_table;
    }
}

