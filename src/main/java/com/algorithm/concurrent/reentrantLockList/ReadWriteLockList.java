package com.algorithm.concurrent.reentrantLockList;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program algorithm
 * @description: 读写锁维护线程安全的list
 * @author: liangshaofeng
 * @create: 2019/06/27 10:28
 */
public class ReadWriteLockList {
    private ArrayList<String> array = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void add(String e){

        writeLock.lock();
        try {
            array.add(e);

        }finally {
            writeLock.unlock();

        }
    }

    public void remove(String e){

        writeLock.lock();
        try {
            array.remove(e);

        }finally {
            writeLock.unlock();

        }
    }

    public String get(int index){
        readLock.lock();
        try {
            return array.get(index);

        }finally {
            readLock.unlock();

        }
    }
}
