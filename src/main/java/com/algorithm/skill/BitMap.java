/**
 * algorithm 版权声明
 * Copyright (c) 2018, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20181120 Chris 初始版本
 */
package com.algorithm.skill;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lsf
 * @Date 2018/11/20 14:31
 * @Description BitMap算法
 * @Question1 给出20亿个非负数的int型整数，然后再给出一个非负数的int型整数 t ，如何判断t是否存在于这20亿数中？
 * @Question2 现在有五十亿个int类型的正整数，要从中找出重复的数并返回。
 * 假设这些数据以数组的形式给出
 * @return 最简单的，存进超大数组，进行遍历，这样时间复杂度为O(n)，内存占用4*20亿/1000/1000/1000约8G内存
 */
public class BitMap {
    //优化1:以数值作为数组下标，出现过的值置1，时间复杂度可以降到O(1)，但空间复杂度有所增加
    //由于int非负整数一共有 2^31 个，所以数组的大小需要 2^31 这么大。
    //不用HashSet是因为HashSet存的是对象，int转Integer会有额外开销

    //优化2：实际上由于只需要判断整数是否存在，所以数组存的int值可以用boolean代替，那么问题来了，int类型占用4个字节，boolean类型到底占用几个字节？
    //《Java虚拟机规范》中这样描述:虽然定义了boolean这种数据类型，但是只对它提供了非常有限的支持。
    // 在Java虚拟机中没有任何供boolean值专用的字节码指令，Java语言表达式所操作的boolean值，在编译之后都使用Java虚拟机中的int数据类型来代替，
    // 而boolean数组将会被编码成Java虚拟机的byte数组，每个元素boolean元素占8位。
    //这样我们就得出结论：一个boolean值占用内存4个字节的空间；但一个boolean数组中的每一个值占用内存1个字节的空间。
    //这样子，将int数组转换成boolean数组，占用内存可以下降到原来四分之一

    //优化3，更进一步，一个bit位有0,1两个状态，可以用一个bit表示一个数的存在状态，这样可以把内存再减少到原来八分之一，也即bitmap

    public static Set<Integer> getRepeats( int arr[]){
        //将重复的值存进set，防止返回重复的数
        Set<Integer> set=new HashSet<>(  );

        //BitSet 双倍扩容机制
        BitSet bitSet=new BitSet( Integer.MAX_VALUE );
        for (int i=0; i<arr.length ;i++){
            int value=arr[i];
            //判断该数是否存在bitSet里
            if(bitSet.get( value )){
                set.add( value );
            }else {
                bitSet.set(value, true);
            }
        }
        return set;
    }
}