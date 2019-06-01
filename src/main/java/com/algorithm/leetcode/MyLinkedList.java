/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190211    gaoyouan     初始版本
 */
package com.algorithm.leetcode;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class MyLinkedList {
//    int length;
//    ListNode head;
//    /** Initialize your data structure here. */
//    public MyLinkedList() {
//        length = 0;
//        head=null;
//    }
//
//    public int size() {
//        return length;
//    }
//
//    public boolean isEmpty() {
//        return length==0;
//    }
//
//    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
//    public int get(int index) {
//        if(index>=length){
//            return -1;
//        }else {
//            ListNode cur=head;
//            while ( index>0 ){
//                index--;
//                cur=cur.next;
//            }
//            return cur.val;
//        }
//    }
//
//    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
//    public void addAtHead(int val) {
//        if(head==null){
//            head=new ListNode(val);
//        }else{
//            ListNode node=new ListNode(val);
//            node.next=head;
//            head=node;
//        }
//        length++;
//    }
//
//    /** Append a node of value val to the last element of the linked list. */
//    public void addAtTail(int val) {
//        if(head==null){
//            head=new ListNode(val);
//        }else {
//            ListNode cur=head;
//            while ( cur.next!=null ){
//                cur=cur.next;
//            }
//            cur.next=new ListNode(val);
//        }
//        length++;
//    }
//
//    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
//    public void addAtIndex(int index, int val) {
//        if(index>length){
//            return;
//        }else if(index==length){
//            if(index==0){
//                head=new ListNode(val);
//            }else {
//                ListNode cur=head;
//                while(index>1){
//                    index--;
//                    cur=cur.next;
//                }
//                cur.next=new ListNode(val);
//            }
//            length++;
//        }else {
//            ListNode cur=head;
//            ListNode pre=null;
//            while(index>0){
//                index--;
//                cur=cur.next;
//                if(pre==null){
//                    pre=head;
//                }else {
//                    pre=pre.next;
//                }
//            }
//            ListNode next=new ListNode( val );
//            if(pre!=null){
//                pre.next=next;
//            }
//            next.next=cur;
//            length++;
//        }
//    }
//
//    /** Delete the index-th node in the linked list, if the index is valid. */
//    public void deleteAtIndex(int index) {
//        if(index>=length){
//            return;
//        }else {
//            ListNode cur=head;
//            ListNode pre=null;
//            ListNode next=cur.next;
//            if(index==0){
//                head=head.next;
//            }else {
//                while ( index>0 ){
//                    index--;
//                    pre=cur;
//                    cur=next;
//                    next=cur.next;
//                }
//                if(pre!=null){
//                    pre.next=next;
//                }
//            }
//          length--;
//        }
//    }
//
//    public class ListNode{
//        int val;
//        ListNode next;
//
//        public ListNode(int val){
//            this.val=val;
//            next=null;
//        }
//    }
int size;           //链表大小
    ListNode head;      //链表头部指针
    ListNode tail;     //这是指向链表的尾部节点的指针

    public MyLinkedList() {
    }

    //获取链表索引index处的Node节点的value，如果索引超出链表范围则返回-1
    public int get(int index) {
        if(index>size-1) return -1;
        int cnt=0;
        ListNode temp=head;
        while(cnt!=index){
            temp = temp.next;
            cnt++;
        }
        return temp.val;
    }

    //新建一个Node 让其指向当前的链表头部head，并更新当前链表的头部指针head
    public void addAtHead(int val) {
        if(head==null){
            head = new ListNode(val);
            size++;
            tail = head;
        }
        else {
            ListNode temp = new ListNode(val);
            temp.next = head;
            head = temp;
            size++;
        }
    }

    //新建一个node，让当前尾部节点tail指向该node，即tai.next=node，并更新tail=tail.next;
    public void addAtTail(int val) {
        if(head==null){
            head = new ListNode(val);
            size++;
            tail = head;
        } else{
            ListNode temp = new ListNode(val);
            tail.next = temp;
            tail = tail.next;
            size++;
        }
    }
    //在链表插入的过程中需要获取其前面的一个节点
    public ListNode getPreNode(int index){
        int cnt=0;
        ListNode temp = head;
        while(temp.next!=null){
            if(cnt!=index-1){
                temp = temp.next;
                cnt++;
            } else break;
        }
        return temp;

    }

    //增加节点，注意index为0 和size-1的特殊情况
    public void addAtIndex(int index, int val) {
        if(index==0) {addAtHead(val); return ;}
        if(index==size) {addAtTail(val);return ;}
        if(index<=size-1){
            ListNode temp = getPreNode(index);
            ListNode tempnext = temp.next;
            ListNode newNode = new ListNode(val);
            temp.next = newNode;
            newNode.next = tempnext;
            size++;
        }
    }

    //删除链表中某节点
    public void deleteAtIndex(int index) {
        if(index<size){
            if(index==0) {head=head.next;size--;}
            else{
                ListNode temp = getPreNode(index);
                if(index==size-1) {
                    temp.next = null;
                    tail = temp;
                    size--;
                } else {
                    ListNode tempnn = temp.next.next;
                    temp.next = tempnn;
                    size--;
                }
            }

        }
    }

    //内部类，一个链表的节点
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x){val = x;}
    }
}