package com.im.sky.test;

import java.util.*;

class SS {

    public static void main(String[] args) {
        System.out.println(countPairs(new int[]{1,3,5,7,9}));
    }

    private static final int MAX = 1 << 20;

    public static int countPairs(int[] deliciousness) {
        Map<Integer, Integer> counterMap = new HashMap<>();
        int res = 0;
        int v;
        for(int it : deliciousness) {
            v = Integer.highestOneBit(it);
            if(v == 0) {
                v = 1;
            }
            while(v < MAX) {
                Integer count = counterMap.get(v - it);
                if(count != null) {
                    res += count;
                }
                v <<= 1;
            }
            counterMap.put(it, counterMap.getOrDefault(it, 0) + 1);
        }
        return res;
    }
}