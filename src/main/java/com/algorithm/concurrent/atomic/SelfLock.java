package com.algorithm.concurrent.atomic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @program algorithm
 * @description: 自定义实现一个锁
 * 涉及到：
 * 1.没抢到，如何让线程挂起，等待其他线程释放线程
 * 2.锁释放后，如何通知其他其他线程获取锁
 * 3.不要用object的wati和notify，因为必须使用synchonize关键字
 *
 * @author: liangshaofeng
 * @create: 2019/04/14 23:49
 */
public class SelfLock implements Lock {
    //lock 拥有者
    AtomicReference<Thread> owner = new AtomicReference<>();
    //线程等待队列
    ConcurrentHashMap<Thread,Object> waiter = new ConcurrentHashMap<>();

    @Override
    public void lock() {
        while (!owner.compareAndSet(null,Thread.currentThread())){
            //循环获取
            waiter.put(Thread.currentThread(),"");
            //park需要循环调用，因为存在伪唤醒，线程不经unpark而自动进入唤醒状态
            LockSupport.park();  //等待获取锁
            waiter.remove(Thread.currentThread());
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if(owner.compareAndSet(Thread.currentThread(),null)){
            //通知其他线程获取锁
            ConcurrentHashMap.KeySetView<Thread,Object> keySetView = waiter.keySet();
           for(Thread thread : keySetView){
               LockSupport.unpark(thread);
           }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
