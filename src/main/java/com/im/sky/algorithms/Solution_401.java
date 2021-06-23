package com.im.sky.algorithms;

import java.util.ArrayList;
import java.util.List;

class Solution_401 {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> res = new ArrayList<>();
        if(turnedOn == 0) {
            res.add("0:00");
            return res;
        }
        if(turnedOn >= 9) {
            return res;
        }
        int m;
        for(int h = 0; h <= turnedOn; h++) {
            if(h >= 4 || (turnedOn - h) >= 6) {
                continue;
            }
            m = turnedOn - h;
            List<Integer> hList = allCom(4, 0, h);
            List<Integer> mList = allCom(6, 0, m);
            for(Integer hV : hList) {
                for(Integer mV : mList) {
                    if(hV > 11 || mV > 59) {
                        continue;
                    }
                    String s = "";
                    s += hV;
                    s += ":";
                    if(mV < 10) {
                        s += "0";
                    }
                    s += mV;
                    res.add(s);
                }
            }
        }
        return res;
    }

    private static List<Integer>  allCom(int n, int start, int k) {
        if(start + k > n) {
            return null;
        }
        List<Integer> res = new ArrayList<>();
        if(k == 0) {
            res.add(0);
            return res;
        }
        for(int i = start; i <= n - k; i++) {
            int curr = (1 << i);
            List<Integer> subList = allCom(n, i + 1, k - 1);
            if(subList == null) {
                res.add((curr));
            }else {
                for(Integer v : subList) {
                    res.add(curr + v);
                }
            }
        }
        return res;
    }
}
