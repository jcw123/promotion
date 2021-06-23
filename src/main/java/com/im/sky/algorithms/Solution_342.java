package com.im.sky.algorithms;

public class Solution_342 {

    public static void main(String[] args) {
        Solution_342 solution_342 = new Solution_342();
        System.out.println(solution_342.isPowerOfFour2(16));
    }

    public boolean isPowerOfFour(int n) {
        double v = Math.log(n) / Math.log(4);
        return v == new Double(v).intValue();
    }

    public boolean isPowerOfFour2(int n) {
        n = Math.abs(n);
        double v = Math.sqrt(n);
        if(v != new Double(v).intValue()) {
            return false;
        }
        n = new Double(v).intValue();
        return (n & (n - 1)) == 0;
    }

    public boolean isPowerOfFour3(int n) {
        if(n <= 0) {
            return false;
        }
        return (n & (n - 1)) == 0 && (n & 0x2AAAAAAA) == 0;
    }

    public boolean isPowerOfFour4(int n) {
        if(n <= 0) {
            return false;
        }
        return (n & (n - 1)) == 0 && (n % 3) == 1;
    }
}
