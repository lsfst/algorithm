package com.algorithm.concurrent.nonReentrantLock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @program algorithm
 * @description: 基于AQS实现不可重入的独占锁
 * @author: liangshaofeng
 * @create: 2019/05/14 23:11
 */
public class NonReentrantock implements Lock,Serializable {

    private static class Sync extends AbstractQueuedSynchronizer{

        //判断锁是否已被持有
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //state为0则尝试获取锁
        @Override
        protected boolean tryAcquire(int acquires) {
            assert acquires == 1;
            if(compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //尝试释放锁，设置state为0
        @Override
        protected boolean tryRelease(int releases) {
            assert releases == 1;
            if(getState()==0)
                throw new IllegalStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition(){
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(timeout));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked(){
        return sync.isHeldExclusively();
    }
}
