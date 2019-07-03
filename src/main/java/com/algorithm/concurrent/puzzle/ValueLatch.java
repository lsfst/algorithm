package com.algorithm.concurrent.puzzle;

import java.util.concurrent.CountDownLatch;

/**
 * @program algorithm
 * @description:countDownlatch实现的携带结果的闭锁
 * @author: liangshaofeng
 * @create: 2019/07/02 14:27
 */
public class ValueLatch<T> {
    private T value = null;
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet(){
        return done.getCount() == 0;
    }

    public synchronized void setValue(T newVal){
        if(!isSet()){
            value = newVal;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this){
            return value;
        }
    }
}
