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

import org.assertj.core.internal.Predicates;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *  对于tomcat来说，我们一般会加载两个上下文容器一个父容器，一个mvc子容器
 * @see
 *
 */
public class SelfListeners implements ApplicationListener< ContextRefreshedEvent > {
    //防止web应用出现父子容器，多次触发
//    1.设置标志位
//    2.只在父容器加载时允许触发
//    3.如果是加载资源，可以每次加载清空前先清空资源容器

    private volatile AtomicBoolean isInit=new AtomicBoolean( false );

    @Override
    public void onApplicationEvent( ContextRefreshedEvent contextRefreshedEvent ) {
        if(!isInit.compareAndSet( false,true )){
            return;
        }
        start();
    }


//    @Override
//    public void onApplicationEvent( ContextRefreshedEvent contextRefreshedEvent ) {
//
//        //父容器parent为null
//        if( contextRefreshedEvent.getApplicationContext().getParent()==null){
//            start();
//        }
//
//    }

    private void start(){
        System.out.println( "begin--------" );
    }
}