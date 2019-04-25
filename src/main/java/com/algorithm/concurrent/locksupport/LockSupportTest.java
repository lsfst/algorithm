package com.algorithm.concurrent.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @program algorithm
 * @description: park()可以被其他线程interrupt()或unpark(Thread t)或虚假唤醒，所以调用时最好循环条件判断
 * @author: liangshaofeng
 * @create: 2019/04/25 22:43
 */
public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
//        park1();
//        park2();
//        park3();
//        park4();
        park5();
    }

    private static void park1(){
        System.out.println("begin park");
        LockSupport.park();
        System.out.println("end park");
    }

    private static void park2() {
        System.out.println("begin unpark");
        LockSupport.unpark(Thread.currentThread());

        LockSupport.park();

        System.out.println("end bark");
    }

    private static void park3() throws InterruptedException {
        Thread thread=new Thread(()->{
            System.out.println("child thread begin park");
            LockSupport.park();
            System.out.println("child thread unpark");
        });

        thread.start();

        Thread.sleep(1000L);

        System.out.println("main thread begin unpark");
        LockSupport.unpark(thread);

    }

    private static void park4() throws InterruptedException {
        Thread thread=new Thread(()->{
            System.out.println("child thread begin park");
            //只有中断才允许退出循环
            while (!Thread.currentThread().isInterrupted()){
                LockSupport.park();
            }
            System.out.println("child thread unpark");
        });

        thread.start();

        Thread.sleep(100000L);

        System.out.println("main thread begin interrupt");
        thread.interrupt();

    }

    private static void park5() throws InterruptedException {
        //blocker park :jstack pid可以获取更多阻塞对象的信息
//        - parking to wait for  <0x000000079579b630> (a com.algorithm.concurrent.locksupport.LockSupportTest)
        LockSupportTest supportTest = new LockSupportTest();
        supportTest.blockerPark();

        Thread.sleep(100000L);

        System.out.println("main thread begin interrupt");
        Thread.currentThread().interrupt();

    }

    public void blockerPark(){
        LockSupport.park(this);
    }

}
