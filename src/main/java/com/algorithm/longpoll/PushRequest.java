/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190423    lsf     初始版本
 */
package com.algorithm.longpoll;

import com.algorithm.utils.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询测试
 */
public class PushRequest {

    private static HttpHandler httpHandler = HttpHandler.getInstance();

    private static AtomicInteger seq = new AtomicInteger( 0 );

    private static Logger logger = LoggerFactory.getLogger( PushRequest.class );

    private static String[] vids = {"2120174320300001","2120174320300002","2120174320300003","2120174320300004","2120174320300005","2120174320300006"};

    private static String MSG_URL = "https://localhost:8080/ecc/sms/recvSmsTest?receiver=8618589028514&code=CODE";

    private static String WATCH_URL = "https://localhost:8080/ecc/watch/VID";

    private static String PUBLISH_URL = "http://localhost:8080/ecc/publish/VID?message=MESSAGE";

    public static void main( String[] args ) throws InterruptedException {

//        new Thread( new HandleMsgTask() ).start();

        new Thread( new WatchTask() ).start();

//        Thread.sleep( 35000 );
//
//        new Thread( new PublishTask() ).start();
    }

    static class HandleMsgTask implements Runnable {

        @Override
        public void run() {
                //通过asyncContext拿到response

            while ( true ){
                try {
                    Thread.sleep( 10000 );
                } catch ( InterruptedException e ) {
                    logger.error( "sleeping interrupted" );
                }

                String code = String.valueOf( randomCode() );
                seq.incrementAndGet();
                httpHandler.post( MSG_URL.replace( "CODE",code ),null,"Basic YWRtaW46QW1uQDF1").toJSONString();
                logger.info( "[{}] msg = {} ",seq.get(),code );
            }

        }
    }


    static class WatchTask implements Runnable {

        @Override
        public void run() {
            while ( true ){
                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    logger.error( "sleeping interrupted" );
                }

                String vid = randomVid();
                seq.incrementAndGet();
                logger.info( "[{}] watch {} ",seq.get(),vid );
                logger.info( "[{}] vid = {} ,result = {} ",seq.get(),vid,httpHandler.get( WATCH_URL.replace( "VID",vid ),"Basic YXBpVXNlcjoxMjM0NTY=") );
            }

        }
    }

    static class PublishTask implements Runnable {

        @Override
        public void run() {
            //通过asyncContext拿到response

            while ( true ){
                try {
                    Thread.sleep( 100 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                int code = randomCode();
                String vid = randomVid();
                logger.info( "publish : [{}][{}] ",vid,code );
                httpHandler.post( PUBLISH_URL.replace( "VID",vid ).replace( "MESSAGE",String.valueOf(code) ),null,"Basic YWRtaW46QW1uQDF1");
            }

        }
    }

    public static int randomCode() {
        return (int) ((ThreadLocalRandom.current().nextDouble() * 9 + 1) * 100000);
    }


    public static String randomVid() {
        return vids[ThreadLocalRandom.current().nextInt( 6 )];
    }
}