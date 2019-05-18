/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190518    lsf     初始版本
 */
package com.algorithm.redis.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
@Controller
@RequestMapping(value = "/publish")
public class SubscribeCtrl {

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("{name}")
    public void sendMessage(@PathVariable("name") String name) {
        publisherService.publish(name);
    }


}