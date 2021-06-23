package com.im.sky.algorithms;

/**
 * 利用前缀树求解数组中任意两个元素异或的最大值
 */
class Solution1_421 {

    public static void main(String[] args) {
        int v = findMaximumXOR(new int[] {3,10,5,25,2,8});
        System.out.println(v);
    }

    public static int findMaximumXOR(int[] nums) {
        Tire root = new Tire();
        Tire p;
        for(int i = 0; i < nums.length; i++) {
            int v = nums[i];
            int base = 1 << 30;
            p = root;
            for(int j = 0; j < 31; j++) {
                boolean findOne = (v & base) != 0;
                base >>= 1;
                if(findOne) {
                    if(p.right == null) {
                        p.right = new Tire();
                    }
                    p = p.right;
                }else {
                    if(p.left == null) {
                        p.left = new Tire();
                    }
                    p = p.left;
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < nums.length; i++) {
            int v = nums[i];
            int sum = 0;
            int base = 1 << 30;
            p = root;
            for(int j = 0; j < 31; j++) {
                boolean findOne = (v & base) != 0;
                base >>= 1;
                if(findOne) {
                    if(p.left != null) {
                        sum = (sum << 1) + 1;
                        p = p.left;
                    }else {
                        p = p.right;
                        sum <<= 1;
                    }
                }else {
                    if(p.right != null) {
                        sum = (sum << 1) + 1;
                        p = p.right;
                    }else {
                        p = p.left;
                        sum <<= 1;
                    }
                }
            }
            max = Math.max(max, sum);
        }
        return max;

    }

    private static class Tire {
        Tire left;

        Tire right;
    }
}
