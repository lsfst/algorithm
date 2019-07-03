package com.algorithm.concurrent.interrupt;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @program algorithm
 * @description:使用线程中断位来中断
 * @author: liangshaofeng
 * @create: 2019/06/29 14:46
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()){
                queue.put(p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            //允许线程退出
            e.printStackTrace();
        }
    }

    public void cancel(){
        interrupt();
    }
}
