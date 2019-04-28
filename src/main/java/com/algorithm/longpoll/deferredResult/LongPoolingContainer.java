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

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 长轮询消息请求与发布
 */
@Service
public class LongPoolingContainer {
    private static Logger logger = LoggerFactory.getLogger( LongPoolingContainer.class );
    //message容器
    private static Map< String, PoolingMessage > messageMap = new ConcurrentSkipListMap<>();
    //    private static ThreadPoolExecutor executor = new ThreadPoolExecutor( 20, 40, 30000L,
    //            TimeUnit.MILLISECONDS, new ArrayBlockingQueue< Runnable >( 100 ) );
    //请求容器:guava中的Multimap，多值map,对map的增强，一个key可以保持多个value
    private static Multimap< String, DeferredResult< JSONObject > > requestsMap = Multimaps.synchronizedSetMultimap( HashMultimap.create() );
    //SynchronizedMultimap 虽是并发容器，但多线程下遍历时进行修改还是会出现ConcurrentModificationException，需要对remove和遍历操作进行上锁
    private ReentrantLock lock = new ReentrantLock();
    private static boolean stop = false;
    //http默认空串不会返回数据，默认结果
    private static JSONObject default_result = new JSONObject();

    static {
        default_result.put( "message","" );
    }

    public LongPoolingContainer(){
        pushLoop();
    }

    public DeferredResult< JSONObject > watch( String vid ) {
        logger.info( "Request received " + vid );
        DeferredResult< JSONObject > deferredResult = new DeferredResult<>( 30000L, default_result );
        //当deferredResult完成时（不论是超时还是异常还是正常完成），移除requestsMap中相应的watch key
        deferredResult.onCompletion( () -> {
            lock.lock();
            try {
                logger.info( "complete remove watch :" + vid );
                requestsMap.remove( vid, deferredResult );
            } finally {
                lock.unlock();
            }
        } );
//        deferredResult.onTimeout( () -> {
//            lock.lock();
//            logger.info( "timeout remove watch :" + vid );
//            requestsMap.remove( vid, deferredResult );
//            lock.unlock();
//        } );
        lock.lock();
        try {
            requestsMap.put( vid, deferredResult );
        } finally {
            lock.unlock();
        }

        return deferredResult;
    }

    public  void publish( String vid, String message ) {
        if ( !StringUtils.isEmpty( message ) ) {
            lock.lock();
            try {
                if ( requestsMap.containsKey( vid ) ) {
                    clear( vid );
                    Collection< DeferredResult< JSONObject > > deferredResults = requestsMap.get( vid );
                    //通知所有watch这个namespace变更的长轮训配置变更结果
                    JSONObject obj = new JSONObject();
                    obj.put( "message",message );
                    for ( DeferredResult< JSONObject > deferredResult : deferredResults ) {
                        deferredResult.setResult( obj );
                    }
                }
            } finally {
                lock.unlock();
            }
        }

    }

    public void put( PoolingMessage message ) {
        messageMap.put( message.getVid(), message );
    }

    public void del( String vid ) {
        messageMap.remove( vid );
    }

    public String getMsg( String vid ) {
        PoolingMessage message = messageMap.get( vid );
        if ( message != null ) {
            String msg = message.getMessage();
//            message.setLastConnectime( System.currentTimeMillis() );
//            message.setMessage( "" );
//            put( message );
            return msg;
        } else {
//            put( new PoolingMessage( vid ) );
            return "";
        }
    }

    public void setMsg( String vid, String msg ) {
        PoolingMessage message = messageMap.get( vid );
        if ( message != null ) {
            message.setLastConnectime( System.currentTimeMillis() );
            message.setMessage( msg );
            put( message );
        } else {
            put( new PoolingMessage( vid, msg ) );
        }
    }

    public void clear( String vid ) {
        PoolingMessage message = messageMap.get( vid );
        if ( message != null ) {
            message.setLastConnectime( System.currentTimeMillis() );
            message.setMessage( null );
            put( message );
        }
    }

    //轮询消息，过期的删除，未过期的推送
    public void pushLoop() {
        logger.info( "long-polling push loop start ..." );
        new Thread( () -> {
            while ( !stop ) {
                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                long now = System.currentTimeMillis();

                for ( Map.Entry< String, PoolingMessage > entry : messageMap.entrySet() ) {
                    PoolingMessage message = entry.getValue();

                    if ( null == message || message.getLastConnectime() +  ECConstants.VALID_TIME < now ) {
                        logger.info( "remove invalidate polling dev :{} ", message.getVid() );
                        del( entry.getKey() );
                    } else {
                        //暂时先不处理空串
                        String msg = entry.getValue().getMessage();
                        if ( !StringUtils.isEmpty( msg ) ) {
                            publish( entry.getKey(), msg );
                        }

                    }
                }
            }
        } ).start();
    }

}