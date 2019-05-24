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
public class SiteInfoBean {
    private String sSiteURL; //Site's URL
    private String sFilePath; //Saved File's Path
    private String sFileName; //Saved File's Name
    private int nSplitter; //Count of Splited Downloading File
    public SiteInfoBean()
    {
//default value of nSplitter is 5
        this("","","",5);
    }
    public SiteInfoBean(String sURL,String sPath,String sName,int nSpiltter)
    {
        sSiteURL= sURL;
        sFilePath = sPath;
        sFileName = sName;
        this.nSplitter = nSpiltter;
    }
    public String getSSiteURL()
    {
        return sSiteURL;
    }
    public void setSSiteURL(String value)
    {
        sSiteURL = value;
    }
    public String getSFilePath()
    {
        return sFilePath;
    }
    public void setSFilePath(String value)
    {
        sFilePath = value;
    }
    public String getSFileName()
    {
        return sFileName;
    }
    public void setSFileName(String value)
    {
        sFileName = value;
    }
    public int getNSplitter()
    {
        return nSplitter;
    }
    public void setNSplitter(int nCount)
    {
        nSplitter = nCount;
    }
}