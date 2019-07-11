package com.algorithm.concurrent.boundedBuffer;

/**
 * @program algorithm
 * @description:计时器
 * @author: liangshaofeng
 * @create: 2019/07/03 14:12
 */
public class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime,endTime;

    @Override
    public synchronized void run() {
        long t = System.nanoTime();
        if(!started){
            started = true;
            startTime = t;
        }else {
            endTime = t;
            started = false;
        }
    }

    public synchronized void clear(){
        started = false;
    }


    public synchronized long getTime(){
        return endTime-startTime;
    }
}
