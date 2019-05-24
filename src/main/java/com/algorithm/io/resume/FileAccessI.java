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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class FileAccessI implements Serializable {
    RandomAccessFile oSavedFile;
    long nPos;
    public FileAccessI() throws IOException
    {
        this("",0);
    }
    public FileAccessI(String sName,long nPos) throws IOException
    {
        oSavedFile = new RandomAccessFile(sName,"rw");
        this.nPos = nPos;
        oSavedFile.seek(nPos);
    }
    public synchronized int write(byte[] b,int nStart,int nLen)
    {
        int n = -1;
        try{
            oSavedFile.write(b,nStart,nLen);
            n = nLen;
        }
        catch(IOException e)
        {
            e.printStackTrace ();
        }
        return n;
    }
}