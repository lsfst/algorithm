package com.algorithm.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @program algorithm
 * @description: 前两个线程执行完成才能执行第三个线程
 * @author: liangshaofeng
 * @create: 2019/05/25 13:11
 */
public class Worker extends Thread {
    private String name;
    private long time;
    private CountDownLatch countDownLatch;

    public Worker(String name, long time, CountDownLatch countDownLatch) {
        this.name = name;
        this.time = time;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + "开始工作");
            Thread.sleep(time);
            System.out.println(name + "工作完成, 耗时："+ time);
            countDownLatch.countDown();
            System.out.println("countDownLatch.getCount():" + countDownLatch.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int COUNT = 2;
        final CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        Worker worker0 = new Worker("lilei-0", (long)(Math.random() * 10000), countDownLatch);
        Worker worker1 = new Worker("lilei-1", (long)(Math.random() * 10000), countDownLatch);
        worker0.start();
        worker1.start();
        countDownLatch.await();
        System.out.println("准备工作就绪");

        Worker worker2 = new Worker("lilei-2", (long)(Math.random() * 10000), countDownLatch);
        worker2.start();
        Thread.sleep(10000);
    }
}
