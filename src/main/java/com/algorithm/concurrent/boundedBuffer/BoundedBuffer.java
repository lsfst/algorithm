package com.algorithm.concurrent.boundedBuffer;

import java.util.concurrent.Semaphore;

/**
 * @program algorithm
 * @description:基于信号量的有界缓存,足够好，但不如ArrayBlockingQueue/LinkedBlockingQueue
 * 原因在于take/put操作含有多个竞争操作
 * @author: liangshaofeng
 * @create: 2019/07/03 13:20
 */
public class BoundedBuffer<E> {
    private  Semaphore availableItems;
    private  Semaphore availableSpaces;
    private  E[] items;
    private int putPosition = 0,takePosition = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[])new Object[capacity];
    }

    public boolean isEmpty(){
        return availableItems.availablePermits()==0;
    }

    public boolean isFull(){
        return availableSpaces.availablePermits()==0;
    }

    public void put(E e) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(e);
        availableItems.release();

    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E e = doExtract();
        availableSpaces.release();
        return e;
    }

    private synchronized void doInsert(E e){
        int i = putPosition;
        items[i] = e;
        putPosition = (++i == items.length)?0:i;
    }

    private synchronized E doExtract(){
        int i = takePosition;
        E e = items[i];
        items[i] = null;
        takePosition = (++i==items.length)?0:i;
        return e;
    }
}
