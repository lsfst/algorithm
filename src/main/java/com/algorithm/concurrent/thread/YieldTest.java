package com.algorithm.concurrent.thread;

/**
 * @program algorithm
 * @description: yield() 让出cpu,并未挂起，处于就绪状态
 * @author: liangshaofeng
 * @create: 2019/04/22 22:01
 */
public class YieldTest implements Runnable{

    YieldTest(){
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for (int i = 0 ;i <5 ;i++){
            if((i % 5)==0){
                System.out.println(Thread.currentThread()+" yield cpu..");
                Thread.yield();
            }
        }
        System.out.println(Thread.currentThread()+" is over" );
    }


    public static void main(String[] args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }
}
