package com.algorithm.concurrent.thread;

import java.util.concurrent.TimeUnit;

/**
 * @program algorithm
 * @description:
 * @author: liangshaofeng
 * @create: 2019/07/02 22:44
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread previius = Thread.currentThread();
        for(int i = 0;i<10;i++){
            Thread thread = new Thread(new Domino(previius),String.valueOf(i));
            thread.start();
            previius=thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+" terminate");

    }

    static class Domino implements Runnable{
        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" terminate");
        }
    }
}
