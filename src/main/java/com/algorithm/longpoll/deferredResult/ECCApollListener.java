/**
 * UUWiFiWebBase 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190418    lsf     初始版本
 */
package com.algorithm.longpoll.deferredResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * ECCDREvent监听器
 */
@Component
public class ECCApollListener implements ApplicationListener< ECCApollEvent > {

    @Autowired
    LongPoolingContainer container;

    @Override
    @Async
    public void onApplicationEvent( ECCApollEvent eccApollEvent ) {
        String message = eccApollEvent.getMessage();
        String vid = eccApollEvent.getVid();
        container.setMsg(vid,message);
    }
}