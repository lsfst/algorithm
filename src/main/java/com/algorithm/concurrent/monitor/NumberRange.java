package com.algorithm.concurrent.monitor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/06/27 16:19
 */
public class NumberRange {
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public synchronized void setLower(int i){
        if(i>upper.get()){
            throw new IllegalArgumentException("cant set lower to "+i+" >upper");
        }
        lower.set(i);
    }

    public synchronized void setUpper(int i){
        if(i<lower.get()){
            throw new IllegalArgumentException("cant set upper to "+i+" < lower");
        }
        upper.set(i);
    }
}
