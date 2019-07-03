package com.algorithm.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @program algorithm
 * @description: countDownLatch相对于join的粒度更细
 * @author: liangshaofeng
 * @create: 2019/05/25 13:25
 */
public class WorkerPart extends Thread {

    private String name;
    private long time;
    private CountDownLatch countDownLatch;

    public WorkerPart(String name, long time, CountDownLatch countDownLatch) {
        this.name = name;
        this.time = time;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + "开始阶段1工作");
            Thread.sleep(time);
            System.out.println(name + "阶段1完成, 耗时："+ time);
            countDownLatch.countDown();

            System.out.println(name + "开始阶段2工作");
            Thread.sleep(time);
            System.out.println(name + "阶段2完成, 耗时："+ time);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        int COUNT = 2;
        final CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        WorkerPart worker0 = new WorkerPart("lilei-0", (long)(Math.random() * 10000), countDownLatch);
        WorkerPart worker1 = new WorkerPart("lilei-1", (long)(Math.random() * 10000), countDownLatch);
        worker0.start();
        worker1.start();
        countDownLatch.await();
        System.out.println("准备工作就绪");

        WorkerPart worker2 = new WorkerPart("lilei-2", (long)(Math.random() * 10000), countDownLatch);
        worker2.start();
    }
}
