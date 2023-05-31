package com.im.sky.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public String oddString(String[] words) {
        Map<String, List<String>> map = new HashMap<>();
        for(String word : words) {
            String key = buildKey(word);
            List<String> list = map.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(word);
        }
        for(List<String> list : map.values()) {
            if(list.size() == 1) {
                return list.get(0);
            }
        }
        return null;
    }

    private String buildKey(String word) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < word.length(); i++) {
            sb.append(",").append(((word.charAt(i) - word.charAt(i - 1))));
        }
        return sb.toString();
    }
}