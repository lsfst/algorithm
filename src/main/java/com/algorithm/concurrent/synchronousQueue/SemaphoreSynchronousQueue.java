/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190326    gaoyouan     初始版本
 */
package com.algorithm.concurrent.synchronousQueue;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的同步队列
 */
public class SemaphoreSynchronousQueue<E> {
    E item = null;
    Semaphore sync = new Semaphore( 0 );
    Semaphore send = new Semaphore( 1 );
    Semaphore recv = new Semaphore( 0 );

    public E take() throws InterruptedException {
        recv.acquire();
        E x = item;
        sync.release();
        send.release();
        return x;
    }

    public void put( E x ) throws InterruptedException {
        send.acquire();
        item = x;
        recv.release();
        sync.acquire();
    }
}