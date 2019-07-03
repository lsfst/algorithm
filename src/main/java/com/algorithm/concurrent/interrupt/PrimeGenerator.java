package com.algorithm.concurrent.interrupt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @program algorithm
 * @description:使用volatile保存取消状态，缺点是推出过程存在延迟，遇到阻塞任务时可能无法取消
 * @author: liangshaofeng
 * @create: 2019/06/29 14:28
 */
public class PrimeGenerator implements Runnable {

    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean canceled;
    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!canceled){
            p  = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }
    }

    public void cancel(){
        canceled = true;
    }

    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }

    List<BigInteger> aSecondOfPrimes() throws InterruptedException{
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            Thread.sleep(1000);
        }finally {
            generator.cancel();
        }
        return generator.get();
    }
}
