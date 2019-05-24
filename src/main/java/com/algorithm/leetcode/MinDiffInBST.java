/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190213    gaoyouan     初始版本
 */
package com.algorithm.leetcode;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *给定一个二叉搜索树的根结点 root, 返回树中任意两节点的差的最小值。
 * 中序遍历:需要保存前一个值
 * @see
 *
 */
public class MinDiffInBST {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode( int x ) {
            val = x;
        }
    }

    public int pre=Integer.MIN_VALUE;
    public int min=Integer.MAX_VALUE;
    public int minDiffInBST( TreeNode root ) {
        inorder(root);
        return min;
    }

    public void inorder( TreeNode root ){
        if(root==null){
            return;
        }
        inorder( root.left );
        if(pre==Integer.MIN_VALUE){
            pre=root.val;
        }else {
            min=Math.min( root.val-pre,min );
            pre=root.val;
        }
        inorder( root.right );
    }
}