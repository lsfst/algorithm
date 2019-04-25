package com.algorithm.concurrent.atomic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/04/16 21:18
 */
public class SelfLockDemo {
    volatile int i = 0;

    Lock lock = new SelfLock();

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
        SelfLockDemo id = new SelfLockDemo();
        long start = System.currentTimeMillis();
        for(int i = 0 ;i<2;i++){
            new Thread(()->{
                for(int j = 0;j<10000;j++){
                    id.add();
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        Thread.sleep(3000L);

        System.out.println(id.i+ "  "+(end-start)+"ms");
    }
}
