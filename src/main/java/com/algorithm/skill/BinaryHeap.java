/**
 * algorithm 版权声明
 * Copyright (c) 2018, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20181121 Chris 初始版本
 */
package com.algorithm.skill;

/**
 * @author lsf
 * @Date 2018/11/21 11:47
 * @Description 二叉堆：具有完全二叉树的特性。
 * 堆中的任何一个父节点的值都大于等于它左右孩子节点的值，或者都小于等于它左右孩子节点的值，由此可以分为最大堆和最小堆
 * 二叉堆的根节点称之为堆顶。根据二叉堆的特性，堆顶要么是整个堆中的最大元素，要么是最小元素。
 * 二叉树实现一般是链表，二叉堆实现一般是数组
 * @return
 */
public class BinaryHeap {
    public static int[] buildHead(int [] arr,int length){
        for(int i=(length-2)/2;i>=0;i++){
            arr=downAdjust( arr,i,length );
        }
        return arr;
    }

    /**
     * @author lsf
     * @Date 2018/11/21 13:46
     * @param parent 要下沉元素的下标
     * @param length 数组长度
     * @Description 要把一个无序的完全二叉树调整为二叉堆，我们可以让所有非叶子节点依次下沉,不过下沉的顺序不是从根节点开始下沉,而是从下面的非叶子节点下沉，在依次往上
     * @return
     */
    public static int[] downAdjust(int[] arr,int parent,int length){
        int temp=arr[parent];
        //获取左子节点
        int child=2*parent+1;
        while ( child<length ){
            //如果右子节点比左子节点小，则定位到右子节点
            if(child+1<length && arr[child]>arr[child+1]){
                child++;
            }
            //父节点比子节点小或等于，下沉结束
            if(temp<=arr[child]){
                break;
            }
            //否则子节点值上浮
            arr[parent]=arr[child];
            parent=child;
            //寻找子节点的子节点
            child=2*parent+1;
        }
        //父节点下沉
        arr[parent]=temp;
        return arr;
    }

    /**
     * @author lsf
     * @Date 2018/11/21 13:46
     * @param length 数组长度
     * @Description 插入操作，一把在尾部插入，再上浮
     * @return
     */
    public static int[] upAdjust(int arr[],int length){
        int child=length-1;
        int parent=(child-2)/2;
        int temp=arr[child];
        while ( child>0 && temp<arr[parent] ){
            //当temp找到正确的位置之后，我们再把temp的值赋给这个节点
            arr[child] = arr[parent];
            child = parent;
            parent = (child - 1) / 2;
        }
        //退出循环代表找到正确的位置
        arr[child] = temp;
        return arr;
    }
}