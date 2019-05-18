package com.algorithm.concurrent.nonReentrantLock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * @program algorithm
 * @description: 使用自定义独占锁实现生产者消费者模式
 * @author: liangshaofeng
 * @create: 2019/05/14 23:25
 */
public class NonReentrantLockTest {
    final static NonReentrantock lock = new NonReentrantock();
    final static Condition notFull = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Queue<String> queue = new LinkedBlockingQueue<>();
    final static int queueSize = 0;

    public static void main(String[] args) {
        Thread producer = new Thread(()->{
            lock.lock();
            try {
                while (queue.size()==queueSize){
                        notEmpty.await();
                }

                queue.add("ele");

                notFull.signalAll();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        Thread consumer = new Thread(()->{
            lock.lock();
            try {
                while (queue.size()== 0){
                    notFull.await();
                }

                String ele = queue.poll();

                notEmpty.signalAll();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });

        producer.start();
        consumer.start();
    }
}
