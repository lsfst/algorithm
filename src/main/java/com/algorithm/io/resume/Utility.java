/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190524    lsf     初始版本
 */
package com.algorithm.io.resume;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class Utility {
    public Utility()
    {
    }
    public static void sleep(int nSecond)
    {
        try{
            Thread.sleep(nSecond);
        }
        catch(Exception e)
        {
            e.printStackTrace ();
        }
    }
    public static void log(String sMsg)
    {
        System.err.println(sMsg);
    }
    public static void log(int sMsg)
    {
        System.err.println(sMsg);
    }
}