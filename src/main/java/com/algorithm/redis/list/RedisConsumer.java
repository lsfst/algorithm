/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190517    lsf     初始版本
 */
package com.algorithm.redis.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
    public class RedisConsumer extends Thread {

        private static Logger logger = LoggerFactory.getLogger( RedisConsumer.class );

        private String customerName;
        private ListOperations listOperations;
        private static boolean stop = false;

        public RedisConsumer(String name,ListOperations listOperations) {
            this.customerName = name;
            this.listOperations = listOperations;
        }

        public void stopCounsume(){
            stop = false;
        }

        public void cousume() {
            //防止队列消息消耗完后频繁访问redis，采用brpop阻塞获取消息
            Object message = listOperations.rightPop( RedisConst.QUEUE_NAME, RedisConst.BLOCK_TIME, TimeUnit.MILLISECONDS );
            if ( message != null ) {
                handle( message.toString() );
            }
        }

        public void handle(String message) {
            //...
            logger.info(customerName + " 正在处理消息: " + message);
        }

        @Override
        public void run() {
            while (!stop) {
                cousume();
            }
        }
    }