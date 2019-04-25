package com.algorithm.concurrent.locksupport;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @program algorithm
 * @description: LockSupport实现先进先出锁,只有队首元素可以获得锁
 * @author: liangshaofeng
 * @create: 2019/04/25 23:18
 */
public class FiFoMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedDeque<>();

    public void lock(){
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        //当前线程不在队首或锁已被其他线程占有，则park自己
        while(waiters.peek()!=current || !locked.compareAndSet(false,true)){
            LockSupport.park(this);
            //忽略中断返回，并重置中断位
            if(Thread.interrupted()){
                wasInterrupted = true;
            }
        }

        waiters.remove();

        if(wasInterrupted){
            current.interrupt();
        }
    }

    public void unpark(){
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
