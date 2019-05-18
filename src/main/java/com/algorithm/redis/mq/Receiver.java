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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
@Component
public class Receiver implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage( Message message, byte[] pattern) {
        RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());
        logger.info("收到消息" + deserialize);
    }

}