package com.im.sky.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class J_38 {
    public String[] permutation(String s) {
        List<String> res;
        int len = s.length();
        boolean[] isUse = new boolean[len];
        res = dfs(s, len, isUse, 0);
        return res.toArray(new String[res.size()]);
    }

    private List<String> dfs(String s, int len, boolean[] isUse, int count) {
        if(count == len) {
            return null;
        }
        Set<Character> set = new HashSet<>();
        List<String> res = new ArrayList<>();
        for(int i = 0; i < len; i++) {
            if(set.contains(s.charAt(i)) || isUse[i]) {
                continue;
            }
            Character c = s.charAt(i);
            set.add(c);
            isUse[i] = true;
            List<String> subList = dfs(s,len, isUse, count + 1);
            isUse[i] = false;
            if(subList == null) {
                res.add(c.toString());
            }else {
                for(String it : subList) {
                    res.add(c + it);
                }
            }
        }
        return res;
    }
}
