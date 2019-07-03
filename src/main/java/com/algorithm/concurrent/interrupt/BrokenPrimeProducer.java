package com.algorithm.concurrent.interrupt;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program algorithm
 * @description: 不可靠操作将导致无法取消
 * @author: liangshaofeng
 * @create: 2019/06/29 14:36
 */
public class BrokenPrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;
    private volatile boolean canceled = false;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!canceled){
                //阻塞操作，在队列填满时无法返回
                queue.put(p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void cancel(){
        canceled = true;
    }

    void consumePrimes() throws InterruptedException{
        BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<>();
        BrokenPrimeProducer primeProducer = new BrokenPrimeProducer(primes);
        primeProducer.start();
        try {
            while (true){
                primes.take();
            }
        }finally {
            primeProducer.cancel();
        }
    }
}
