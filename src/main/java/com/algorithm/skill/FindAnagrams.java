/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190118    gaoyouan     初始版本
 */
package com.algorithm.skill;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class FindAnagrams {
    public List<Integer> findAnagrams( String s, String p) {
        List<Integer> list=new ArrayList<>(  );
        if(s.length()<p.length()){
            return list;
        }
        //将p转换成滑动窗口，字符做下标，数量做value，再在s中截取等长的子串，遇到存在的元素做减法，直到所有value都为0
        //问题在于如何快速判断数组所有值都为0，可以定义一个变量进行记录，只要
        char[] chs=s.toCharArray();
        char[] chp=p.toCharArray();
        int [] arrp=new int[26];
        for(char ch:chp){
            arrp[ch-'a']++;
        }
        int count=chp.length;
        int start=0,end=0;
        while ( end<chs.length ){
            if(arrp[chs[end]-'a']-->1){
                count--;
            }
            end++;
            if(count==0){
                list.add( start );
            }
            if(end-start==chp.length){
                if(arrp[chs[start]-'a']++>=0){
                    count++;
                }
                start++;
            }
        }
        return list;
     }
}