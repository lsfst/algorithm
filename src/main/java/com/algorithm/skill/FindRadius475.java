/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190125    gaoyouan     初始版本
 */
package com.algorithm.skill;

import java.util.Arrays;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class FindRadius475 {
    public static int findRadius(int[] houses, int[] heaters) {
        //实际上求得是组中相邻两点的最大距离
        //位于两个heaters中的house，求小一点的距离
        //位于首部的heater和house求距离
        //位于尾部的两者距离
        //最后求最大值
        //滑动窗口
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int lenhouse=houses.length,lenheater=heaters.length;
        int startlen=0,endlen=0;
        if(houses[0]<heaters[0]){
            startlen=heaters[0]-houses[0];
        }else {
            for(int j=0;j<lenheater;j++){
                if(heaters[j]>houses[0]){
                    startlen=Math.min(heaters[j]-houses[0],houses[0] -heaters[j] );
                }else {
                    if(j==lenheater-1){
                        startlen=houses[0] -heaters[j];
                    }
                }
            }
        }
        if(houses[lenhouse-1]>heaters[lenheater-1]){
            endlen=houses[lenhouse-1]-heaters[lenheater-1];
        }else {
            for(int j=lenheater-1;j>=0;j--){
                if(heaters[j]<houses[lenhouse-1]){
                    endlen=Math.min(heaters[j+1]-houses[lenhouse-1],houses[lenhouse-1] -heaters[j] );
                    break;
                }else {
                    if(j==0){
                        endlen=houses[lenhouse-1] -heaters[0];
                    }
                }
            }
        }
        //先找到一头一尾的最小距离 ()
        int max=Math.max( startlen,endlen );
        for(int i=1,j=0;i<lenhouse-1 && j<lenheater;){
            if(houses[i]<heaters[j]){
                max=Math.max( max,heaters[j]-houses[i] );
                i++;
            }else {
                if(j<lenheater-1 && houses[i]<heaters[j+1]){
                    max=Math.max( max,Math.min(heaters[j+1]-houses[i],houses[i]-heaters[j]) );
                    i++;
                }else {
                    j++;
                }
            }
        }
        return max;
    }


    public static int findRadius2(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int n = houses.length;
        int m = heaters.length;
        int minimum = 0;
        int j = 0;
        for(int i = 0 ;i< n ;i++){
            while( j < m - 1 && (Math.abs(heaters[j] - houses[i]) >= Math.abs(heaters[j+1] - houses[i])))j ++;
            //判断当前的房子离哪个加热器近，如果离左边的加热器比较近，更新最小半径为 以前的 和 这次中的较大的
            //如果离右边加热器比较近，遍历下一个加热器
            //如[1,2,3,4],[1,4]    1，2离1近，3，4离4近，距离差最大是1
            minimum = Math.max(minimum,Math.abs(heaters[j] - houses[i]));
        }
        return minimum;
    }
}