package com.algorithm.concurrent.thread;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/04/22 22:37
 */
public class InterruptTest1 {

    public static void main(String[] args) throws InterruptedException {
       test2();
//       test1();
    }

    private static void test1() throws InterruptedException {
        Thread thread = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread()+ "hello");
            }
        });
        thread.start();

        Thread.sleep(1000);

        System.out.println("main thread interrupt thread");

        thread.interrupt();

        thread.join();

        System.out.println("main is over");
    }

    private static void test2() throws InterruptedException {
        Thread thread = new Thread(()->{
            System.out.println("thread begin sleep for 200s");
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                System.out.println("thread is interrupting while sleeping");
                return;
            }
            System.out.println("thread leaving normally");
        });
        thread.start();

        Thread.sleep(1000);

        System.out.println("main thread interrupt thread");

        thread.interrupt();

        thread.join();

        System.out.println("main is over");
    }


}
