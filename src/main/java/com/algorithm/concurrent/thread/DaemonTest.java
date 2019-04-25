package com.algorithm.concurrent.thread;

/**
 * @program algorithm
 * @description: main运行结束后，JVM会启动一个DestroyJavaVM的线程，等待所有用户线程执行完成后终止JVM进程
 * @author: liangshaofeng
 * @create: 2019/04/22 23:03
 */
public class DaemonTest {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (;;){

            }
        });
//        观察执行完后是否虚拟机终止
//        t.setDaemon(true);
        t.start();
        System.out.println("main is over");
    }
}
