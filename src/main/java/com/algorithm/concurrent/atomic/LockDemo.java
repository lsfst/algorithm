package com.algorithm.concurrent.atomic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program algorithm
 * @description: 和同步关键字相比，lock更灵活
 * @author: liangshaofeng
 * @create: 2019/04/14 21:05
 */
public class LockDemo {
     volatile int i = 0;

     Lock lock = new ReentrantLock();

    public void add(){
        lock.lock();
//        lock.tryLock();
        try {
            i++;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo id = new LockDemo();
        long start = System.currentTimeMillis();
        for(int i = 0 ;i<2;i++){
            new Thread(()->{
                for(int j = 0;j<10000;j++){
                    id.add();
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        Thread.sleep(5000L);

        System.out.println(id.i+ "  "+(end-start)+"ms");
    }
}
