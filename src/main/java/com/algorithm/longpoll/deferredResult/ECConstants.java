/**
 * UUWiFiWebBase 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190422    lsf     初始版本
 */
package com.algorithm.longpoll.deferredResult;

import com.algorithm.utils.CommonUtils;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class ECConstants {

    //长连接check间隔
    public static final int Check_TIME_INTERVAL = 1 * 60 * 1000 ;
    //长连接失效时间
    public static final int VALID_TIME =  2 * 60 * 1000;
    //长连接hold时间
    public static long LONG_POLL_TIMEOUT =  30000L;
    //是否开启长轮询
    public static boolean LONG_POLL_START = false;

    static{
        String start_poll= CommonUtils.getRsAppCfg( "ecc.start.poll" );
        LONG_POLL_START=(start_poll!=null && start_poll.equals( "true" ))?true:LONG_POLL_START;
    }
}