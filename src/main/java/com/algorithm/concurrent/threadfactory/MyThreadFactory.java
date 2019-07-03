package com.algorithm.concurrent.threadfactory;

import java.util.concurrent.ThreadFactory;

/**
 * @program algorithm
 * @description: 自定义线程工厂
 * @author: liangshaofeng
 * @create: 2019/07/02 13:15
 */
public class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r,poolName);
    }
}
