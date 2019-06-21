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
import java.util.HashMap;
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
    //message容器
    private static Map< String, PoolingMessage > messageMap = new ConcurrentSkipListMap<>();

    //定时轮询
    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    //请求容器:guava中的Multimap，多值map,对map的增强，一个key可以保持多个value
    private static Multimap< String, DeferredResult< Map > > requestsMap = Multimaps.synchronizedSetMultimap( HashMultimap.create() );

    //SynchronizedMultimap 虽是并发容器，但多线程下遍历时进行修改还是会出现ConcurrentModificationException，需要对remove和遍历操作上锁
    private ReentrantLock lock = new ReentrantLock();

//    private static boolean stop = false;


    public static Map< String, String > default_result = new HashMap< String, String >() {{
        put( "CODE", "" );
    }};

    public LongPoolingContainer() {
        if ( ECConstants.LONG_POLL_START ) {
            LongPollTask task = new LongPollTask();
            executor.scheduleWithFixedDelay( task, 2, 1, TimeUnit.SECONDS );
        }
    }

    public DeferredResult< Map > watch( String vid) {
        DeferredResult< Map > deferredResult = new DeferredResult<>( ECConstants.LONG_POLL_TIMEOUT, default_result );

        //当deferredResult完成时（不论是超时还是异常还是正常完成），移除requestsMap中相应的watch key
        deferredResult.onCompletion( () -> {
            lock.lock();

            try {
                logger.info(  "complete|result:{}", JSONObject.toJSONString( deferredResult.getResult() ) );
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

    public void publish( String vid, Map< String, String > message ) {
        if ( !StringUtils.isEmpty( message ) ) {
            lock.lock();

            try {
                if ( requestsMap.containsKey( vid ) ) {
                    //清空对应消息
                    clear( vid );
                    //通知所有watch这个namespace变更的长轮询配置变更结果
                    Collection< DeferredResult< Map > > deferredResults = requestsMap.get( vid );
                    logger.info( "{}|publish msg: [{}]", vid, JSONObject.toJSONString( message ) );
                    for ( DeferredResult< Map > deferredResult : deferredResults ) {
                        deferredResult.setResult( message );
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


    public String getMsg( String vid, String type ) {
        PoolingMessage message = messageMap.get( vid );
        if ( message != null ) {
            String msg = message.getMessage().get( type );
            return msg;
        } else {
            return "";
        }
    }

    public void setMsg( String vid, String msg ) {
        setMsg( vid, "CODE", msg );
    }

    public void setMsg( String vid, String type, String msg ) {
        PoolingMessage message = messageMap.get( vid );
        if ( message != null ) {
            logger.info( vid + "|publish|{}|{}", type, msg );
            message.setLastConnectime( System.currentTimeMillis() );
            message.setMessage( type, msg );
            put( message );
        } else {
            put( new PoolingMessage( vid, type, msg ) );
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
//    public void pushLoop() {
//        logger.info( "long-polling push loop start ..." );
//        new Thread( () -> {
//            while ( !stop ) {
//                try {
//                    Thread.sleep( 1000 );
//                } catch ( InterruptedException e ) {
//                    e.printStackTrace();
//                }
//                long now = System.currentTimeMillis();
//
//                for ( Map.Entry< String, PoolingMessage > entry : messageMap.entrySet() ) {
//                    PoolingMessage message = entry.getValue();
//
//                    if ( null == message || message.getLastConnectime() +  ECConstants.VALID_TIME < now ) {
//                        logger.info( "remove invalidate polling dev :{} ", message.getVid() );
//                        del( entry.getKey() );
//                    } else {
//                        //暂时先不处理空串
//                        String msg = entry.getValue().getMessage();
//                        if ( !StringUtils.isEmpty( msg ) ) {
//                            publish( entry.getKey(), msg );
//                        }
//
//                    }
//                }
//            }
//        } ).start();
//    }

    private class LongPollTask implements Runnable {

        @Override
        public void run() {
            long now = System.currentTimeMillis();

            for ( Map.Entry< String, PoolingMessage > entry : messageMap.entrySet() ) {
                PoolingMessage message = entry.getValue();
                if ( null == message || message.getLastConnectime() + ECConstants.VALID_TIME < now ) {
                    logger.info( "{}|invalidate remove", message.getVid() );
                    del( entry.getKey() );
                } else {
                    //暂时先不处理空串
                    Map msg = entry.getValue().getMessage();
                    if ( msg != null && msg.size() > 0 ) {
                        publish( entry.getKey(), msg );
                    }
                }
            }
        }
    }
}