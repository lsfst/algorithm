package com.algorithm.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/04/14 21:14
 */
public class LockDemo2 {
     AtomicInteger i = new AtomicInteger(0);

    public void add(){
        i.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo2 id = new LockDemo2();
        long start = System.currentTimeMillis();
        for(int i = 0 ;i<2;i++){
            new Thread(()->{
                for(int j = 0;j<10000;j++){
                    id.add();
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        Thread.sleep(2000L);

        System.out.println(id.i+ "  "+(end-start)+"ms");
    }
}
