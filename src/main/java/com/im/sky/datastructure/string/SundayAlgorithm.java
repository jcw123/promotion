package com.im.sky.datastructure.string;

/**
 * Created by jiangchangwei on 2019/7/9.
 *
 *
 *Sunday算法
 *
 * 字符模式匹配算法的另一种改进
 *
 * 思想：
 * Sunday算法是从前往后匹配，在匹配失败时关注的是主串中参加匹配的最末位字符的下一位字符。
 *
 * 如果该字符没有在模式串中出现则直接跳过，即移动位数 = 模式串长度 + 1；
 * 否则，其移动位数 = 模式串长度 - 该字符最右出现的位置(以0开始) = 模式串中该字符最右出现的位置到尾部的距离 + 1。
 */
public class SundayAlgorithm {

    public static void main(String[] args) {
        System.out.println(strIndex("abcdeabdabdfe", "bdfe"));
    }

    public static int strIndex(String s, String t) {
        int n = s.length();
        int m = t.length();
        if(m > n) {
            return -1;
        }
        int[] wordShuffle = wordShuffle(t);
        int step = 0;
        int head;
        for(int i = 0, j; i < n; i = head + step) {
            head = i;
            for(j = 0; j < m; j++) {
                if(s.charAt(i) != t.charAt(j)) {
                    int next = head + m;
                    if(next >= n) {
                        return -1;
                    }
                    step = m - wordShuffle[s.charAt(next)];
                    break;
                }else {
                    i++;
                }
            }
            if(j == m) {
                return i - m;
            }
        }
        return -1;


    }

    private static int[] wordShuffle(String t) {
        int table_size = 256;
        int[] wordShuffle = new int[table_size];
        for(int i = 0; i < table_size; i++) {
            wordShuffle[i] = -1;
        }
        for(int i = 0; i < t.length(); i++) {
            wordShuffle[t.charAt(i)] = i;
        }
        return wordShuffle;
    }
}
