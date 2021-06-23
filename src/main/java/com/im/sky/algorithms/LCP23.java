package com.im.sky.algorithms;

import java.util.LinkedList;
import java.util.List;

class LCP23 {

    public static void main(String[] args) {
        LCP23 lcp23 = new LCP23();
        boolean ok = lcp23.isMagic(new int[] {2,4,3,1,5});
        System.out.println(ok);
    }

    public boolean isMagic(int[] target) {
        int len = target.length;
        int[] arr = new int[len];
        int[] tmp = new int[len];
        for(int i = 1; i <= len; i++) {
            arr[i - 1] = i;
            tmp[i - 1] = i;
        }
        change(tmp, 0, len - 1);
        int k = 0;
        for(; k < len; k++) {
            if(tmp[k] != target[k]) {
                break;
            }
        }
        if(k == 0) {
            return false;
        }
        if(k == len) {
            return true;
        }
        return canEquals(arr, len, k, target);
    }

    private boolean canEquals(int[] arr, int len, int k, int[] target) {
        int lo = 0;
        while(lo < len) {
            change(arr, lo, len - 1);
            for(int i = lo; i < Math.min(lo + k, len); i++) {
                if(arr[i] != target[i]) {
                    return false;
                }
            }
            lo = Math.min(lo + k, len);
        }
        return true;
    }

    private void change(int[] arr, int lo, int hi) {
        List<Integer> list = new LinkedList<>();
        int t = 1;
        int index = lo;
        for(int i = lo; i <= hi; i++) {
            if( t % 2 == 0) {
                arr[index] = arr[i];
                index++;
            }else {
                list.add(arr[i]);
            }
            t++;
        }
        for(Integer v : list) {
            arr[index++] = v;
        }
    }
}
