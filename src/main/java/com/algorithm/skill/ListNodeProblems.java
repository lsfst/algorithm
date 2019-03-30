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
package com.algorithm.skill;

import java.util.*;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class ListNodeProblems {
    public class ListNode {
        int val;
        ListNode next;

        ListNode( int x ) {
            val = x;
        }
    }

    /**
     * 给定一个带有头结点 head 的非空单链表，返回链表的中间结点。
     *
     * 如果有两个中间结点，则返回第二个中间结点。
     * @param head
     * @return
     */
    public ListNode middleNode( ListNode head ) {
        if(head.next==null){
            return head;
        }
        //双结点
        ListNode first=head,second=head;
        while ( second.next!=null && second.next.next!=null ){
            second=second.next.next;
            first=first.next;
        }
        if(second.next!=null){
            first=first.next;
        }
        return first;
    }

    /**
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     *
     * 示例：
     *
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     *
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     * 说明：
     *
     * 给定的 n 保证是有效的。
     *
     * 进阶：
     *
     * 你能尝试使用一趟扫描实现吗？
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //还是先计算长度吧
        ListNode node=head;
        int len=1;
        while ( node.next!=null ){
            len++;
            node=node.next;
        }
        int index=len-n;
        ListNode cur=head,pre=null;
        for(int i=0;i<index;i++){
            pre=cur;
           cur=cur.next;
        }
        if(pre==null){
            ListNode next=cur.next;
            cur.next=null;
            head=next;
        }else {
            pre.next=cur.next;
        }
        return head;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        // 双节点，一个是first一个是second。先让first走n步，然后再让first和second同时往前走，当first走到头时，second即是倒数第n+1个节点了。
        ListNode first=head;
        ListNode pre=null;
        ListNode second=head;
        for(int i=0;i<n;i++){
            second=second.next;
        }
        //second==null 说明first即为要删除的节点
        while ( second!=null ){
            pre=first;
            first=first.next;
            second=second.next;
        }
        if(pre==null){
            //删除头结点
            head.next=null;
            head=first;
        }else {
            pre.next=first.next;
        }
        return head;
    }


    /**
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     *
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     *
     * 示例:
     *
     * 给定 1->2->3->4, 你应该返回 2->1->4->3.
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode cur=head;
        ListNode next=head.next;
        //交换头俩节点
        ListNode temp=next.next;
        cur.next=temp;
        next.next=cur;
        head=next;

        ListNode pre=cur;
        cur=temp;
        while ( cur!=null  ){
            if(cur.next!=null){
                //交换
                ListNode next1=cur.next;
                cur.next=next1.next;
                next1.next=cur;
                pre.next=next1;
                //赋值
                pre=cur;
                cur=pre.next;
            }else {
                break;
            }
        }
        return head;
    }


    /**
     * 给出一个链表，每 k 个节点一组进行翻转，并返回翻转后的链表。
     *
     * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么将最后剩余节点保持原有顺序。
     *
     * 示例 :
     *
     * 给定这个链表：1->2->3->4->5
     *
     * 当 k = 2 时，应当返回: 2->1->4->3->5
     *
     * 当 k = 3 时，应当返回: 3->2->1->4->5
     *
     * 说明 :
     *
     * 你的算法只能使用常数的额外空间。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        //基本同上:先检查长度是否足够
        int len=0;
        ListNode node=head;
        while ( node!=null ){
            len++;
            node=node.next;
        }
        if(len<k){
            return head;
        }

        ListNode preHead=new ListNode( -1 );
        preHead.next=head;
        ListNode curHead=preHead;
        //一次翻转K个节点
        ListNode[] nodes=new ListNode[k];
        ListNode cur=head;
        while ( len>=k ){
            len-=k;
          for(int i=0;i<k;i++){
              nodes[i]=cur;
              cur=cur.next;
          }
          //交换
            for(int i=k-1;i>0;i--){
                nodes[i].next=nodes[i-1];
            }
            nodes[0].next=cur;
            curHead.next=nodes[k-1];
            curHead=nodes[0];
        }
        return preHead.next;

    }

    /**
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     *
     * 示例:
     *
     * 输入:
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 输出: 1->1->2->3->4->4->5->6
     * @param lists
     * @return
     */
    //暴力计数排序，重新建立链表
    public ListNode mergeKLists(ListNode[] lists) {
        Map<Integer,Integer> map=new HashMap<>(  );
        for(ListNode node:lists){
            while ( node!=null ){
                map.put( node.val,map.getOrDefault( node.val,0 )+1 );
                node=node.next;
            }
        }
        if(map.keySet().size()==0){
            return null;
        }
        int keys[]=new int[map.keySet().size()];
        int index=0;
        for(int key:map.keySet()){
            keys[index]=key;
            index++;
        }
        Arrays.sort(keys);
        ListNode res=new ListNode( keys[0] );
        ListNode cur=res;
        for(int i=0;i<keys.length;i++){
            int len=map.get( i );
            if(i==0){
                len--;
            }
            while ( len>0 ){
                len--;
                cur.next=new ListNode( keys[i] );
                cur=cur.next;
            }

        }
        return res;
    }

    public ListNode mergeKLists2(ListNode[] lists) {
        //最小堆:打散重构
        PriorityQueue<ListNode> queue=new PriorityQueue<>( lists.length, new Comparator< ListNode >() {
            @Override
            public int compare( ListNode o1, ListNode o2 ) {
                return o1.val-o2.val;
            }
        } );
        for(int i=0;i<lists.length;i++){
            ListNode listNode=lists[i];
            while ( listNode!=null ){
                ListNode temp=listNode;
                listNode=listNode.next;
                temp.next=null;
                queue.add( temp );
            }
        }
        ListNode res=new ListNode( -1 );
        ListNode cur=res;
        while ( !queue.isEmpty() ){
            cur.next=queue.poll();
            cur=cur.next;
        }
        return res;
    }

    public ListNode mergeKLists3(ListNode[] lists) {
        //参考两个链表合并:但两两合并效率太低
        if(lists.length==0){
            return null;
        }
        if(lists.length==1){
            return lists[0];
        }
        ListNode res=mergeTwoList( lists[0],lists[1] );
        for(int i=2;i<lists.length;i++){
            res=mergeTwoList( res,lists[i] );
        }
        return res;
    }

    public ListNode mergeKLists4(ListNode[] lists) {
        //双链表合并的改进版：lists一分为二，分别合并，效率very nice
        if(lists.length==0){
            return null;
        }
        int n=lists.length;
        while ( n>1 ){
            int k = (n + 1) / 2;
            for (int i = 0; i < n / 2; ++i) {
                lists[i] = mergeTwoList(lists[i], lists[i + k]);
            }
            n = k;
        }
        return lists[0];
    }

    public ListNode mergeTwoList(ListNode node1,ListNode node2){
        if(node1==null){
            return node2;
        }
        if(node2==null){
            return node1;
        }
        ListNode head=new ListNode( -1 );
        ListNode res=head;
        ListNode temp;
        while ( node1!=null && node2!=null ){

            if(node1.val<node2.val){
                 temp=node1;
                node1=node1.next;
                temp.next=null;
                head.next=temp;
                head=head.next;

            }else {
                 temp=node2;
                node2=node2.next;
                temp.next=null;
                head.next=temp;
                head=head.next;
            }
        }
        if ( node1!=null ){
            head.next=node1;
        }
        if ( node2!=null ){
           head.next=node2;
        }
        return res.next;
    }


    /**
     * 61. 旋转链表
     *
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     *
     * 示例 1:
     *
     * 输入: 1->2->3->4->5->NULL, k = 2
     * 输出: 4->5->1->2->3->NULL
     * 解释:
     * 向右旋转 1 步: 5->1->2->3->4->NULL
     * 向右旋转 2 步: 4->5->1->2->3->NULL
     * 示例 2:
     *
     * 输入: 0->1->2->NULL, k = 4
     * 输出: 2->0->1->NULL
     * 解释:
     * 向右旋转 1 步: 2->0->1->NULL
     * 向右旋转 2 步: 1->2->0->NULL
     * 向右旋转 3 步: 0->1->2->NULL
     * 向右旋转 4 步: 2->0->1->NULL
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        //先统计长度
        if(k==0 || head==null || head.next==null){
            return head;
        }
        ListNode node=head;
        int count=0;
        ListNode tail=null;
        while ( node!=null ){
            count++;
            if(node.next==null){
                tail=node;
            }
            node=node.next;
        }
        k%=count;
        //注意顺序

        if(k>0){
            k=count-k;
            ListNode newHead=head;
            ListNode preNewHead=null;
            while ( k>0 ){
                preNewHead=newHead;
                newHead=newHead.next;
                k--;
            }
            preNewHead.next=null;
            tail.next=head;

            return newHead;

        }else {
            return head;
        }
    }

    /**
     * 86. 分隔链表
     * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
     *
     * 你应当保留两个分区中每个节点的初始相对位置。
     *
     * 示例:
     *
     * 输入: head = 1->4->3->2->5->2, x = 3
     * 输出: 1->2->2->4->3->5
     *
     * 并没说要在原链表上进行修改，可以再造一个
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode preHead1 = new ListNode( -1 );
        ListNode preHead2 = new ListNode( -1 );
        ListNode node1=preHead1;
        ListNode node2=preHead2;
        while ( head!=null ){
            if(head.val<x){
                node1.next=head;
                head=head.next;
                node1=node1.next;
                node1.next=null;
            }else {
                node2.next=head;
                head=head.next;
                node2=node2.next;
                node2.next=null;
            }

        }
        node1.next=preHead2.next;
        return preHead1.next;
    }

    /**
     * 89. 格雷编码
     *
     * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
     *
     * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。格雷编码序列必须以 0 开头。
     *
     * 示例 1:
     *
     * 输入: 2
     * 输出: [0,1,3,2]
     * 解释:
     * 00 - 0
     * 01 - 1
     * 11 - 3
     * 10 - 2
     *
     * 对于给定的 n，其格雷编码序列并不唯一。
     * 例如，[0,2,3,1] 也是一个有效的格雷编码序列。
     *
     * 00 - 0
     * 10 - 2
     * 11 - 3
     * 01 - 1
     * 示例 2:
     *
     * 输入: 0
     * 输出: [0]
     * 解释: 我们定义格雷编码序列必须以 0 开头。
     *      给定编码总位数为 n 的格雷编码序列，其长度为 2n。当 n = 0 时，长度为 20 = 1。
     *      因此，当 n = 0 时，其格雷编码序列为 [0]
     *
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {

    }
}