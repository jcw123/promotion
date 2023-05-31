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

    private TreeNode parent;

    private TreeNode left;

    private TreeNode right;

    private int val;
}
