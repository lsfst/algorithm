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

import com.algorithm.redis.list.RedisConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
@Service
public class PublisherService {
    private static Logger logger = LoggerFactory.getLogger( PublisherService.class );

    @Autowired
    private RedisTemplate redisTemplate;

    public void publish(String message) {
        try {
            redisTemplate.convertAndSend( RedisConst.CHANNEL_NAME, message);
            logger.info("发送成功:"+message);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送失败:"+message);
        }
    }

}