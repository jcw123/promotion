package com.im.sky.algorithms;

/**
 * @author jiangchangwei
 * @date 2019-12-11 下午 8:03
 **/
public class LC700 {

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    private static class Solution {
        public TreeNode searchBST(TreeNode root, int val) {
            TreeNode p = root;
            while(p != null) {
                if(p.val == val) {
                    return p;
                }else if(p.val < val) {
                    p = p.right;
                }else {
                    p = p.left;
                }
            }
            return null;
        }

        class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) {val = x;}
        }
    }
}
