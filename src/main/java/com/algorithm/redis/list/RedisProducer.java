/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190516    lsf     初始版本
 */
package com.algorithm.redis.list;

import com.algorithm.redis.autorefresh.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *
 * @see
 *
 */
public class RedisProducer extends Thread {

    private String producerName;
    private volatile int count;
    private ListOperations listOperations;

    public RedisProducer(String name,ListOperations listOperations) {
        this.producerName = name;
        this.listOperations = listOperations;
    }

    public void putMessage(String message) {
        Long size = listOperations.leftPush(RedisConst.QUEUE_NAME, message);
        System.out.println(producerName + ": 当前未被处理消息条数为:" + size);
        count++;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        try {
            int i=0;
            while (true) {
                putMessage(String.valueOf( i ));
                i++;
//                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}