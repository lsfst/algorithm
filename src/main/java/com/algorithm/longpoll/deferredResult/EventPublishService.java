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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 事件发布器
 */
@Service
public class EventPublishService {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 发布验证码事件
     * @param vid
     * @param message
     */
    public void registerApollEvent( String vid,String message)
    {
        applicationContext.publishEvent(new ECCApollEvent(this,vid,message));
    }
}