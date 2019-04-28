/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190424    lsf     初始版本
 */
package com.algorithm.longpoll.deferredResult;

import org.springframework.context.ApplicationEvent;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class ECCApollEvent extends ApplicationEvent {
    private String vid;
    private String message;

    public ECCApollEvent( Object source,String vid,String message ) {
        super( source );
        this.vid = vid;
        this.message = message;
    }

    public String getVid() {
        return vid;
    }

    public String getMessage() {
        return message;
    }
}