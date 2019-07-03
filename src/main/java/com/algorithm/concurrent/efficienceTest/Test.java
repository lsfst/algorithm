package com.algorithm.concurrent.efficienceTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program algorithm
 * @description: 线程与线程池的效率对比
 * @author: liangshaofeng
 * @create: 2019/05/25 12:00
 */
public class Test {
    public static void main(String[] args) {
        threadPoolTest(20000);
//        threadest(20000);
    }


    public static void threadPoolTest(int count){
        long start = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        ThreadPoolExecutor tp = new ThreadPoolExecutor(1,1,60,TimeUnit.SECONDS,new LinkedBlockingQueue<>(count));
        final Random random = new Random();
        for(int i=0;i<count;i++){
            tp.execute(()->{
                l.add(random.nextInt());
            });
        }
        tp.shutdown();
        try {
            tp.awaitTermination(1,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(l.size());
    }

    public static void threadest(int count ){
        long start = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<>();
        final Random random = new Random();
        for(int i=0;i<count;i++){
            Thread thread = new Thread(()->{
                l.add(random.nextInt());
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis()-start);
        System.out.println(l.size());
    }
}
