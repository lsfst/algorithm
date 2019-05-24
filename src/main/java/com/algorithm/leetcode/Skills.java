/**
 * algorithm 版权声明
 * Copyright (c) 2018, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20181119 Chris 初始版本
 */
package com.algorithm.leetcode;

/**
 * @author lsf
 * @Date 2018/11/19 10:36
 * @Description
 */
public class Skills {
    /**
     * @Description n个无序的int组成整型数组arr，这些整数的取值范围都在0-20之间，要在 O(n) 的时间复杂度中把这 n 个数按照从小到大的顺序打印出来。
     * 数组的下标是一个隐含的很有用的数组，特别是在统计一些数字，或者判断一些整型数是否出现过的时候。例如，给你一串字母，让你判断这些字母出现的次数时，
     * 我们就可以把这些字母作为下标，在遍历的时候，如果字母a遍历到，则arr[a]就可以加1了，即  arr[a]++;
     * 这个时间复杂度，不可能排序
     * @return
     */
    public void arrIndex(int arr[]){
        int temp[]=new int[21];
        for(int i=0;i<arr.length;i++){
            temp[arr[i]]++;
        }
        //顺序打印
        for(int i=0;i<21;i++){
            for(int j=0;j<temp[i];j++){
                System.out.print( i );
            }
        }
    }


    /**
     * @Description 双指针
     * 判断单链表是否有环：设置一个慢指针和一个快指针来遍历这个链表。慢指针一次移动一个节点，而快指针一次移动两个节点，如果该链表没有环，则快指针会先遍历完这个表，如果有环，则快指针会在第二次遍历时和慢指针相遇。
     * 如何一次遍历就找到链表中间位置节点：设置一个快指针和慢指针。慢的一次移动一个节点，而快的两个。在遍历链表的时候，当快指针遍历完成时，慢指针刚好达到中点。
     * 单链表中倒数第 k 个节点：设置两个指针，其中一个指针先移动k个节点。之后两个指针以相同速度移动。当那个先移动的指针遍历完成的时候，第二个指针正好处于倒数第k个节点。
     * @return
     */


    /**递归优化
     * 1.状态保存(空间换时间)
     * @Description 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法？
     * f(n)=f(n-1)+f(n-2)=(f(n-2)+f(n-3)) + (f(n-3)+f(n-4)) 显然，有大量重复计算
     * 可以考虑将f(n)的值保存起来,默认为0，说明未被计算
     * 2.递归-》递推：减少栈调用次数(减少空间)
     */
        int[] arr = new int[1000];
        public int FrogJump(int n){
            //f(n)=f(n-1)+f(n-2)

            if(n>2){
                if(arr[n]==0){
                    arr[n]= FrogJump( n-1 )+FrogJump( n-2 );
                }
                return arr[n];
            }else {
                return n;
            }
        }

    public int FrogJump2(int n){
        if(n<2){
            return n;
        }

        int f1 = 1;
        int f2 = 2;
        int sum = 0;

        for(int i=3;i<n;i++){
            sum=f1+f2;
            f1=f2;
            f2=sum;
        }
        return sum;
    }

}