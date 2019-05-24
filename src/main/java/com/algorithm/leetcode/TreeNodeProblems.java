/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190214    gaoyouan     初始版本
 */
package com.algorithm.leetcode;

import org.springframework.stereotype.Service;

import java.util.Stack;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
@Service
public class TreeNodeProblems {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    /**
     * 872. 叶子相似的树
     *
     * 请考虑一颗二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列 。
     * 如果有两颗二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
     *
     * 如果给定的两个头结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。
     *
     * 给定的两颗树可能会有 1 到 100 个结点。
     * @param root1
     * @param root2
     * @return
     */

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        //中序遍历
        Stack<Integer> stack1=new Stack<>();
        Stack<Integer> stack2=new Stack<>();
        inorder(root1,stack1);
        inorder( root2,stack2 );
        //比较stack
        if(stack1.size()!=stack2.size()){
            return false;
        }else {
            while ( !stack1.isEmpty() ){
                if(stack1.pop()!=stack2.pop()){
                    return false;
                }
            }
        }
        return true;
    }

    public void inorder( TreeNode root, Stack<Integer> stack ){
          if(root==null){
              return;
          }
          if(root.left==null && root.right==null){
              stack.add( root.val );
          }
          inorder( root.left,stack );
        inorder( root.right,stack );
    }

    /**
     * 给定一个树，按中序遍历重新排列树，使树中最左边的结点现在是树的根，并且每个结点没有左子结点，只有一个右子结点。
     *
     *输入：[5,3,6,2,4,null,8,1,null,null,null,7,9]
     *
     *        5
     *       / \
     *     3    6
     *    / \    \
     *   2   4    8
     *  /        / \
     * 1        7   9
     *
     * 输出：[1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
     *
     *  1
     *   \
     *    2
     *     \
     *      3
     *       \
     *        4
     *         \
     *          5
     *           \
     *            6
     *             \
     *              7
     *               \
     *                8
     *                 \
     *                  9
     *
     *
     * 提示：
     *
     * 给定树中的结点数介于 1 和 100 之间。
     * 每个结点都有一个从 0 到 1000 范围内的唯一整数值。
     * @param root
     * @return
     */
    public TreeNode increasingBST(TreeNode root) {
        return increasingBST(root, null);
    }

    public TreeNode increasingBST(TreeNode root, TreeNode tail) {
        if (root == null) return tail;
        TreeNode res = increasingBST(root.left, root);
        root.left = null;
        root.right = increasingBST(root.right, tail);
        return res;
    }
}