/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190220    gaoyouan     初始版本
 */
package com.algorithm.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @brief
 *     SmartApplicationListener实现ApplicationListener接口，可以支持listener之间的触发顺序，普通的ApplicationListener优先级最低
 *  ContextRefreshedEvent	当ApplicationContext或者叫spring被初始化或者刷新initialized会触发该事件
 * ContextStartedEvent	spring初始化完，时触发
 * ContextStoppedEvent	spring停止后触发，一个停止了的动作，可以通过start()方法从新启动
 * ContextClosedEvent	spring关闭，所有bean都被destroyed掉了,这个时候不能被刷新，或者从新启动了
 * RequestHandledEvent	请求经过DispatcherServlet时被触发，在request完成之后
 * @see
 *
 */
public class SmartListener implements SmartApplicationListener {
    private volatile AtomicBoolean isInit=new AtomicBoolean( false );
    private static Logger logger= LoggerFactory.getLogger( SmartListener.class );

    @Override
    public boolean supportsEventType( Class< ? extends ApplicationEvent > aClass ) {
        return aClass == ContextRefreshedEvent.class;
    }

    @Override
    public boolean supportsSourceType( Class< ? > aClass ) {
        return true;
    }

    @Override
    public void onApplicationEvent( ApplicationEvent  applicationEvent ) {
        if(!isInit.compareAndSet( false,true )){
            return;
        }
        start( (ContextRefreshedEvent)applicationEvent );
    }

    private void start( ContextRefreshedEvent  contextRefreshedEvent ) {
        if ( null == contextRefreshedEvent.getApplicationContext().getParent() ) {
            logger.debug( ">>>>> spring初始化完毕 <<<<<" );
            // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
            Map< String, Object > baseServices =
                    contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation( Service.class );
            for ( Object service : baseServices.values() ) {
                logger.debug( ">>>>> {}.initMapper()", service.getClass().getName() );
                try {
                    Method initMapper = service.getClass().getMethod( "initMapper" );
                    initMapper.invoke( service );
                } catch ( Exception e ) {
                    logger.error( "初始化BaseService的initMapper方法异常", e );
                    e.printStackTrace();
                }
            }
            // 系统入口初始化
            Map< String, SmartApplicationListener > baseInterfaceBeans =
                    contextRefreshedEvent.getApplicationContext().getBeansOfType( SmartApplicationListener.class );
            for ( Object service : baseInterfaceBeans.values() ) {
                logger.debug( ">>>>> {}.init()", service.getClass().getName() );
                try {
                    Method init = service.getClass().getMethod( "init" );
                    init.invoke( service );
                } catch ( Exception e ) {
                    logger.error( "初始化SmartApplicationListener的init方法异常", e );
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getOrder() {
        //越小优先级越高
        return 0;
    }

}