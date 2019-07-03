package com.algorithm.concurrent.countdownlatch;

import sun.awt.windows.ThemeReader;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/05/25 13:22
 */
public class WorkJoin extends Thread {
    private String name;
    private long time;

    public WorkJoin(String name, long time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + "开始工作");
            Thread.sleep(time);
            System.out.println(name + "工作完成, 耗时："+ time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WorkJoin worker0 = new WorkJoin("lilei-0", (long)(Math.random() * 10000));
        WorkJoin worker1 = new WorkJoin("lilei-1", (long)(Math.random() * 10000));
        WorkJoin worker2 = new WorkJoin("lilei-2", (long)(Math.random() * 10000));
        worker0.start();
        worker1.start();

        worker0.join();
        worker1.join();
        System.out.println("准备工作就绪");

        worker2.start();
        Thread.sleep(10000);
    }
}
