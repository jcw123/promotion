package com.im.sky.test.hashcodetest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-05-31 11:35
 **/
@EqualsAndHashCode
@Data
public class TreeNode {

    @EqualsAndHashCode.Exclude
    private TreeNode parent;

    private TreeNode left;

    private TreeNode right;

    private int val;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node = new TreeNode(2);
        root.left = node;
        node.parent = root;
        System.out.println(root.hashCode());
    }
}
