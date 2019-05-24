/**
 * lgorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190126    gaoyouan     初始版本
 */
package com.algorithm.leetcode;

import java.util.*;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class NextGreaterElement {
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<Integer>();
        HashMap<Integer, Integer> hasMap = new HashMap<Integer, Integer>();

        int[] result = new int[nums1.length];

        for(int num : nums2) {
            while(!stack.isEmpty() && stack.peek()<num){
                hasMap.put(stack.pop(), num);
            }
            stack.push(num);
        }

        for(int i = 0; i < nums1.length; i++) result[i] = hasMap.getOrDefault(nums1[i], -1);

        return result;
    }

    public static String[] findRelativeRanks(int[] nums) {
        int[] copy=new int[nums.length];
        System.arraycopy(nums,0,copy,0,nums.length);
        Arrays.sort(copy);
        Map<Integer,String> map=new HashMap();
        for(int i=0;i<copy.length;i++){
            if(i==copy.length-3){
                map.put(copy[i],"Bronze Medal");
            }else if(i==copy.length-2){
                map.put(copy[i],"Silver Medal");
            }else if(i==copy.length-1){
                map.put(copy[i],"Gold Medal");
            }else {
                map.put(copy[i],copy.length-i+"");
            }

        }
        String[] res=new String[nums.length];
        for(int j=0;j<nums.length;j++){
            res[j]=map.get(nums[j]);
        }
        return res;
    }

    public boolean checkPerfectNumber(int num) {
        if(num==1){
            return true;
        }
        int mid=num/2;
        int sum=0;
        for(int i=mid;i>=1;i-- ){
            if(num%i==0){
                sum+=i;
            }
        }
        return num==sum;
    }

    public static boolean detectCapitalUse(String word) {
        if(word==null || word.equals( "" )){
            return true;
        }
        char[] chars=word.toCharArray();
        boolean firstBig=true;
        if(chars[0]-'a'>=0){
            firstBig=false;
        }
        if(firstBig){
            if(chars.length>1){
                if(chars [1]-'a'>=0){
                    //后面小写
                    for(int i=2;i<chars.length;i++){
                        if(chars[i]-'a'<0){
                            return false;
                        }
                    }
                }else {
                    //全大写
                    for(int i=2;i<chars.length;i++){
                        if(chars[i]-'a'>=0){
                            return false;
                        }
                    }
                }
//                for(int i=1;i<chars.length;i++){
//                    if(chars[i]-'a'<0){
//                        return false;
//                    }
//                }
            }

        }else {
            for(int i=1;i<chars.length;i++){
                if(chars[i]-'a'<0){
                    return false;
                }
            }
        }
        return true;
    }


    public List<List<Integer>> levelOrder(Node root) {
        //层序遍历，栈
        List<List<Integer>> res=new ArrayList<>(  );
        List<Integer> rootVal=new ArrayList<>(  );
        rootVal.add( root.val );
        res.add( rootVal );
        Stack<Node> stack=new Stack();
        stack.add(root);
        while(!stack.isEmpty()){
            List<Integer> nodeVal=new ArrayList<>(  );
            Node node=stack.pop();
            List<Node> nodes=node.children;
            for(Node node1:nodes){
                nodeVal.add( node1.val );
                stack.push( node1 );
            }
            if(!nodeVal.isEmpty())
            res.add( nodeVal );
        }
        return  res;
    }


}
class Node {
    public int val;
    public List< Node > children;

    public Node() {
    }

    public Node( int _val, List< Node > _children ) {
        val = _val;
        children = _children;
    }
}