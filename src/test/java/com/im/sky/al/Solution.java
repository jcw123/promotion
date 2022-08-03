package com.im.sky.al;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] words = new String[]{"Science","is","what","we","understand","well","enough","to","explain", "to","a","computer.","Art","is","everything","else","we","do"};
        int maxWidth = 20;
        System.out.println(s.fullJustify(words, maxWidth));
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> list = new ArrayList<>();
        int len = words.length;
        int i = 0;
        while(i < len) {
            int end = i;
            int curLen = 0;
            boolean first = true;
            while(end < len && curLen + words[end].length() + (first ? 0 : 1) <= maxWidth) {
                curLen += words[end].length() + (first ? 0 : 1);
                first = false;
                end++;
            }
            int remain = maxWidth - curLen;
            int count = end - 1 - i;
            StringBuilder sb = new StringBuilder();
            if(end == len || count == 0) {
                sb.append(words[i]);
                int k = i + 1;
                while(k < end) {
                    sb.append(" ").append(words[k]);
                    k++;
                }
                while(remain-- > 0) {
                    sb.append(" ");
                }
            }else {
                StringBuilder tmp = new StringBuilder();
                for(int k = end - 1; k > i; k--) {
                    int curr = remain / count;
                    remain %= count;
                    count--;
                    sb.insert(0, words[k]);
                    while(curr-- > 0) {
                        tmp.append(" ");
                    }
                    sb.insert(0, tmp);
                    sb.insert(0, " ");
                }
                sb.insert(0, words[i]);
            }
            list.add(sb.toString());
            i = end;
        }
        return list;
    }
}
