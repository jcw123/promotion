package com.im.sky.datastructure.string;

/**
 * Created by jiangchangwei on 2019/7/9.
 */
public class BMAlgorithm {

    public static void main(String[] args) {
        System.out.println(strIndex("abcdeabdabdfe", "abdabd"));
    }

    public static int strIndex(String s, String t) {
        int n = s.length();
        int m = t.length();

        if(m > n) {
            return -1;
        }
        int[] bad_table = build_bad_table(t);// 1,3,5,6,2,
        int[] good_table = build_good_table(t);// 1,8,5,10,11,12,13

        for (int i = m - 1, j; i < n;) {
            for (j = m - 1; s.charAt(i) == t.charAt(j); i--, j--) {
                if (j == 0) {
                    return i;
                }
            }
            i += Math.max(good_table[m - j - 1], bad_table[s.charAt(i)]);
        }
        return -1;

    }

    /**
     * 构建坏字符表
     * @param t
     * @return
     */
    public static int[] build_bad_table(String t) {
        final int table_size = 256;
        int[] bad_table = new int[table_size];
        int m = t.length();
        for(int  i = 0; i < table_size; i++) {
            bad_table[i] = m;
        }
        for(int j = 0; j < m - 1; j++) {
            int k = t.charAt(j);
            bad_table[k] = m - 1 - j;
        }
        return bad_table;
    }

    /**
     * 构建好的后缀表
     * @param t
     * @return
     */
    public static int[] build_good_table(String t) {
        int m = t.length();
        int[] good_table = new int[m];
        int lastPrefixPosition = m;
        for (int i = m - 1; i >= 0; --i) {
            if (isPrefix(t, i + 1)) {
                lastPrefixPosition = i + 1;
            }
            good_table[m - 1 - i] = lastPrefixPosition - i + m - 1;
        }

        for (int i = 0; i < m - 1; ++i) {
            int slen = suffixLength(t, i);
            good_table[slen] = m - 1 - i + slen;
        }
        return good_table;
    }

    /**
     * 前缀匹配
     * @param t
     * @param p
     * @return
     */
    private static boolean isPrefix(String t, int p) {
        int m = t.length();
        for(int i = p, j = 0; i < m; i++, j++) {
            if(t.charAt(i) != t.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 后缀匹配
     * @param t
     * @param p
     * @return
     */
    private static int suffixLength(String t, int p) {
        int m = t.length();
        int len = 0;
        for(int i = p, j = m - 1; i >= 0 && t.charAt(i) == t.charAt(j); i--, j--) {
            len++;
        }
        return len;
    }
}
