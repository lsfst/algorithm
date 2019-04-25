package com.algorithm.concurrent.atomic;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/04/14 21:21
 */
public class LockDemo3 {
    volatile int i = 0;

    public synchronized void add(){
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo3 id = new LockDemo3();
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
