package com.algorithm.concurrent.boundedBuffer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program algorithm
 * @description:BoundedBuffer的生产者-消费者测试
 * CyclicBarrier作为阀门，可代替两个CountDownlatch
 * @author: liangshaofeng
 * @create: 2019/07/03 13:32
 */
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
//    private final BarrierTimer timer = new BarrierTimer();
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final int nTrials,nPairs;

    public PutTakeTest(int capacity,int nPairs, int nTrials) {
        this.bb = new BoundedBuffer<>(capacity);
        this.barrier = new CyclicBarrier(nPairs*2+1);
        this.nTrials = nTrials;
        this.nPairs = nPairs;
    }

    void test() throws BrokenBarrierException, InterruptedException {
//        pool.execute(timer);
        long start = System.nanoTime();
        for(int i = 0;i<nPairs;i++){
            pool.execute(new Producer());
            pool.execute(new Consumer());
        }
        barrier.await();
        barrier.await();
        long end = System.nanoTime();
//        pool.execute(timer);
//        long nsPerItem = timer.getTime()/(nPairs*(long)nTrials);
        long nsPerItem = (end-start)/(nPairs*(long)nTrials);
        System.out.println("Throughput: "+nsPerItem+" ns/item");
        System.out.println(putSum.get()==takeSum.get());
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
            int tpt=100000;
            for(int cap =1;cap<=1000;cap*=10){
                System.out.println("Capacity:"+cap);
                for(int pairs = 1;pairs<=128;pairs*=2){
                    PutTakeTest t = new PutTakeTest(cap,pairs,tpt);
                    System.out.println("Pairs: "+pairs+"\t");
                    t.test();
                    System.out.println("\t");
                    Thread.sleep(1000);
                    t.test();
                    System.out.println();
                    Thread.sleep(1000);
                }
            }

        pool.shutdown();
    }
    //随机数生成器
    public int xorShift(int y){
        y^=(y<<6);
        y^=(y>>>21);
        y^=(y<<7);
        return y;
    }

    class Producer implements Runnable{

        @Override
        public void run() {
            try {

                int seed = (this.hashCode()^(int) System.nanoTime());
                int sum = 0;
                barrier.await();
                for(int i=nTrials;i>0;--i){
                    bb.put(seed);
                    sum+=seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    class Consumer implements Runnable{

        @Override
        public void run() {
            try {

                barrier.await();
                int sum = 0;
                for(int i=nTrials;i>0;--i){
                    sum+=bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
